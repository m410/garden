package org.m410.j8.controller.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.Controller;
import org.m410.j8.controller.action.http.Action;

import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.controller.action.PathExpr;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class FileUploadController extends Controller {
    public FileUploadController() {
        super(new PathExpr("/file"));
    }

    @Override
    public List<HttpActionDefinition> actions() {
        return ImmutableList.of(
                post("", upload)
        );
    }

    Action upload = (req) -> {
        String in = req.postBodyAsString();
        return respond().asText(in);
    };
}
