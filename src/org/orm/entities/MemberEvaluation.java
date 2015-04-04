
package org.orm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity  
@Table(name= "member_evaluations")
public class MemberEvaluation implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private String name;
    private String responsibilities;
    private String task_completeness;
    private String task_completeness_details;
    private char participation;
    private String participation_details;
    private char grade;
    private int percentage;

    @ManyToOne
    @JoinColumn(name="evaluation_stage_id")
    private EvaluationStage evalutionStage;
    private Timestamp created_on;

    @ManyToOne
    @JoinColumn(name="evaluator_id")
    private User evaluator;

    @ManyToOne
    @JoinColumn(name="evalutee_id")
    private User evalutee;

    @ManyToOne
    @JoinColumn(name="groups_id")
    private Group group;

    public MemberEvaluation() {
    }

    public MemberEvaluation(String name, String responsibilities, String task_completeness, String task_completeness_details, char participation, String participation_details, char grade, int percentage, EvaluationStage evalutionStage, Timestamp created_on, User evaluator, User evalutee, Group group) {
        this.name = name;
        this.responsibilities = responsibilities;
        this.task_completeness = task_completeness;
        this.task_completeness_details = task_completeness_details;
        this.participation = participation;
        this.participation_details = participation_details;
        this.grade = grade;
        this.percentage = percentage;
        this.evalutionStage = evalutionStage;
        this.created_on = created_on;
        this.evaluator = evaluator;
        this.evalutee = evalutee;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public EvaluationStage getEvalutionStage() {
        return evalutionStage;
    }

    public void setEvalutionStage(EvaluationStage evalutionStage) {
        this.evalutionStage = evalutionStage;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }

    public User getEvalutee() {
        return evalutee;
    }

    public void setEvalutee(User evalutee) {
        this.evalutee = evalutee;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
