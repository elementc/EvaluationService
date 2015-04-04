package org.rest.util;


import org.orm.entities.*;
import org.rest.dtos.*;

public class EntityToDTO {

    public static CourseDTO getCourseDTO(Course course){
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setCode(course.getCode());
        courseDTO.setCreated_on(course.getCreated_on());
        courseDTO.setTerm(course.getTerm());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setYear(course.getYear());

        return courseDTO;
    }

    public static UserDTO getUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setCreated_on(user.getCreated_on());
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullname(user.getFullname());
        userDTO.setNeed_password_reset(user.isNeed_password_reset());
        userDTO.setIs_inspector(user.isIs_inspector());

        return userDTO;
    }

    public static GroupDTO getGroupDTO(Group group){
        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setId(group.getId());
        groupDTO.setCourse_id(group.getCourse().getId());
        groupDTO.setCreated_on(group.getCreated_on());
        groupDTO.setName(group.getName());

        return groupDTO;
    }

    public static GroupMemberDTO getGroupMemberDTO(GroupMember groupMember){
        GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
        groupMemberDTO.setId(groupMember.getId());
        groupMemberDTO.setGroup_id(groupMember.getGroup().getId());
        groupMemberDTO.setUser_id(groupMember.getUser().getId());
        return groupMemberDTO;
    }

    public static EvaluationStageDTO getEvaluationStageDTO(EvaluationStage evaluationStage){
        EvaluationStageDTO evaluationStageDTO = new EvaluationStageDTO();
        evaluationStageDTO.setId(evaluationStage.getId());
        evaluationStageDTO.setName(evaluationStage.getName());
        evaluationStageDTO.setDescription(evaluationStage.getDescription());
        evaluationStageDTO.setStart_date(evaluationStage.getStart_date());
        evaluationStageDTO.setEnd_date(evaluationStage.getEnd_date());
        evaluationStageDTO.setCreated_on(evaluationStage.getCreated_on());
        evaluationStageDTO.setCourse_id(evaluationStage.getCourse().getId());
        evaluationStageDTO.setIs_open(evaluationStage.isIs_open());
        return evaluationStageDTO;
    }

    public static MemberEvaluationDTO getMemberEvaluationDTO(MemberEvaluation memberEvaluation){
        MemberEvaluationDTO memberEvaluationDTO = new MemberEvaluationDTO();
        memberEvaluationDTO.setId(memberEvaluation.getId());
        memberEvaluationDTO.setResponsibilities(memberEvaluation.getResponsibilities());
        memberEvaluationDTO.setTask_completeness(memberEvaluation.getTask_completeness());
        memberEvaluationDTO.setTask_completeness_details(memberEvaluation.getTask_completeness_details());
        memberEvaluationDTO.setParticipation(memberEvaluation.getParticipation());
        memberEvaluationDTO.setParticipation_details(memberEvaluation.getParticipation_details());
        memberEvaluationDTO.setGrade(memberEvaluation.getGrade());
        memberEvaluationDTO.setPercentage(memberEvaluation.getPercentage());
        memberEvaluationDTO.setEvaluation_stage_id(memberEvaluation.getEvalutionStage().getId());
        memberEvaluationDTO.setCreated_on(memberEvaluation.getCreated_on());
        memberEvaluationDTO.setEvaluator_id(memberEvaluation.getEvaluator().getId());
        memberEvaluationDTO.setEvalutee_id(memberEvaluation.getEvalutee().getId());
        memberEvaluationDTO.setGroups_id(memberEvaluation.getGroup().getId());
        return memberEvaluationDTO;
    }

    public static GroupEvaluationDTO getGroupEvaluationDTO(GroupEvaluation groupEvaluation){
        GroupEvaluationDTO groupEvaluationDTO = new GroupEvaluationDTO();
        groupEvaluationDTO.setId(groupEvaluation.getId());
        groupEvaluationDTO.setGoals(groupEvaluation.getGoals());
        groupEvaluationDTO.setGoals_details(groupEvaluation.getGoals_details());
        groupEvaluationDTO.setOpenness(groupEvaluation.getOpenness());
        groupEvaluationDTO.setOpenness_details(groupEvaluation.getOpenness_details());
        groupEvaluationDTO.setMutual_trust_details(groupEvaluation.getMutual_trust_details());
        groupEvaluationDTO.setMutual_trust(groupEvaluation.getMutual_trust());
        groupEvaluationDTO.setDifference_attitude_details(groupEvaluation.getDifference_attitude_details());
        groupEvaluationDTO.setDifference_attitude(groupEvaluation.getDifference_attitude());
        groupEvaluationDTO.setSupport(groupEvaluation.getSupport());
        groupEvaluationDTO.setSupport_details(groupEvaluation.getSupport_details());
        groupEvaluationDTO.setParticipation(groupEvaluation.getParticipation());
        groupEvaluationDTO.setParticipation_details(groupEvaluation.getParticipation_details());
        groupEvaluationDTO.setDecision_making(groupEvaluation.getDecision_making());
        groupEvaluationDTO.setDecision_making_details(groupEvaluation.getDecision_making_details());
        groupEvaluationDTO.setFlexibility(groupEvaluation.getFlexibility());
        groupEvaluationDTO.setFlexibility_details(groupEvaluation.getFlexibility_details());
        groupEvaluationDTO.setResource_use(groupEvaluation.getResource_use());
        groupEvaluationDTO.setResource_use_details(groupEvaluation.getResource_use_details());
        groupEvaluationDTO.setGrade(groupEvaluation.getGrade());
        groupEvaluationDTO.setPercentage(groupEvaluation.getPercentage());
        groupEvaluationDTO.setEvaluation_stage_id(groupEvaluation.getEvalutionStage().getId());
        groupEvaluationDTO.setCreated_on(groupEvaluation.getCreated_on());
        groupEvaluationDTO.setEvaluator_id(groupEvaluation.getEvaluator().getId());
        groupEvaluationDTO.setGroup_id(groupEvaluation.getGroup().getId());
        return groupEvaluationDTO;
    }
}
