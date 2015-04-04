package org.rest.services;

import org.orm.entities.Group;
import org.orm.entities.User;
import org.orm.services.GroupOperations;
import org.orm.services.QueryService;
import org.rest.dtos.GroupDTO;
import org.rest.dtos.UserDTO;
import org.rest.util.DTOToEntity;
import org.rest.util.EntityToDTO;
import org.rest.util.URIConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path(URIConstants.KEY_GROUPS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsService {

    private QueryService queryService;
    private GroupOperations groupOperations;

    @Context
    private SecurityContext securityContext;

    public GroupsService(){
        groupOperations = new GroupOperations();
        queryService = new QueryService();
    }

    @GET
    public ArrayList<GroupDTO> getGroups() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<Group> groupEntities = groupOperations.getAllGroups();
        if(groupEntities == null){
            return null;
        }
        ArrayList<GroupDTO> groupDTOs = new ArrayList<GroupDTO>();
        for(Group group : groupEntities){
            groupDTOs.add(EntityToDTO.getGroupDTO(group));
        }
        return groupDTOs;
    }

    @Path("{groupID}")
    @GET
    public GroupDTO getGroup(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        return EntityToDTO.getGroupDTO(groupOperations.getGroup(groupID));
    }


    @Path("{groupID}")
    @DELETE
    public void deleteGroup(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        groupOperations.deleteGroup(groupID);
    }

    @PUT
    public void updateGroup(GroupDTO groupDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        groupOperations.addOrUpdateGroup(DTOToEntity.getGroupEntity(groupDTO));
    }

    @POST
    public void addGroup(GroupDTO groupDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        groupDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        groupOperations.addOrUpdateGroup(DTOToEntity.getGroupEntity(groupDTO));
    }

    @GET
    @Path("GetGroupsByCourseID/{courseID}")
    public List<GroupDTO> getGroupsByCourseID(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<Group> groups = queryService.getGroupsByCourseID(courseID);
        List<GroupDTO> groupDTOs = null;
        if(groups != null){
            groupDTOs = new ArrayList<GroupDTO>();
            for (Group group : groups){
                groupDTOs.add(EntityToDTO.getGroupDTO(group));
            }
        }
        return groupDTOs;
    }

    @GET
    @Path("GetGroupsByUserID/{userID}")
    public List<GroupDTO> getGroupsByUserID(@PathParam("userID") Integer userID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<Group> groups = queryService.getGroupsByUserID(userID);
        List<GroupDTO> groupDTOs = null;
        if(groups != null){
            groupDTOs = new ArrayList<GroupDTO>();
            for (Group group : groups){
                groupDTOs.add(EntityToDTO.getGroupDTO(group));
            }
        }
        return groupDTOs;
    }

    @GET
    @Path("GetUsersByGroupID/{groupID}")
    public List<UserDTO> getUsersByGroupID(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<User> users = queryService.getUsersByGroupID(groupID);

        List<UserDTO> userDTOs = null;

        if(users != null){
            userDTOs = new ArrayList<UserDTO>();
            for (User user : users){
                userDTOs.add(EntityToDTO.getUserDTO(user));
            }
        }
        return userDTOs;
    }

    private void unauthorized(String response){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"" + response + "\"}");
        throw new WebApplicationException(builder.build());
    }
}
