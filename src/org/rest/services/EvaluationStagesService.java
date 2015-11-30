package org.rest.services;

import org.orm.entities.EvaluationStage;
import org.orm.services.EvaluationStageOperations;
import org.rest.services.dtos.EvaluationStageDTO;
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

@Path(URIConstants.KEY_EVALUATION_STAGES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvaluationStagesService {
    @Context
    private SecurityContext securityContext;
    private EvaluationStageOperations evaluationStageOperations;

    public EvaluationStagesService(){
        evaluationStageOperations = new EvaluationStageOperations();
    }

    @GET
    public ArrayList<EvaluationStageDTO> getEvaluationStages() throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        List<EvaluationStage> evaluationStageEntities = evaluationStageOperations.getAllEvaluationStages();
        if(evaluationStageEntities == null){
            return null;
        }
        ArrayList<EvaluationStageDTO> evaluationStageDTOs = new ArrayList<EvaluationStageDTO>();
        for(EvaluationStage evaluationStage : evaluationStageEntities){
            evaluationStageDTOs.add(EntityToDTO.getEvaluationStageDTO(evaluationStage));
        }
        return evaluationStageDTOs;
    }

    @Path("{evaluationStageID}")
    @GET
    public EvaluationStageDTO getEvaluationStage(@PathParam("evaluationStageID") Integer evaluationStageID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        return EntityToDTO.getEvaluationStageDTO(evaluationStageOperations.getEvaluationStage(evaluationStageID));
    }


    @Path("{evaluationStageID}")
    @DELETE
    public void deleteEvaluationStage(@PathParam("evaluationStageID") Integer evaluationStageID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        evaluationStageOperations.deleteEvaluationStage(evaluationStageID);
    }

    @PUT
    public void updateEvaluationStage(EvaluationStageDTO evaluationStageDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        evaluationStageOperations.addOrUpdateEvaluationStage(DTOToEntity.getEvaluationStageEntity(evaluationStageDTO));
    }

    @POST
    public void addEvaluationStage(EvaluationStageDTO evaluationStageDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized();
        }
        evaluationStageDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        evaluationStageOperations.addOrUpdateEvaluationStage(DTOToEntity.getEvaluationStageEntity(evaluationStageDTO));
    }

    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Only admins can access this service\"}");
        throw new WebApplicationException(builder.build());
    }
}
