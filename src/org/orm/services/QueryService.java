package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.orm.entities.*;
import org.orm.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


public class QueryService {

	private Session session;


    public List<Group> getGroupsByUserID(int userID) throws Exception{
        ArrayList<Group> groups = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();
            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("user.id", userID));

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
            session.clear();session.close();
        }

        return groups;
    }

    public void updateUser(User user, boolean updatePassword) throws Exception{
        if(!updatePassword){
            User org_user = getUserByUserID(user.getId());
            user.setPassword(org_user.getPassword());
        }
        new UserOperations().addOrUpdateUser(user);
    }

    public void addUser(User user) throws Exception{
        new UserOperations().addOrUpdateUser(user);
    }

    public void addMemberEvaluation(MemberEvaluation memberEvaluation) throws Exception{
       new MemberEvaluationOperations().addOrUpdateMemberEvaluation(memberEvaluation);
    }

    public void addGroupEvaluation(GroupEvaluation groupEvaluation) throws Exception{
        new GroupEvaluationOperations().addOrUpdateGroupEvaluation(groupEvaluation);
    }

    public User getUserByUserID(int userID) throws Exception{
        return new UserOperations().getUser(userID);
    }

    public User getUserByEmail(String email) throws Exception{
        return new UserOperations().getUserByEmail(email);
    }

    public List<User> getUsersByGroupID(int groupID) throws Exception{
        ArrayList<User> users = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("group.id", groupID));

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
            session.clear();session.close();
        }

        return users;
    }

    public List<MemberEvaluation> getMemberEvaluationsByUserID(int userID) throws Exception{
        List<MemberEvaluation> memberEvaluations = null;

        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(MemberEvaluation.class);

            criteria.add(Expression.eq("evaluator.id", userID));

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
            session.clear();session.close();
        }

        return memberEvaluations;
    }

    public List<GroupEvaluation> getGroupEvaluationsByUserID(int userID) throws Exception{
        List<GroupEvaluation> groupEvaluations = null;

        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(GroupEvaluation.class);

            criteria.add(Expression.eq("evaluator.id", userID));

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
            session.clear();session.close();
        }

        return groupEvaluations;
    }

    public List<Course> getCourses() throws Exception{
        List<Course> courseList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();
            Criteria criteria = session.createCriteria(Course.class);

            criteria.addOrder(Order.asc("id"));

            try{
                courseList = criteria.list();
            }catch (ObjectNotFoundException e){
                courseList = null;
            }

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.close();
        }
        return courseList;
    }

    public List<Group> getGroupsByCouseID(int courseID) throws Exception{
        List<Group> groups = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(Group.class);

            criteria.add(Expression.eq("course.id", courseID));


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
            session.clear();session.close();
        }

        return groups;
    }

    public List<Course> getCoursesByUserID(int userID) throws Exception{
        ArrayList<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(GroupMember.class);

            criteria.add(Expression.eq("user.id", userID));

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
            session.clear();session.close();
        }

        return courses;
    }


    public List<EvaluationStage> getEvaluationStagesByCourseID(int courseID) throws Exception{
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.clear();

            Criteria criteria = session.createCriteria(EvaluationStage.class);

            criteria.add(Expression.eq("course.id", courseID));

            List<EvaluationStage> evaluationStages = null;

            try{
                evaluationStages = criteria.list();
            }catch (ObjectNotFoundException e){
                evaluationStages = null;
            }
            return evaluationStages;

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            session.clear();session.close();
        }
    }

    public void addGroupMember(GroupMember groupMember) throws Exception{
        new GroupMemberOperations().addOrUpdateGroupMember(groupMember);
    }

    public void addGroup(Group group) throws Exception{
        new GroupOperations().addOrUpdateGroup(group);
    }
}
