
package org.orm.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity  
@Table(name= "evaluation_stages")
public class EvaluationStage implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private boolean is_open;
    private Timestamp created_on;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public EvaluationStage() {
    }

    public EvaluationStage(String name, String description, Date start_date, Date end_date, boolean is_open, Timestamp created_on, Course course) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.is_open = is_open;
        this.created_on = created_on;
        this.course = course;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getValidationError(){
        if(name == null || name.trim().length() == 0){
            return "Missing evaluation stage name";
        }

        if(description == null || description.trim().length() == 0){
            return "Missing evaluation stage description";
        }

        if(created_on == null){
            return "Missing evaluation stage creation timestamp";
        }

        if(start_date == null){
            return "Missing evaluation stage start date";
        }

        if(end_date == null){
            return "Missing evaluation stage end date";
        }

        if(course == null || course.getId() == 0){
            return "Missing course that this evaluation stage belongs to";
        }
        return null;
    }
}
