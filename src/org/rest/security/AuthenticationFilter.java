package org.rest.security;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    public AuthenticationFilter() {}

    @Context
    private HttpServletRequest request;

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest){
        SecurityContext context = (SecurityContext) request.getSession(true).getAttribute("session-security-context");
        if(context != null){
            containerRequest.setSecurityContext(context);
        }
        return containerRequest;
    }
}