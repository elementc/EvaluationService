package org.rest.services;

import org.orm.entities.User;
import org.orm.services.UserOperations;
import org.rest.dtos.AuthForm;
import org.rest.util.EntityToDTO;
import org.rest.util.URIConstants;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;

@Path(URIConstants.KEY_AUTH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {
    @Context
    private HttpServletRequest request;

    private UserOperations userOperations;

    public LoginService(){
        userOperations = new UserOperations();
    }

    @POST
    public Response login(AuthForm authForm)
    {
        String email = authForm.getEmail();
        String password = authForm.getPassword();
        try{

            if(email != null && password != null){
                User user = userOperations.getUserByEmailAndPassword(email, passwordDigest(password));
                if(user != null){
                    SecurityContext context = getSecurityContext(user);
                    request.getSession(true).setAttribute("session-security-context", context);
                    return Response.ok(EntityToDTO.getUserDTO(user)).build();
                }
            }
        }catch (Exception e){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PUT
    public Response logout()
    {
        request.getSession(true).setAttribute("session-security-context", null);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("changepassword")
    public void changePassword(HashMap<String, String> authForm) throws Exception{
        String email = authForm.get("email");
        String password = authForm.get("password");
        String newPassword = authForm.get("newPassword");
        if(email != null && password != null && newPassword != null && newPassword.trim().length() > 0){
            User user = userOperations.getUserByEmailAndPassword(email, passwordDigest(password));
            if(user != null){
                user.setPassword(passwordDigest(newPassword));
                user.setNeed_password_reset(false);
                userOperations.addOrUpdateUser(user);
            }else {
                throw new WebApplicationException(Response.notModified().build());
            }
        }else {
            throw new WebApplicationException(Response.notModified().build());
        }
    }

    private String passwordDigest(String password){
        try{
            return new String(MessageDigest.getInstance("SHA-256").digest(password.getBytes()));
        }catch (NoSuchAlgorithmException e){
            return password;
        }
    }

    private SecurityContext getSecurityContext(final User user){
        SecurityContext ctx = new SecurityContext() {


            class UserPrincipal implements Principal{
                private String role;
                private String name;
                public UserPrincipal(User user){
                   if(user.isIs_inspector()){
                       role = "ADMIN";
                   }else{
                       //role = "USER";
                       role = "ADMIN";
                   }
                   this.name = user.getId()+"";
                }

                @Override
                public String getName() {
                    return this.name;
                }

                public String getRole(){
                    return this.role;
                }
            }

            private UserPrincipal userPrincipal = new UserPrincipal(user);

            @Override
            public UserPrincipal getUserPrincipal() {
                return userPrincipal;
            }

            @Override
            public boolean isUserInRole(String s) {
                return userPrincipal.getRole().equals(s);
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        };

        return ctx;
    }
}
