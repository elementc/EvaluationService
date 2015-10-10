
package org.orm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity  
@Table(name= "groups")
public class Group implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private String name;

    private Timestamp created_on;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public Group() {
    }

    public Group(String name, Timestamp created_on, Course courses) {
        this.name = name;
        this.created_on = created_on;
        this.course = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public String getValidationError(){
        if(name == null || name.trim().length() == 0){
            return "Missing group name";
        }

        if(course == null || course.getId() == 0){
            return "Missing course that this group belongs to";
        }

        if(created_on == null){
            return "Missing group creation timestamp";
        }
        return null;
    }
}
