package org.orm.test;

import java.sql.Timestamp;
import java.util.List;

import org.orm.entities.Course;
import org.orm.entities.Group;
import org.orm.entities.User;
import org.orm.services.GroupOperations;
import org.orm.services.UserOperations;


public class LoadData {

	public static void main(String[] args) {

		try {

            UserOperations userOps = new UserOperations();
            //User newUser  = new User("rushil2011@my.fit.edu", "Password", false, "Rushil Patel", false, new Timestamp(System.currentTimeMillis()));
            //userOps.addOrUpdateUser(newUser);

            Course course = new Course();
            course.setId(1);

            GroupOperations groupsOperations = new GroupOperations();

            Group group = new Group("Test", new Timestamp(System.currentTimeMillis()), course);
            groupsOperations.addOrUpdateGroup(group);

            List<User> users = userOps.getAllUsers();

            for(User user : users){
                System.out.println(user.getEmail());
            }

            List<Group> groups = groupsOperations.getAllGroups();

            for(Group g : groups){
                System.out.println(g.getCourse().getTitle());
            }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
