package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Group;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class GroupOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateGroup(Group group)
			throws HibernateException, EntityValidationException ,Exception {

        validateGroup(group);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
            if(group.getId() != 0){
                session.update(group);
            }else{
                session.save(group);
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

	public void deleteGroup(int groupID)
			throws HibernateException, Exception {

		try {
			Group group = getGroup(groupID);
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.delete(group);
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

	public List<Group> getAllGroups() throws HibernateException,
			Exception {

		List<Group> groupsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Group.class);

			//criteria.addOrder(Order.asc("id"));

            try{
                groupsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupsList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupsList;
	}

	public List<Group> getTopGroups(int maxRows)
			throws HibernateException, Exception {

		List<Group> groupsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Group.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupsList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupsList;
	}

	public Group getGroup(int groupID)
			throws HibernateException, Exception {

		Group group = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			group = (Group) session.get(Group.class,
					groupID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return group;
	}


    private void validateGroup(Group group) throws EntityValidationException{
        if(group.getCourse() == null || group.getName() == null || group.getCreated_on() == null){
            throw new EntityValidationException("Group object is missing required values!");
        }
    }
}
