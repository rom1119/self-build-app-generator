package com.Self.Build.App.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    DefaultTokenServices defaultTokenServices;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, Authentication auth) {
        String token = request.getHeader("AUTH-TOKEN");
        System.out.println(token);
        if (token != null ) {
            defaultTokenServices.revokeToken(token);
        }
    }

}