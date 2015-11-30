package org.rest.services.dtos;

import java.sql.Timestamp;

public class MemberEvaluationDTO {
    private int id;
    private String responsibilities;
    private String task_completeness;
    private String task_completeness_details;
    private char participation;
    private String participation_details;
    private char grade;
    private int percentage;
    private int evaluation_stage_id;
    private Timestamp created_on;
    private int evaluator_id;
    private int evaluatee_id;
    private int groups_id;

    public MemberEvaluationDTO(){

    }

    public MemberEvaluationDTO(int id, String responsibilities, String task_completeness, String task_completeness_details, char participation, String participation_details, char grade, int percentage, int evaluation_stage_id, Timestamp created_on, int evaluator_id, int evaluatee_id, int groups_id) {
        this.id = id;
        this.responsibilities = responsibilities;
        this.task_completeness = task_completeness;
        this.task_completeness_details = task_completeness_details;
        this.participation = participation;
        this.participation_details = participation_details;
        this.grade = grade;
        this.percentage = percentage;
        this.evaluation_stage_id = evaluation_stage_id;
        this.created_on = created_on;
        this.evaluator_id = evaluator_id;
        this.evaluatee_id = evaluatee_id;
        this.groups_id = groups_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getTask_completeness() {
        return task_completeness;
    }

    public void setTask_completeness(String task_completeness) {
        this.task_completeness = task_completeness;
    }

    public String getTask_completeness_details() {
        return task_completeness_details;
    }

    public void setTask_completeness_details(String task_completeness_details) {
        this.task_completeness_details = task_completeness_details;
    }

    public char getParticipation() {
        return participation;
    }

    public void setParticipation(char participation) {
        this.participation = participation;
    }

    public String getParticipation_details() {
        return participation_details;
    }

    public void setParticipation_details(String participation_details) {
        this.participation_details = participation_details;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getEvaluation_stage_id() {
        return evaluation_stage_id;
    }

    public void setEvaluation_stage_id(int evaluation_stage_id) {
        this.evaluation_stage_id = evaluation_stage_id;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public int getEvaluator_id() {
        return evaluator_id;
    }

    public void setEvaluator_id(int evaluator_id) {
        this.evaluator_id = evaluator_id;
    }

    public int getEvaluatee_id() {
        return evaluatee_id;
    }

    public void setEvaluatee_id(int evaluatee_id) {
        this.evaluatee_id = evaluatee_id;
    }

    public int getGroups_id() {
        return groups_id;
    }

    public void setGroups_id(int groups_id) {
        this.groups_id = groups_id;
    }
}
