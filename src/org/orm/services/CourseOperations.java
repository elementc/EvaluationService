package org.orm.services;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.orm.entities.Course;
import org.orm.exceptions.EntityValidationException;
import org.orm.util.HibernateUtil;

import java.util.List;


public class CourseOperations {

	private Session session;
	private Transaction transaction;

	public void addOrUpdateCourse(Course course)
			throws HibernateException, EntityValidationException, Exception {

        validateCourse(course);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
            if(course.getId() != 0){
                session.update(course);
            }else{
                session.save(course);
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

	public void deleteCourse(int courseID)
			throws HibernateException, Exception {

		try {
			Course course = getCourse(courseID);
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.delete(course);
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

	public List<Course> getAllCourses() throws HibernateException,
			Exception {

		List<Course> courseList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Course.class);

			criteria.addOrder(Order.asc("id"));

            try{
                courseList = criteria.list();
            }catch (ObjectNotFoundException e){
                courseList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return courseList;
	}

	public List<Course> getTopCourses(int maxRows)
			throws HibernateException, Exception {

		List<Course> courseList = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Course.class);

			if (maxRows > 0)
				criteria.setMaxResults(maxRows);// max number of rows

			criteria.addOrder(Order.asc("id"));// in ascending order of id

            try{
                courseList = criteria.list();
            }catch (ObjectNotFoundException e){
                courseList = null;
            }

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return courseList;
	}

	public Course getCourse(int courseID)
			throws HibernateException, Exception {

		Course course = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			course = (Course) session.get(Course.class, courseID);

		} catch (HibernateException e) {
			throw new HibernateException(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		return course;
	}

    private void validateCourse(Course course) throws EntityValidationException{
        if(course.getCode() == null || course.getTerm() == null || course.getCreated_on() == null || course.getTitle() == null){
            throw new EntityValidationException("Course object is missing required values!");
        }
    }
}
