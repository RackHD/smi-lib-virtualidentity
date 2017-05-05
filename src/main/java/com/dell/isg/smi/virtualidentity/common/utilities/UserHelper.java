/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.common.utilities;

//import org.keycloak.KeycloakPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);


    public static String getUserName() {
        String userName = null;
        try {
            // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // if(null != authentication){
            // Object principal = authentication.getPrincipal();
            // if (principal instanceof KeycloakPrincipal<?>){
            // userName = ((KeycloakPrincipal<?>) principal).getName();
            // }
            // else{
            logger.warn("failed to get the user from the context's authentication object");
            // }
            // }
            // else{
            logger.warn("failed to get the authentication object from the context");
            // }
        } catch (Exception e) {
            logger.warn("Unable to determine the username");
        }
        return userName;
    }
}
