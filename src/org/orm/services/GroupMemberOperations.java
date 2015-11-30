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

	public void addOrUpdateGroupMember(GroupMember groupMember)
			throws HibernateException, EntityValidationException ,Exception {

        validateGroupMember(groupMember);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(groupMember.getId() != 0){
				session.update(groupMember);
			}else{
				session.save(groupMember);
			}
			session.getTransaction().commit();
		} catch (RuntimeException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
	}

	public void deleteGroupMember(int groupMemberID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(getGroupMember(groupMemberID));
			session.getTransaction().commit();
		} catch (RuntimeException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
	}

	public List<GroupMember> getAllGroupMember() throws HibernateException,
			Exception {

		Session session = null;
		List<GroupMember> groupMemberList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GroupMember.class);

			criteria.addOrder(Order.asc("id"));

            try{
                groupMemberList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMemberList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupMemberList;
	}

	public List<GroupMember> getTopGroupMembers(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<GroupMember> groupMemberList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GroupMember.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupMemberList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupMemberList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupMemberList;
	}

	public GroupMember getGroupMember(int groupMemberID)
			throws HibernateException, Exception {

		Session session = null;
		GroupMember groupMember = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
            groupMember = (GroupMember) session.get(GroupMember.class,
                    groupMemberID);

		} finally {
			session.flush(); session.close();
		}
		return groupMember;
	}


    private void validateGroupMember(GroupMember groupMember) throws EntityValidationException{
		String validationError = groupMember.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
