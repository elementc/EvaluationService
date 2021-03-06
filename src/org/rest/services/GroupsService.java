package org.rest.services;

import org.orm.entities.Group;
import org.orm.services.GroupOperations;
import org.orm.services.QueryService;
import org.rest.services.dtos.GroupDTO;
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
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
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
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getGroupDTO(groupOperations.getGroup(groupID));
    }


    @Path("{groupID}")
    @DELETE
    public void deleteGroup(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupOperations.deleteGroup(groupID);
    }

    @PUT
    public void updateGroup(GroupDTO groupDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupOperations.addOrUpdateGroup(DTOToEntity.getGroupEntity(groupDTO));
    }

    @POST
    public void addGroup(GroupDTO groupDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        groupOperations.addOrUpdateGroup(DTOToEntity.getGroupEntity(groupDTO));
    }


    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
