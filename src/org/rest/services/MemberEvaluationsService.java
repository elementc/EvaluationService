package org.rest.services;

import org.orm.entities.Group;
import org.orm.entities.MemberEvaluation;
import org.orm.services.MemberEvaluationOperations;
import org.orm.services.QueryService;
import org.rest.dtos.GroupDTO;
import org.rest.dtos.GroupEvaluationDTO;
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

@Path(URIConstants.KEY_MEMBER_EVALUATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberEvaluationsService {

    private MemberEvaluationOperations memberEvaluationOperations;

    @Context
    private SecurityContext securityContext;

    public MemberEvaluationsService(){
        memberEvaluationOperations = new MemberEvaluationOperations();
    }

    @GET
    public List<MemberEvaluationDTO> getMemberEvaluations() throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        List<MemberEvaluation> memberEvaluations = memberEvaluationOperations.getAllMemberEvaluations();
        if(memberEvaluations == null){
            return null;
        }
        ArrayList<MemberEvaluationDTO> memberEvaluationDTOs = new ArrayList<MemberEvaluationDTO>();
        for(MemberEvaluation memberEvaluation : memberEvaluations){
            memberEvaluationDTOs.add(EntityToDTO.getMemberEvaluationDTO(memberEvaluation));
        }
        return memberEvaluationDTOs;
    }

    @Path("{memberEvaluationID}")
    @GET
    public MemberEvaluationDTO getMemberEvaluation(@PathParam("memberEvaluationID") Integer memberEvaluationID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getMemberEvaluationDTO(memberEvaluationOperations.getMemberEvaluation(memberEvaluationID));
    }


    @Path("{memberEvaluationID}")
    @DELETE
    public void deleteMemberEvaluation(@PathParam("memberEvaluationID") Integer memberEvaluationID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        memberEvaluationOperations.deleteMemberEvaluation(memberEvaluationID);
    }

    @PUT
    public void updateMemberEvaluation(MemberEvaluationDTO memberEvaluation) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        memberEvaluationOperations.addOrUpdateMemberEvaluation(DTOToEntity.getMemberEvaluationEntity(memberEvaluation));
    }

    @POST
    public void addMemberEvaluation(MemberEvaluationDTO memberEvaluation) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        memberEvaluation.setCreated_on(new Timestamp(System.currentTimeMillis()));
        memberEvaluationOperations.addOrUpdateMemberEvaluation(DTOToEntity.getMemberEvaluationEntity(memberEvaluation));
    }



    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
