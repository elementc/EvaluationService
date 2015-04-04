package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Course;
import org.orm.entities.EvaluationStage;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class EvaluationStageOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateEvaluationStage(EvaluationStage evaluationStage)
			throws HibernateException, EntityValidationException ,Exception {

        validateEvaluationStage(evaluationStage);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
            if(evaluationStage.getId() != 0){
                session.update(evaluationStage);
            }else{
                session.save(evaluationStage);
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

	public void deleteEvaluationStage(int evaluationStageID)
			throws HibernateException, Exception {

		try {
			EvaluationStage evaluationStage = getEvaluationStage(evaluationStageID);
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.delete(evaluationStage);
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

	public List<EvaluationStage> getAllEvaluationStages() throws HibernateException,
			Exception {

		List<EvaluationStage> evaluationStageList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(EvaluationStage.class);

			criteria.addOrder(Order.asc("id"));

            try{
                evaluationStageList = criteria.list();
            }catch (ObjectNotFoundException e){
                evaluationStageList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return evaluationStageList;
	}

	public List<EvaluationStage> getTopEvaluationStages(int maxRows)
			throws HibernateException, Exception {

		List<EvaluationStage> evaluationStageList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(EvaluationStage.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                evaluationStageList = criteria.list();
            }catch (ObjectNotFoundException e){
                evaluationStageList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return evaluationStageList;
	}

	public EvaluationStage getEvaluationStage(int evaluationStageID)
			throws HibernateException, Exception {

		EvaluationStage evaluationStage = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			evaluationStage = (EvaluationStage) session.get(EvaluationStage.class, evaluationStageID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return evaluationStage;
	}


    private void validateEvaluationStage(EvaluationStage evaluationStage) throws EntityValidationException{
        if(evaluationStage.getName() == null || evaluationStage.getCourse() == null || evaluationStage.getCreated_on() == null || evaluationStage.getDescription() == null || evaluationStage.getStart_date() == null || evaluationStage.getStart_date() == null){
            throw new EntityValidationException("EvaluationStage object is missing required values!");
        }
    }
}
