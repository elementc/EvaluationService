package org.orm.services;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.orm.entities.User;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class UserOperations {

	public void addOrUpdateUser(User user)
			throws HibernateException, EntityValidationException ,Exception {

        validateUser(user);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(user.getId() != 0){
				session.update(user);
			}else{
				session.save(user);
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

	public void deleteUser(int userID)
			throws HibernateException, Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(getUser(userID));
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

	public List<User> getAllUsers() throws HibernateException,
			Exception {

		List<User> usersList = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);

			criteria.addOrder(Order.asc("id"));

			usersList = criteria.list();

		} finally {
			session.flush();
			session.close();
		}
		return usersList;
	}

	public List<User> getTopUsers(int maxRows)
			throws HibernateException, Exception {

		List<User> usersList = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

			usersList = criteria.list();

		} finally {
			session.flush();
			session.close();
		}
		return usersList;
	}

	public User getUser(int userID)
			throws HibernateException, Exception {

		User userAccount = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			userAccount = (User) session.get(User.class,
					userID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.flush();
			session.close();
		}
		return userAccount;
	}

    public User getUserByEmail(String email) throws Exception{
        List<User> users = null;
		Session session = null;
		try {
            session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);


            if (email != null) {
                criteria.add(Expression.eq("email", email));
            }

            users = criteria.list();

        } catch (HibernateException e) {
            throw new HibernateException(e);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
			session.flush();
			session.close();
        }

        if(users != null && users.size() > 0){
            return users.get(0);
        }else{
            return null;
        }
    }

    public User getUserByEmailAndPassword(String email, String password) throws Exception{
        List<User> users = null;
		Session session = null;
		try {
            session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);


            if (email != null) {
                criteria.add(Expression.eq("email", email));
            }
            if (password != null) {
                criteria.add(Expression.eq("password", password));
            }

            users = criteria.list();

        } finally {
			session.flush();
			session.close();
        }

        if(users != null && users.size() > 0){
            return users.get(0);
        }else{
            return null;
        }
    }

    private void validateUser(User user) throws EntityValidationException{
		String validationError = user.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }

}
