package org.rest.services;

import org.orm.entities.*;
import org.orm.services.QueryService;
import org.orm.services.UserOperations;
import org.rest.services.dtos.*;
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
import java.util.HashMap;
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

    @POST
    @Path("resetpassword")
    public void updateUser(HashMap<String, String> resetForm) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        String email = getUser().getEmail();
        String password = resetForm.get("password");
        String newPassword = resetForm.get("newPassword");

        if(email != null && password != null && newPassword != null && newPassword.trim().length() > 0){
            UserOperations userOperations = new UserOperations();
            User user = userOperations.getUserByEmailAndPassword(email, UsersService.passwordDigest(password));
            if(user != null){
                user.setPassword(UsersService.passwordDigest(newPassword));
                user.setNeed_password_reset(false);
                userOperations.addOrUpdateUser(user);
            }else {
                throw new WebApplicationException(Response.notModified().build());
            }
        }else {
            throw new WebApplicationException(Response.notModified().build());
        }
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

            User userCheck = queryService.getUserByEmail(userDTO.getEmail());
            if(userCheck != null && userCheck.getId() != userDTO.getId()){
                invalidRequest("user already exist with that email address");
            }

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
        }else{
            List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>();
            for (Course course : courses){
                courseDTOs.add(EntityToDTO.getCourseDTO(course));
            }
            return courseDTOs;
        }
    }

    @GET
    @Path("allcourses")
    public List<CourseDTO> getAllCourses() throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }

        List<Course> courses = queryService.getCourses();
        if(courses == null){
            return null;
        }else {
            List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>();
            for (Course course : courses) {
                courseDTOs.add(EntityToDTO.getCourseDTO(course));
            }
            return courseDTOs;
        }
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
    @Path("groups/subscribe/{groupID}")
    public void subscribeToGroup(@PathParam("groupID") Integer groupID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());

        GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
        groupMemberDTO.setGroup_id(groupID);
        groupMemberDTO.setUser_id(userID);
        groupMemberDTO.setId(0);

        queryService.addGroupMember(DTOToEntity.getGroupMemberEntity(groupMemberDTO));
    }


    @GET
    @Path("groups/creategroup/{groupname}/{courseID}")
    public void createGroup(@PathParam("groupname") String groupname, @PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setCourse_id(courseID);
        groupDTO.setName(groupname);
        groupDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
        queryService.addGroup(DTOToEntity.getGroupEntity(groupDTO));
    }

    @GET
    @Path("groups/bycourseid/{courseID}")
    public List<GroupDTO> getGroupByCourseID(@PathParam("courseID") Integer courseID) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("USER") && !securityContext.isUserInRole("ADMIN"))){
            unauthorized();
        }

        List<Group> groups = queryService.getGroupsByCouseID(courseID);
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
    @Path("member_evaluations")
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
    @Path("group_evaluations")
    public void addOrUpdateGroupEvaluations(GroupEvaluationDTO groupEvaluationDTO) throws Exception{
        if(securityContext == null || (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("USER"))){
            unauthorized();
        }
        int userID = Integer.parseInt(securityContext.getUserPrincipal().getName());

        if(userID == groupEvaluationDTO.getEvaluator_id()){
            GroupDTO group = getGroup(groupEvaluationDTO.getGroup_id());
            if(group != null){
                groupEvaluationDTO.setCreated_on(new Timestamp(System.currentTimeMillis()));
                queryService.addOrUpdateGroupEvaluation(DTOToEntity.getGroupEvaluationEntity(groupEvaluationDTO));
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
