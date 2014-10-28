package org.m410.angular.web;

import com.google.common.collect.ImmutableList;

import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;

import java.util.List;

import static org.m410.garden.transaction.TransactionScope.*;


/**
 * shows the home page
 *
 * @author Michael Fortin
 */
public final class HomeController extends Controller {

    public HomeController() {
    	
    	super("");
    }

    @Override
    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(get("", home).transaction(None));
    }

    Action home = req -> respond().withView("/index.html");
}
