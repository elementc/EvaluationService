package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Course;
import org.orm.entities.EvaluationStage;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class EvaluationStageOperations {

	public void addOrUpdateEvaluationStage(EvaluationStage evaluationStage)
			throws HibernateException, EntityValidationException ,Exception {

		validateEvaluationStage(evaluationStage);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			/*session.clear();*/
			transaction = session.beginTransaction();
			if(evaluationStage.getId() != 0){
				session.update(evaluationStage);
			}else{
				session.save(evaluationStage);
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

	public void deleteEvaluationStage(int evaluationStageID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			/*session.clear();*/
			transaction = session.beginTransaction();
			session.delete(getEvaluationStage(evaluationStageID));
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

	public List<EvaluationStage> getAllEvaluationStages() throws HibernateException,
			Exception {

		Session session = null;
		List<EvaluationStage> evaluationStageList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(EvaluationStage.class);

			criteria.addOrder(Order.asc("id"));

            try{
                evaluationStageList = criteria.list();
            }catch (ObjectNotFoundException e){
                evaluationStageList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return evaluationStageList;
	}

	public List<EvaluationStage> getTopEvaluationStages(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<EvaluationStage> evaluationStageList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(EvaluationStage.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                evaluationStageList = criteria.list();
            }catch (ObjectNotFoundException e){
                evaluationStageList = null;
            }

		} finally {
			session.flush(); session.close();
		}
		return evaluationStageList;
	}

	public EvaluationStage getEvaluationStage(int evaluationStageID)
			throws HibernateException, Exception {

		Session session = null;
		EvaluationStage evaluationStage = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			evaluationStage = (EvaluationStage) session.get(EvaluationStage.class, evaluationStageID);

		} finally {
			session.flush(); session.close();
		}
		return evaluationStage;
	}


    private void validateEvaluationStage(EvaluationStage evaluationStage) throws EntityValidationException{
		String validationError = evaluationStage.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
