package org.rest.services;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.orm.entities.User;
import org.orm.services.UserOperations;
import org.rest.dtos.UserDTO;
import org.rest.util.DTOToEntity;
import org.rest.util.EntityToDTO;
import org.rest.util.URIConstants;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path(URIConstants.KEY_USERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersService{

    private UserOperations userOperations;

    @Context
    private SecurityContext securityContext;

    public UsersService(){
        userOperations = new UserOperations();
    }

    @GET
    public ArrayList<UserDTO> getUsers() throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }

        List<User> userEntities = userOperations.getAllUsers();
        if(userEntities == null){
            return null;
        }
        ArrayList<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for(User user : userEntities){
            userDTOs.add(EntityToDTO.getUserDTO(user));
        }
        return userDTOs;
    }

    @Path("{userID}")
    @GET
    public UserDTO getUser(@PathParam("userID") Integer userID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getUserDTO(userOperations.getUser(userID));
    }


    @Path("{userID}")
    @DELETE
    public void deleteUser(@PathParam("userID") Integer userID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        userOperations.deleteUser(userID);
    }

    @PUT
    public void updateUser(UserDTO userDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        User user = userOperations.getUser(userDTO.getId());
        userDTO.setPassword(user.getPassword());
        userOperations.addOrUpdateUser(DTOToEntity.getUserEntity(userDTO));
    }

    @GET
    @Path("resetpassword/{email}")
    public void resetPassword(@PathParam("email") String email) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }

        User user = userOperations.getUserByEmail(email);
        if(user != null){
            user.setPassword(UsersService.passwordDigest("password"));
            user.setNeed_password_reset(false);
            userOperations.addOrUpdateUser(user);
        }else {
            throw new WebApplicationException(Response.notModified().build());
        }
    }

    @POST
    public void addUser(UserDTO userDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        userDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        userDTO.setPassword(passwordDigest(userDTO.getPassword()));
        userOperations.addOrUpdateUser(DTOToEntity.getUserEntity(userDTO));
    }

    public static String passwordDigest(String password){
        try{
            return Base64.encode(MessageDigest.getInstance("SHA-256").digest(password.getBytes()));
        }catch (NoSuchAlgorithmException e){
            return password;
        }
    }

    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
