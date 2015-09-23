package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Group;
import org.orm.entities.GroupEvaluation;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class GroupEvaluationOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateGroupEvaluation(GroupEvaluation groupEvaluation)
			throws HibernateException, EntityValidationException ,Exception {

        validateGroupEvaluation(groupEvaluation);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
            if(groupEvaluation.getId() != 0){
                session.update(groupEvaluation);
            }else{
                session.save(groupEvaluation);
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

	public void deleteGroupEvaluation(int groupEvaluationID)
			throws HibernateException, Exception {

		try {
			GroupEvaluation groupEvaluation = getGroupEvaluation(groupEvaluationID);
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
			session.delete(groupEvaluation);
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

	public List<GroupEvaluation> getAllGroupEvaluations() throws HibernateException,
			Exception {

		List<GroupEvaluation> groupEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(GroupEvaluation.class);

			criteria.addOrder(Order.asc("id"));

            try{
                groupEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupEvaluationsList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupEvaluationsList;
	}

	public List<GroupEvaluation> getTopGroupEvaluations(int maxRows)
			throws HibernateException, Exception {

		List<GroupEvaluation> groupEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(GroupEvaluation.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                groupEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                groupEvaluationsList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupEvaluationsList;
	}

	public GroupEvaluation getGroupEvaluation(int groupEvaluationID)
			throws HibernateException, Exception {

		GroupEvaluation groupEvaluation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			groupEvaluation = (GroupEvaluation) session.get(GroupEvaluation.class,
					groupEvaluationID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return groupEvaluation;
	}


    private void validateGroupEvaluation(GroupEvaluation groupEvaluation) throws EntityValidationException{
        if(groupEvaluation.getCreated_on() == null || groupEvaluation.getGroup() == null || groupEvaluation.getDecision_making_details() == null || groupEvaluation.getDifference_attitude_details() == null || groupEvaluation.getEvaluator() == null || groupEvaluation.getEvalutionStage() == null || groupEvaluation.getFlexibility_details() == null || groupEvaluation.getGoals_details() == null || groupEvaluation.getMutual_trust_details() == null || groupEvaluation.getDifference_attitude_details() == null || groupEvaluation.getParticipation_details() == null || groupEvaluation.getResource_use_details() == null || groupEvaluation.getResource_use_details() == null ){
            throw new EntityValidationException("GroupEvaluation object is missing required values!");
        }
    }
}
