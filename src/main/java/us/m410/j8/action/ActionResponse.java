package us.m410.j8.action;

import us.m410.j8.action.direction.Direction;

import java.util.Map;

/**
 */
public interface ActionResponse {

    Map<String,String> getHeaders();

    Direction getDirection();

    Map<String,Object>  getModel();

    Map<String,Object>  getSession();

    Flash getFlash();

    boolean doInvalidateSession();
}
