
package org.orm.entities;

import javax.persistence.*;

@Entity  
@Table(name= "group_members")
public class GroupMember implements java.io.Serializable{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public GroupMember() {
    }

    public GroupMember(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
