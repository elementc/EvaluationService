package org.rest.services;

import org.orm.entities.GroupMember;
import org.orm.services.GroupMemberOperations;
import org.rest.services.dtos.GroupMemberDTO;
import org.rest.util.DTOToEntity;
import org.rest.util.EntityToDTO;
import org.rest.util.URIConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path(URIConstants.KEY_GROUP_MEMBERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupMembersService {

    private GroupMemberOperations groupMemberOperations;

    @Context
    private SecurityContext securityContext;

    public GroupMembersService(){
        groupMemberOperations = new GroupMemberOperations();
    }

    @GET
    public List<GroupMemberDTO> getGroupMembers() throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        List<GroupMember> groupMembers = groupMemberOperations.getAllGroupMember();
        if(groupMembers == null){
            return null;
        }
        ArrayList<GroupMemberDTO> groupMemberDTOs = new ArrayList<GroupMemberDTO>();
        for(GroupMember groupMember : groupMembers){
            groupMemberDTOs.add(EntityToDTO.getGroupMemberDTO(groupMember));
        }
        return groupMemberDTOs;
    }

    @Path("{groupMemberID}")
    @GET
    public GroupMemberDTO getGroupMember(@PathParam("groupMemberID") Integer groupMemberID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getGroupMemberDTO(groupMemberOperations.getGroupMember(groupMemberID));
    }


    @Path("{groupMemberID}")
    @DELETE
    public void deleteGroupMember(@PathParam("groupMemberID") Integer groupMemberID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupMemberOperations.deleteGroupMember(groupMemberID);
    }

    @PUT
    public void updateGroupMember(GroupMemberDTO groupMember) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupMemberOperations.addOrUpdateGroupMember(DTOToEntity.getGroupMemberEntity(groupMember));
    }

    @POST
    public void addGroupMember(GroupMemberDTO groupMember) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        groupMemberOperations.addOrUpdateGroupMember(DTOToEntity.getGroupMemberEntity(groupMember));
    }

    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
