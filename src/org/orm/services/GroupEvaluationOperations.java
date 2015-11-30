package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Group;
import org.orm.entities.GroupEvaluation;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class GroupEvaluationOperations {

	public void addOrUpdateGroupEvaluation(GroupEvaluation groupEvaluation)
			throws HibernateException, EntityValidationException ,Exception {

		validateGroupEvaluation(groupEvaluation);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(groupEvaluation.getId() != 0){
				session.update(groupEvaluation);
			}else{
				session.save(groupEvaluation);
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

	public void deleteGroupEvaluation(int groupEvaluationID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(getGroupEvaluation(groupEvaluationID));
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

	public List<GroupEvaluation> getAllGroupEvaluations() throws HibernateException,
			Exception {

		Session session = null;
		List<GroupEvaluation> groupEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GroupEvaluation.class);

			criteria.addOrder(Order.asc("id"));

            try{
                groupEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupEvaluationsList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupEvaluationsList;
	}

	public List<GroupEvaluation> getTopGroupEvaluations(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<GroupEvaluation> groupEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GroupEvaluation.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupEvaluationsList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return groupEvaluationsList;
	}

	public GroupEvaluation getGroupEvaluation(int groupEvaluationID)
			throws HibernateException, Exception {

		Session session = null;
		GroupEvaluation groupEvaluation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			groupEvaluation = (GroupEvaluation) session.get(GroupEvaluation.class,
					groupEvaluationID);

		} finally {
			session.flush(); session.close();
		}
		return groupEvaluation;
	}


    private void validateGroupEvaluation(GroupEvaluation groupEvaluation) throws EntityValidationException{
		String validationError = groupEvaluation.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
