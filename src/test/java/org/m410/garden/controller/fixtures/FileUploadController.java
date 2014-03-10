package org.m410.garden.controller.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;

import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.PathExpr;

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
        String in = req.bodyAsString();
        return respond().asText(in);
    };
}
