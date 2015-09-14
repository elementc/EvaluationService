package org.rest.services;

import org.orm.entities.*;
import org.orm.services.QueryService;
import org.orm.services.UserOperations;
import org.rest.dtos.*;
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

@Path(URIConstants.KEY_MOBILE_SERVICE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MobileService {

    private QueryService queryService;

    @Context
    private SecurityContext securityContext;

    public MobileService(){
        queryService = new QueryService();
    }

    @GET
    @Path("user")
    public UserDTO getUser() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());
        return EntityToDTO.getUserDTO(queryService.getUserByUserID(userID));
    }


    @POST
    @Path("user")
    public void updateUser(UserDTO userDTO) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());

        if(userDTO.getId() == userID){
            if(userDTO.getPassword() != null && userDTO.getPassword().trim().length() > 0){
                userDTO.setPassword(UsersService.passwordDigest(userDTO.getPassword()));
                queryService.updateUser(DTOToEntity.getUserEntity(userDTO), true);
            }else{
                queryService.updateUser(DTOToEntity.getUserEntity(userDTO), false);
            }
        }else{
            invalidRequest("user can only update its information");
        }
    }

    @POST
    @Path("user/signup")
    public void signupUser(UserDTO userDTO) throws Exception{
        userDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        userDTO.setIs_inspector(false);
        userDTO.setNeed_password_reset(false);
        userDTO.setPassword(UsersService.passwordDigest(userDTO.getPassword()));
        queryService.addUser(DTOToEntity.getUserEntity(userDTO));
    }

    @GET
    @Path("courses")
    public List<CourseDTO> getCourses() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }

        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());
        List<Course> courses = queryService.getCoursesByUserID(userID);
        if(courses == null){
            return null;
        }
        List<CourseDTO> courseDTOs = null;

        if(courses != null){
            courseDTOs = new ArrayList<CourseDTO>();
            for (Course course : courses){
                courseDTOs.add(EntityToDTO.getCourseDTO(course));
            }
        }
        return courseDTOs;
    }

    @GET
    @Path("courses/{courseID}")
    public CourseDTO getCourse(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        for(CourseDTO dto :  getCourses()){
            if(dto.getId() == courseID){
                return dto;
            }
        }
        return null;
    }

    @GET
    @Path("courses/{courseID}/evaluation_stages")
    public List<EvaluationStageDTO> getEvaluationStages(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        CourseDTO courseDTO = getCourse(courseID);
        if(courseDTO != null){
            List<EvaluationStage> evaluationStages = queryService.getEvaluationStagesByCourseID(courseID);
            if(evaluationStages != null){
                ArrayList<EvaluationStageDTO> evaluationStageDTOs = new ArrayList<EvaluationStageDTO>();
                for (EvaluationStage evaluationStage : evaluationStages){
                    evaluationStageDTOs.add(EntityToDTO.getEvaluationStageDTO(evaluationStage));
                }
                return evaluationStageDTOs;
            }
        }
        return null;
    }

    @GET
    @Path("courses/{courseID}/evaluation_stages/{evaluationStageID}")
    public EvaluationStageDTO getEvaluationStages(@PathParam("courseID") Integer courseID, @PathParam("evaluationStageID") Integer evaluationStageID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        List<EvaluationStageDTO> evaluationStageDTOs = getEvaluationStages(courseID);
        if(evaluationStageDTOs != null){
            for (EvaluationStageDTO evaluationStageDTO : evaluationStageDTOs){
                if(evaluationStageDTO.getId() == evaluationStageID){
                    return evaluationStageDTO;
                }
            }
        }
        return null;
    }

    @GET
    @Path("groups")
    public List<GroupDTO> getGroups() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());
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
    @Path("groups/{groupID}")
    public GroupDTO getGroup(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        for(GroupDTO dto :  getGroups()){
            if(dto.getId() == groupID){
                return dto;
            }
        }
        return null;
    }

    @GET
    @Path("groups/{groupID}/users")
    public List<UserDTO> getGroupUsers(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }

        GroupDTO groupDTO = getGroup(groupID);
        if(groupDTO != null){
            List<User> users = queryService.getUsersByGroupID(groupDTO.getId());
            if(users != null){
                ArrayList<UserDTO> userDTOs = new ArrayList<UserDTO>();
                for(User user : users){
                    userDTOs.add(EntityToDTO.getUserDTO(user));
                }
                return userDTOs;
            }
        }
        return null;
    }

    @GET
    @Path("member_evaluations")
    public List<MemberEvaluationDTO> getMemberEvaluations() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());
        List<MemberEvaluation> memberEvaluations = queryService.getMemberEvaluationsByUserID(userID);

        List<MemberEvaluationDTO> memberEvaluationDTOs = null;

        if(memberEvaluations != null){
            memberEvaluationDTOs = new ArrayList<MemberEvaluationDTO>();
            for (MemberEvaluation memberEvaluation : memberEvaluations){
                memberEvaluationDTOs.add(EntityToDTO.getMemberEvaluationDTO(memberEvaluation));
            }
        }

        return memberEvaluationDTOs;
    }

    @POST
    @Path("member_evaluations/add")
    public void addMemberEvaluation(MemberEvaluationDTO memberEvaluationDTO) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());

        if(userID == memberEvaluationDTO.getEvaluator_id()){
            GroupDTO group = getGroup(memberEvaluationDTO.getGroups_id());
            if(group != null){
                List<UserDTO> groupMembers = getGroupUsers(group.getId());
                if(groupMembers != null){
                    boolean isEvaluateeInGroup = false;
                    for(UserDTO user : groupMembers){
                        if(user.getId() == memberEvaluationDTO.getEvaluatee_id()){
                            isEvaluateeInGroup = true;
                            break;
                        }
                    }
                    if(isEvaluateeInGroup){
                        memberEvaluationDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
                        queryService.addMemberEvaluation(DTOToEntity.getMemberEvaluationEntity(memberEvaluationDTO));
                    }else{
                        invalidRequest("Evaluator can not submit evaluation for user that is not in their group!");
                    }
                }else{
                    invalidRequest("Group does not have any members!");
                }
            }else{
                invalidRequest("User does not belong to the given group!");
            }
        }else{
            invalidRequest("Evaluator and Logged in user do not match!");
        }
    }

    @GET
    @Path("group_evaluations")
    public List<GroupEvaluationDTO> getGroupEvaluations() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());
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


    @POST
    @Path("group_evaluations/add")
    public void getGroupEvaluations(GroupEvaluationDTO groupEvaluationDTO) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());

        if(userID == groupEvaluationDTO.getEvaluator_id()){
            GroupDTO group = getGroup(groupEvaluationDTO.getGroup_id());
            if(group != null){
                groupEvaluationDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
                queryService.addGroupEvaluation(DTOToEntity.getGroupEvaluationEntity(groupEvaluationDTO));
            }else{
                invalidRequest("User does not belong to the given group!");
            }
        }else{
            invalidRequest("Evaluator and Logged in user do not match!");
        }
    }

    private void unauthorized(){
        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Permission Denied\"}");
        throw new WebApplicationException(builder.build());
    }

    private void invalidRequest(String response){
        Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + response + "\"}");
        throw new WebApplicationException(builder.build());
    }
}
