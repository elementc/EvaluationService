package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.MemberEvaluation;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class MemberEvaluationOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateMemberEvaluation(MemberEvaluation memberEvaluation)
			throws HibernateException, EntityValidationException ,Exception {

        validateMemberEvaluation(memberEvaluation);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
            if(memberEvaluation.getId() != 0){
                session.update(memberEvaluation);
            }else{
                session.save(memberEvaluation);
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

	public void deleteMemberEvaluation(int memberEvaluationID)
			throws HibernateException, Exception {

		try {
			MemberEvaluation memberEvaluation = getMemberEvaluation(memberEvaluationID);
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			transaction = session.beginTransaction();
			session.delete(memberEvaluation);
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

	public List<MemberEvaluation> getAllMemberEvaluations() throws HibernateException,
			Exception {

		List<MemberEvaluation> memberEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(MemberEvaluation.class);

			criteria.addOrder(Order.asc("id"));

            try{
                memberEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                memberEvaluationsList = null;
            }
		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return memberEvaluationsList;
	}

	public List<MemberEvaluation> getTopMemberEvaluations(int maxRows)
			throws HibernateException, Exception {

		List<MemberEvaluation> memberEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();
			Criteria criteria = session.createCriteria(MemberEvaluation.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                memberEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                memberEvaluationsList = null;
            }
		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return memberEvaluationsList;
	}

	public MemberEvaluation getMemberEvaluation(int memberEvaluationID)
			throws HibernateException, Exception {

		MemberEvaluation memberEvaluation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.clear();

			memberEvaluation = (MemberEvaluation) session.get(MemberEvaluation.class,
					memberEvaluationID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return memberEvaluation;
	}


    private void validateMemberEvaluation(MemberEvaluation memberEvaluation) throws EntityValidationException{
        if(memberEvaluation.getEvaluator() == null || memberEvaluation.getEvaluatee() == null || memberEvaluation.getEvalutionStage() == null || memberEvaluation.getParticipation_details() == null || memberEvaluation.getTask_completeness_details() == null || memberEvaluation.getGroup() == null){
            throw new EntityValidationException("MemberEvaluation object is missing required values!");
        }
    }
}
