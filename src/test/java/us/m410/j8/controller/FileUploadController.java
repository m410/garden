package us.m410.j8.controller;

import com.google.common.collect.ImmutableList;
import us.m410.j8.action.Action;
import static us.m410.j8.action.Response.*;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.PathExpr;

import java.io.InputStream;
import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class FileUploadController extends Controller {
    protected FileUploadController() {
        super(new PathExpr("/file"));
    }

    @Override
    public List<ActionDefinition> actions() {
        return ImmutableList.of(
                post("", upload)
        );
    }

    Action upload = (req) -> {
        String in = req.postBodyAsString();
        return response().asText(in);
    };
}
