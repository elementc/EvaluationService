
package org.orm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity  
@Table(name= "group_evaluations")
public class GroupEvaluation implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    private char goals;
    private String goals_details;
    private char openness;
    private String openness_details;
    private char mutual_trust;
    private String mutual_trust_details;
    private char difference_attitude;
    private String difference_attitude_details;
    private char support;
    private String support_details;
    private char participation;
    private String participation_details;
    private char decision_making;
    private String decision_making_details;
    private char flexibility;
    private String flexibility_details;
    private char resource_use;
    private String resource_use_details;
    private char grade;
    private int percentage;

    @ManyToOne
    @JoinColumn(name = "evaluation_stage_id")
    private EvaluationStage evalutionStage;
    private Timestamp created_on;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private User evaluator;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    public GroupEvaluation() {

    }

    public GroupEvaluation(char goals, String goals_details, char openness, String openness_details, char mutual_trust, String mutual_trust_details, char difference_attitude, String difference_attitude_details, char support, String support_details, char participation, String participation_details, char decision_making, String decision_making_details, char flexibility, String flexibility_details, char resource_use, String resource_use_details, char grade, int percentage, EvaluationStage evalutionStage, Timestamp created_on, User evaluator, Group group) {
        this.goals = goals;
        this.goals_details = goals_details;
        this.openness = openness;
        this.openness_details = openness_details;
        this.mutual_trust = mutual_trust;
        this.mutual_trust_details = mutual_trust_details;
        this.difference_attitude = difference_attitude;
        this.difference_attitude_details = difference_attitude_details;
        this.support = support;
        this.support_details = support_details;
        this.participation = participation;
        this.participation_details = participation_details;
        this.decision_making = decision_making;
        this.decision_making_details = decision_making_details;
        this.flexibility = flexibility;
        this.flexibility_details = flexibility_details;
        this.resource_use = resource_use;
        this.resource_use_details = resource_use_details;
        this.grade = grade;
        this.percentage = percentage;
        this.evalutionStage = evalutionStage;
        this.created_on = created_on;
        this.evaluator = evaluator;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getGoals() {
        return goals;
    }

    public void setGoals(char goals) {
        this.goals = goals;
    }

    public String getGoals_details() {
        return goals_details;
    }

    public void setGoals_details(String goals_details) {
        this.goals_details = goals_details;
    }

    public char getOpenness() {
        return openness;
    }

    public void setOpenness(char openness) {
        this.openness = openness;
    }

    public String getOpenness_details() {
        return openness_details;
    }

    public void setOpenness_details(String openness_details) {
        this.openness_details = openness_details;
    }

    public char getMutual_trust() {
        return mutual_trust;
    }

    public void setMutual_trust(char mutual_trust) {
        this.mutual_trust = mutual_trust;
    }

    public String getMutual_trust_details() {
        return mutual_trust_details;
    }

    public void setMutual_trust_details(String mutual_trust_details) {
        this.mutual_trust_details = mutual_trust_details;
    }

    public char getDifference_attitude() {
        return difference_attitude;
    }

    public void setDifference_attitude(char difference_attitude) {
        this.difference_attitude = difference_attitude;
    }

    public String getDifference_attitude_details() {
        return difference_attitude_details;
    }

    public void setDifference_attitude_details(String difference_attitude_details) {
        this.difference_attitude_details = difference_attitude_details;
    }

    public char getSupport() {
        return support;
    }

    public void setSupport(char support) {
        this.support = support;
    }

    public String getSupport_details() {
        return support_details;
    }

    public void setSupport_details(String support_details) {
        this.support_details = support_details;
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

    public char getDecision_making() {
        return decision_making;
    }

    public void setDecision_making(char decision_making) {
        this.decision_making = decision_making;
    }

    public String getDecision_making_details() {
        return decision_making_details;
    }

    public void setDecision_making_details(String decision_making_details) {
        this.decision_making_details = decision_making_details;
    }

    public char getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(char flexibility) {
        this.flexibility = flexibility;
    }

    public String getFlexibility_details() {
        return flexibility_details;
    }

    public void setFlexibility_details(String flexibility_details) {
        this.flexibility_details = flexibility_details;
    }

    public char getResource_use() {
        return resource_use;
    }

    public void setResource_use(char resource_use) {
        this.resource_use = resource_use;
    }

    public String getResource_use_details() {
        return resource_use_details;
    }

    public void setResource_use_details(String resource_use_details) {
        this.resource_use_details = resource_use_details;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}