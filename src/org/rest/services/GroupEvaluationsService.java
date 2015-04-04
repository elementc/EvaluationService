package org.rest.services;

import org.orm.entities.GroupEvaluation;
import org.orm.entities.GroupMember;
import org.orm.entities.MemberEvaluation;
import org.orm.services.GroupEvaluationOperations;
import org.orm.services.GroupMemberOperations;
import org.orm.services.QueryService;
import org.rest.dtos.GroupEvaluationDTO;
import org.rest.dtos.GroupMemberDTO;
import org.rest.dtos.MemberEvaluationDTO;
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

@Path(URIConstants.KEY_GROUP_EVALUATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupEvaluationsService {
    @Context
    private SecurityContext securityContext;
    private GroupEvaluationOperations groupEvaluationOperations;
    private QueryService queryService;

    public GroupEvaluationsService(){
        groupEvaluationOperations = new GroupEvaluationOperations();
        queryService = new QueryService();
    }

    @GET
    public List<GroupEvaluationDTO> getGroupEvaluations() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<GroupEvaluation> groupEvaluations = groupEvaluationOperations.getAllGroupEvaluations();
        if(groupEvaluations == null){
            return null;
        }
        ArrayList<GroupEvaluationDTO> groupEvaluationDTOs = new ArrayList<GroupEvaluationDTO>();
        for(GroupEvaluation groupEvaluation : groupEvaluations){
            groupEvaluationDTOs.add(EntityToDTO.getGroupEvaluationDTO(groupEvaluation));
        }
        return groupEvaluationDTOs;
    }

    @Path("{groupEvaluationID}")
    @GET
    public GroupEvaluationDTO getGroupEvaluation(@PathParam("groupEvaluationID") Integer groupEvaluationID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        return EntityToDTO.getGroupEvaluationDTO(groupEvaluationOperations.getGroupEvaluation(groupEvaluationID));
    }


    @Path("{groupEvaluationID}")
    @DELETE
    public void deleteGroupEvaluation(@PathParam("groupEvaluationID") Integer groupEvaluationID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        groupEvaluationOperations.deleteGroupEvaluation(groupEvaluationID);
    }

    @PUT
    public void updateGroupEvaluation(GroupEvaluationDTO groupEvaluation) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        groupEvaluationOperations.addOrUpdateGroupEvaluation(DTOToEntity.getGroupEvaluationEntity(groupEvaluation));
    }

    @POST
    public void addGroupEvaluation(GroupEvaluationDTO groupEvaluation) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        groupEvaluation.setCreated_on(new Timestamp(System.currentTimeMillis()));
        groupEvaluationOperations.addOrUpdateGroupEvaluation(DTOToEntity.getGroupEvaluationEntity(groupEvaluation));
    }

    @GET
    @Path("GetGroupEvaluationsByUserID/{userID}")
    public List<GroupEvaluationDTO> getGroupEvaluationsByUserID(@PathParam("userID") Integer userID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        List<GroupEvaluation> groupEvaluations = queryService.getGroupEvaluationsByUserID(userID);
        if(groupEvaluations == null){
            return null;
        }
        List<GroupEvaluationDTO> groupEvaluationDTOs = null;

        if(groupEvaluations != null){
            groupEvaluationDTOs = new ArrayList<GroupEvaluationDTO>();
            for (GroupEvaluation groupEvaluation : groupEvaluations){
                groupEvaluationDTOs.add(EntityToDTO.getGroupEvaluationDTO(groupEvaluation));
            }
        }

        return groupEvaluationDTOs;
    }

    private void unauthorized(String response){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"" + response + "\"}");
        throw new WebApplicationException(builder.build());
    }
}
