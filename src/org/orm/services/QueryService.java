package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.orm.entities.*;
import org.orm.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


public class QueryService {

	private Session session;

    public User getUserByEmailAndPassword(String email, String password) throws Exception{
        List<User> users = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class);


            if (email != null) {
                criteria.add(Expression.eq("email", email));
            }
            if (password != null) {
                criteria.add(Expression.eq("password", password));
            }

            try{
                users = criteria.list();
            }catch (ObjectNotFoundException e){
                users = null;
            }
        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        if(users != null && users.size() > 0){
            return users.get(0);
        }else{
            return null;
        }
    }

    public List<Group> getGroupsByCourseID(int courseID) throws Exception{
        List<Group> groups;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Group.class);

            criteria.add(Expression.eq("course_id", courseID));

            try{
                groups = criteria.list();
            }catch (ObjectNotFoundException e){
                groups = null;
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return groups;
    }

    public List<Group> getGroupsByUserID(int userID) throws Exception{
        ArrayList<Group> groups = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("user_id", userID));

            List<GroupMember> groupMembers = null;
            try{
                groupMembers = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMembers = null;
            }
            if(groupMembers != null && groupMembers.size() > 0){
                groups = new ArrayList<Group>();

                for(GroupMember groupMember : groupMembers){
                    groups.add(groupMember.getGroup());
                }
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return groups;
    }

    public List<User> getUsersByGroupID(int groupID) throws Exception{
        ArrayList<User> users = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("group_id", groupID));

            List<GroupMember> groupMembers = null;
            try{
                groupMembers = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMembers = null;
            }

            if(groupMembers != null && groupMembers.size() > 0){
                users = new ArrayList<User>();

                for(GroupMember groupMember : groupMembers){
                    users.add(groupMember.getUser());
                }
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return users;
    }

    public List<MemberEvaluation> getMemberEvaluationsByUserID(int userID) throws Exception{
        List<MemberEvaluation> memberEvaluations = null;

        try{
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(MemberEvaluation.class);

            criteria.add(Expression.eq("evaluator_id", userID));

            try{
                memberEvaluations = criteria.list();
            }catch (ObjectNotFoundException e){
                memberEvaluations = null;
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return memberEvaluations;
    }

    public List<GroupEvaluation> getGroupEvaluationsByUserID(int userID) throws Exception{
        List<GroupEvaluation> groupEvaluations = null;

        try{
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(GroupEvaluation.class);

            criteria.add(Expression.eq("evaluator_id", userID));

            try{
                groupEvaluations = criteria.list();
            }catch (ObjectNotFoundException e){
                groupEvaluations = null;
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return groupEvaluations;
    }

    public List<Course> getCoursesByUserID(int userID) throws Exception{
        ArrayList<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("user_id", userID));

            List<GroupMember> groupMembers = null;

            try{
                groupMembers = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMembers = null;
            }

            if(groupMembers != null && groupMembers.size() > 0){
                courses = new ArrayList<Course>();

                for(GroupMember groupMember : groupMembers){
                    courses.add(groupMember.getGroup().getCourse());
                }
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }

        return courses;
    }
}
