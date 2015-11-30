package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.MemberEvaluation;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class MemberEvaluationOperations {

	public void addOrUpdateMemberEvaluation(MemberEvaluation memberEvaluation)
			throws HibernateException, EntityValidationException ,Exception {

		validateMemberEvaluation(memberEvaluation);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(memberEvaluation.getId() != 0){
				session.update(memberEvaluation);
			}else{
				session.save(memberEvaluation);
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

	public void deleteMemberEvaluation(int memberEvaluationID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(getMemberEvaluation(memberEvaluationID));
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

	public List<MemberEvaluation> getAllMemberEvaluations() throws HibernateException,
			Exception {

		Session session = null;
		List<MemberEvaluation> memberEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(MemberEvaluation.class);

			criteria.addOrder(Order.asc("id"));

            try{
                memberEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                memberEvaluationsList = null;
            }
		} finally {
			session.flush(); session.close();
		}
		return memberEvaluationsList;
	}

	public List<MemberEvaluation> getTopMemberEvaluations(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<MemberEvaluation> memberEvaluationsList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(MemberEvaluation.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                memberEvaluationsList = criteria.list();
            }catch (ObjectNotFoundException e){
                memberEvaluationsList = null;
            }
		} finally {
			session.flush(); session.close();
		}
		return memberEvaluationsList;
	}

	public MemberEvaluation getMemberEvaluation(int memberEvaluationID)
			throws HibernateException, Exception {

		Session session = null;
		MemberEvaluation memberEvaluation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			memberEvaluation = (MemberEvaluation) session.get(MemberEvaluation.class,
					memberEvaluationID);

		} finally {
			session.flush(); session.close();
		}
		return memberEvaluation;
	}


    private void validateMemberEvaluation(MemberEvaluation memberEvaluation) throws EntityValidationException{
		String validationError = memberEvaluation.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
