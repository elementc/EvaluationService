
package org.orm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity  
@Table(name= "courses")
public class Course implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private String code;
    private String title;
    private String term;
    private int year;
    private Timestamp created_on;

    public Course() {
    }

    public Course(String code, String title, String term, int year, Timestamp created_on) {
        this.code = code;
        this.title = title;
        this.term = term;
        this.year = year;
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }
}
