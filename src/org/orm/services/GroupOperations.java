package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Group;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class GroupOperations {

	public void addOrUpdateGroup(Group group)
			throws HibernateException, EntityValidationException ,Exception {

		validateGroup(group);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(group.getId() != 0){
				session.update(group);
			}else{
				session.save(group);
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

	public void deleteGroup(int groupID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(getGroup(groupID));
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

	public List<Group> getAllGroups() throws HibernateException,
			Exception {

		Session session = null;
		List<Group> groupsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class);

			//criteria.addOrder(Order.asc("id"));

            try{
                groupsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupsList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupsList;
	}

	public List<Group> getTopGroups(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<Group> groupsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Group.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupsList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupsList;
	}

	public Group getGroup(int groupID)
			throws HibernateException, Exception {

		Session session = null;
		Group group = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			group = (Group) session.get(Group.class,
					groupID);

		} finally {
			session.flush(); session.close();
		}
		return group;
	}


    private void validateGroup(Group group) throws EntityValidationException{
		String validationError = group.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
