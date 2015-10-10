
package org.orm.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity  
@Table(name = "users")
public class User implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private String email;
    private String password;
    private boolean need_password_reset;
    private String fullname;
    private boolean is_inspector;
    private Timestamp created_on;

    public User(String email, String password, boolean need_password_reset, String fullname, boolean is_inspector, Timestamp created_on) {
        this.email = email;
        this.password = password;
        this.need_password_reset = need_password_reset;
        this.fullname = fullname;
        this.is_inspector = is_inspector;
        this.created_on = created_on;
    }

    public User(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNeed_password_reset() {
        return need_password_reset;
    }

    public void setNeed_password_reset(boolean need_password_reset) {
        this.need_password_reset = need_password_reset;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isIs_inspector() {
        return is_inspector;
    }

    public void setIs_inspector(boolean is_inspector) {
        this.is_inspector = is_inspector;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public String getValidationError(){
        if(email == null || email.trim().length() == 0){
            return "Missing user's email address";
        }

        if(fullname == null || fullname.trim().length() == 0){
            return "Missing user's name";
        }

        if(password == null || password.trim().length() == 0){
            return "Missing user's password";
        }

        if(created_on == null){
            return "Missing user creation timestamp";
        }
        return null;
    }
}
