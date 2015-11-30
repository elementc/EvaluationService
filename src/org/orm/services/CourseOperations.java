package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Course;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class CourseOperations {

	public void addOrUpdateCourse(Course course)
			throws HibernateException, EntityValidationException, Exception {

        validateCourse(course);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			/*session.clear();*/
			transaction = session.beginTransaction();
            if(course.getId() != 0){
                session.update(course);
            }else{
                session.save(course);
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

	public void deleteCourse(int courseID)
			throws HibernateException, Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			/*session.clear();*/
			transaction = session.beginTransaction();
			session.delete(getCourse(courseID));
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

	public List<Course> getAllCourses() throws HibernateException,
			Exception {

		List<Course> courseList = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Course.class);

			criteria.addOrder(Order.asc("id"));

            try{
                courseList = criteria.list();
            }catch (ObjectNotFoundException e){
                courseList = null;
            }

		} finally {
			session.flush();
			session.close();
		}
		return courseList;
	}

	public List<Course> getTopCourses(int maxRows)
			throws HibernateException, Exception {

		Session session = null;
		List<Course> courseList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Course.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                courseList = criteria.list();
            }catch (ObjectNotFoundException e){
                courseList = null;
            }

		} finally {
			session.flush();
			session.close();
		}
		return courseList;
	}

	public Course getCourse(int courseID)
			throws HibernateException, Exception {

		Session session = null;
		Course course = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			course = (Course) session.get(Course.class, courseID);

		} finally {
			session.flush();
			session.close();
		}
		return course;
	}

    private void validateCourse(Course course) throws EntityValidationException{
		String validationError = course.getValidationError();
		if(validationError != null){
			throw new EntityValidationException(validationError);
		}
    }
}
