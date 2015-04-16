package org.rest.util;


import org.orm.entities.*;
import org.rest.dtos.*;

public class DTOToEntity {

    public static Course getCourseEntity(CourseDTO courseDTO){
        Course course = new Course(courseDTO.getCode(), courseDTO.getTitle(), courseDTO.getTerm(), courseDTO.getYear(), courseDTO.getCreated_on());
        course.setId(courseDTO.getId());
        return course;
    }

    public static User getUserEntity(UserDTO userDTO){
        User user = new User(userDTO.getEmail(), userDTO.getPassword(),userDTO.isNeed_password_reset(), userDTO.getFullname(), userDTO.isIs_inspector(), userDTO.getCreated_on());
        user.setId(userDTO.getId());
        return user;
    }

    public static Group getGroupEntity(GroupDTO groupDTO){
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setCreated_on(groupDTO.getCreated_on());
        Course course = new Course();
        course.setId(groupDTO.getCourse_id());
        group.setCourse(course);
        return group;
    }

    public static GroupMember getGroupMemberEntity(GroupMemberDTO groupMemberDTO){
        GroupMember groupMember = new GroupMember();
        Group group = new Group();
        group.setId(groupMemberDTO.getGroup_id());
        User user = new User();
        user.setId(groupMemberDTO.getUser_id());
        groupMember.setId(groupMemberDTO.getId());
        groupMember.setGroup(group);
        groupMember.setUser(user);
        return groupMember;
    }

    public static EvaluationStage getEvaluationStageEntity(EvaluationStageDTO evaluationStageDTO){
        EvaluationStage evaluationStage = new EvaluationStage();
        evaluationStage.setId(evaluationStageDTO.getId());
        evaluationStage.setName(evaluationStageDTO.getName());
        evaluationStage.setDescription(evaluationStageDTO.getDescription());
        evaluationStage.setStart_date(evaluationStageDTO.getStart_date());
        evaluationStage.setEnd_date(evaluationStageDTO.getEnd_date());
        evaluationStage.setIs_open(evaluationStageDTO.isIs_open());
        evaluationStage.setCreated_on(evaluationStageDTO.getCreated_on());
        Course course = new Course();
        course.setId(evaluationStageDTO.getCourse_id());
        evaluationStage.setCourse(course);
        return evaluationStage;
    }


    public static MemberEvaluation getMemberEvaluationEntity(MemberEvaluationDTO memberEvaluationDTO){
        MemberEvaluation memberEvaluation = new MemberEvaluation();
        memberEvaluation.setId(memberEvaluationDTO.getId());
        memberEvaluation.setResponsibilities(memberEvaluationDTO.getResponsibilities());
        memberEvaluation.setTask_completeness(memberEvaluationDTO.getTask_completeness());
        memberEvaluation.setTask_completeness_details(memberEvaluationDTO.getTask_completeness_details());
        memberEvaluation.setParticipation(memberEvaluationDTO.getParticipation());
        memberEvaluation.setParticipation_details(memberEvaluationDTO.getParticipation_details());
        memberEvaluation.setGrade(memberEvaluationDTO.getGrade());
        memberEvaluation.setCreated_on(memberEvaluationDTO.getCreated_on());
        memberEvaluation.setPercentage(memberEvaluationDTO.getPercentage());
        EvaluationStage evaluationStage = new EvaluationStage();
        evaluationStage.setId(memberEvaluationDTO.getEvaluation_stage_id());
        memberEvaluation.setEvalutionStage(evaluationStage);
        User evaluator = new User();
        evaluator.setId(memberEvaluationDTO.getEvaluator_id());
        memberEvaluation.setEvaluator(evaluator);
        User evalutee = new User();
        evalutee.setId(memberEvaluationDTO.getEvaluatee_id());
        memberEvaluation.setEvaluatee(evalutee);
        Group group = new Group();
        group.setId(memberEvaluationDTO.getGroups_id());
        memberEvaluation.setGroup(group);
        return memberEvaluation;
    }

    public static GroupEvaluation getGroupEvaluationEntity(GroupEvaluationDTO groupEvaluationDTO){
        GroupEvaluation groupEvaluation = new GroupEvaluation();
        groupEvaluation.setId(groupEvaluationDTO.getId());
        groupEvaluation.setGoals(groupEvaluationDTO.getGoals());
        groupEvaluation.setGoals_details(groupEvaluationDTO.getGoals_details());
        groupEvaluation.setOpenness(groupEvaluationDTO.getOpenness());
        groupEvaluation.setOpenness_details(groupEvaluationDTO.getOpenness_details());
        groupEvaluation.setMutual_trust(groupEvaluationDTO.getMutual_trust());
        groupEvaluation.setMutual_trust_details(groupEvaluationDTO.getMutual_trust_details());
        groupEvaluation.setDifference_attitude(groupEvaluationDTO.getDifference_attitude());
        groupEvaluation.setDifference_attitude_details(groupEvaluationDTO.getDifference_attitude_details());
        groupEvaluation.setSupport(groupEvaluationDTO.getSupport());
        groupEvaluation.setSupport_details(groupEvaluationDTO.getSupport_details());
        groupEvaluation.setParticipation(groupEvaluationDTO.getParticipation());
        groupEvaluation.setParticipation_details(groupEvaluationDTO.getParticipation_details());
        groupEvaluation.setDecision_making(groupEvaluationDTO.getDecision_making());
        groupEvaluation.setDecision_making_details(groupEvaluationDTO.getDecision_making_details());
        groupEvaluation.setFlexibility(groupEvaluationDTO.getFlexibility());
        groupEvaluation.setFlexibility_details(groupEvaluationDTO.getFlexibility_details());
        groupEvaluation.setResource_use(groupEvaluationDTO.getResource_use());
        groupEvaluation.setResource_use_details(groupEvaluationDTO.getResource_use_details());
        groupEvaluation.setGrade(groupEvaluationDTO.getGrade());
        groupEvaluation.setPercentage(groupEvaluationDTO.getPercentage());
        groupEvaluation.setCreated_on(groupEvaluationDTO.getCreated_on());
        EvaluationStage evaluationStage = new EvaluationStage();
        evaluationStage.setId(groupEvaluationDTO.getEvaluation_stage_id());
        groupEvaluation.setEvalutionStage(evaluationStage);
        User evaluator = new User();
        evaluator.setId(groupEvaluationDTO.getEvaluator_id());
        groupEvaluation.setEvaluator(evaluator);
        Group group = new Group();
        group.setId(groupEvaluationDTO.getId());
        groupEvaluation.setGroup(group);
        return groupEvaluation;
    }
}
