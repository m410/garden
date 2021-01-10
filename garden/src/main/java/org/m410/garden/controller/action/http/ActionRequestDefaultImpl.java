package org.m410.garden.controller.action.http;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.garden.controller.action.Identity;
import org.m410.garden.controller.action.NotAPostException;
import org.m410.garden.controller.action.PathExpr;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ActionRequestDefaultImpl implements ActionRequest {

    private final HttpServletRequest servletRequest;
    private final PathExpr pathExpr;
    private final Identity identity;


    public ActionRequestDefaultImpl(HttpServletRequest servletRequest, PathExpr pathExpr) {
        this.servletRequest = servletRequest;
        this.pathExpr = pathExpr;
        this.identity = new Identity(servletRequest);
    }

    @Override
    public boolean isActiveSession() {
        return servletRequest.getSession(false) != null;
    }

    @Override
    public Identity identity() {
        return identity;
    }

    @Override
    public Map<String, Object> session() {

        if(isActiveSession()) {
            HashMap<String,Object> map = new HashMap<>();
            Enumeration<String> attributeNames = servletRequest.getSession(false).getAttributeNames();

            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                map.put(name,servletRequest.getSession(false).getAttribute(name));
            }

            return ImmutableMap.copyOf(map);
        }
        else {
            return ImmutableMap.of();
        }
    }

    @Override
    public RequestProperties properties() {
        return new RequestProperties(servletRequest);
    }

    @Override
    public Map<String, String> headers() {
        HashMap<String,String> map = new HashMap<>();
        Enumeration<String> headerNames = servletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            map.put(name, servletRequest.getHeader(name));
        }

        return ImmutableMap.copyOf(map);
    }

    /**
     * todo need to add listener org.apache.commons.fileupload.servlet.FileCleanerCleanup
     *
     * @return a list of file items or an empty list.
     */
    @Override
    public List<FileItem> files() {
        if(ServletFileUpload.isMultipartContent(servletRequest)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = servletRequest.getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                return ImmutableList.copyOf(upload.parseRequest(servletRequest));
            }
            catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return ImmutableList.of();
        }
    }

    @Override
    public Map<String, String> url() {
        return pathExpr.parametersForRequest(servletRequest);
    }

    @Override
    public Map<String, String[]> request() {
        return ImmutableMap.copyOf(servletRequest.getParameterMap());
    }

    @Override
    public Map<String, String> params() {
        ImmutableMap.Builder<String,String> b = ImmutableMap.builder();
        servletRequest.getParameterMap().entrySet().stream().forEach(e->b.put(e.getKey(),e.getValue()[0]));
        return b.build();
    }

    @Override
    public InputStream bodyAsStream() {
        // TODO check if it's a post or put
        try {
            return servletRequest.getInputStream();
        }
        catch (IOException e) {
            throw new NotAPostException(e);
        }
    }

    @Override
    public String bodyAsString() {
        // TODO check if it's a post or put
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(servletRequest.getInputStream(), writer, "UTF-8");
            return writer.toString();
        }
        catch (IOException e) {
            throw new NotAPostException(e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identity",identity())
                .append("url", url())
                .append("request", request())
                .append("session",session())
                .toString();
    }
}
