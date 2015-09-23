package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Group;
import org.orm.entities.GroupMember;
import org.orm.entities.User;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class GroupMemberOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateGroupMember(GroupMember groupMember)
			throws HibernateException, EntityValidationException ,Exception {

        validateGroupMember(groupMember);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
            if(groupMember.getId() != 0){
                session.update(groupMember);
            }else{
                session.save(groupMember);
            }
			transaction.commit();
		} catch (HibernateException e) {

			if (transaction != null){
                transaction.rollback();
            }
			throw new HibernateException(e);
		} catch (Exception e) {
			if (transaction != null){
                transaction.rollback();
            }
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	public void deleteGroupMember(int groupMemberID)
			throws HibernateException, Exception {

		try {
            GroupMember groupMember = getGroupMember(groupMemberID);
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
			session.delete(groupMember);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null){
                transaction.rollback();
            }

			throw new HibernateException(e);
		} catch (Exception e) {
			if (transaction != null){
                transaction.rollback();
            }

			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	public List<GroupMember> getAllGroupMember() throws HibernateException,
			Exception {

		List<GroupMember> groupMemberList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(GroupMember.class);

			criteria.addOrder(Order.asc("id"));

            try{
                groupMemberList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMemberList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupMemberList;
	}

	public List<GroupMember> getTopGroupMembers(int maxRows)
			throws HibernateException, Exception {

		List<GroupMember> groupMemberList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(GroupMember.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupMemberList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMemberList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupMemberList;
	}

	public GroupMember getGroupMember(int groupMemberID)
			throws HibernateException, Exception {

        GroupMember groupMember = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
            groupMember = (GroupMember) session.get(GroupMember.class,
                    groupMemberID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupMember;
	}


    private void validateGroupMember(GroupMember groupMember) throws EntityValidationException{
        if(groupMember.getUser() == null || groupMember.getGroup() == null){
            throw new EntityValidationException("GroupMember object is missing required values!");
        }
    }
}
