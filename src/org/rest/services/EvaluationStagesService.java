package org.rest.services;

import org.orm.entities.EvaluationStage;
import org.orm.entities.Group;
import org.orm.services.EvaluationStageOperations;
import org.orm.services.GroupOperations;
import org.rest.dtos.EvaluationStageDTO;
import org.rest.dtos.GroupDTO;
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
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
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
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized("Permission denied!");
        }
        return EntityToDTO.getEvaluationStageDTO(evaluationStageOperations.getEvaluationStage(evaluationStageID));
    }


    @Path("{evaluationStageID}")
    @DELETE
    public void deleteEvaluationStage(@PathParam("evaluationStageID") Integer evaluationStageID) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        evaluationStageOperations.deleteEvaluationStage(evaluationStageID);
    }

    @PUT
    public void updateEvaluationStage(EvaluationStageDTO evaluationStageDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        evaluationStageOperations.addOrUpdateEvaluationStage(DTOToEntity.getEvaluationStageEntity(evaluationStageDTO));
    }

    @POST
    public void addEvaluationStage(EvaluationStageDTO evaluationStageDTO) throws Exception{
        if(securityContext == null || !securityContext.isUserInRole("ADMIN")){
            unauthorized("Permission denied!");
        }
        evaluationStageDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        evaluationStageOperations.addOrUpdateEvaluationStage(DTOToEntity.getEvaluationStageEntity(evaluationStageDTO));
    }

    private void unauthorized(String response){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"" + response + "\"}");
        throw new WebApplicationException(builder.build());
    }
}
