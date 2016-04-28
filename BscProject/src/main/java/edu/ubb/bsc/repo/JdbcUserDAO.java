package edu.ubb.bsc.repo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.User;

public class JdbcUserDAO implements UserDAO {

	private static SessionFactory factoryUser;
	private final static Logger log = LoggerFactory.getLogger(UserDAO.class);

	public JdbcUserDAO() {
		try {
			factoryUser = new AnnotationConfiguration().addAnnotatedClass(User.class).configure().buildSessionFactory();
			log.info("UserDao Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public List<User> getAllUsers() throws RepositoryException {

		return null;
	}

	public User getUserById(int id) throws RepositoryException {

		Session session = factoryUser.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User) session.get(User.class, id);

			log.info("Get user");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	public List<User> getUsersByFilter(String pattern) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertUser(User user) throws RepositoryException {
		Session session = factoryUser.openSession();
		Transaction tx = null;
		Integer id = 0;

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(user); // save to DB
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		log.info("New User Inserted!");
		return id.toString();
	}

	public void updateUser(User user) throws RepositoryException {
		Session session = factoryUser.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(user);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteUser(User user) throws RepositoryException {
		Session session = factoryUser.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (user != null) {
				session.delete(user);
				log.info("User with ID= " + user.getUserId() + " deleted!");
			} else {
				log.info("User with ID= " + user.getUserId() + " not exist!");
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public User loginAuthenticateUser(String userName, String password) throws RepositoryException {
		Session session = factoryUser.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();

			Query query = session
					.createQuery("FROM User WHERE userName = :userName" + " and userPassword = :userPassword");
			query.setParameter("userName", userName);
			query.setParameter("userPassword", password);

			log.info("Login in progress!");

			List<?> u = query.list();
			for (Iterator<?> iterator = u.iterator(); iterator.hasNext();) {
				user = (User) iterator.next();
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return user;
	}

	public User getUserByName(String username) throws RepositoryException {
		Session session = factoryUser.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE userName = :userName");
			query.setParameter("userName", username);

			List<?> u = query.list();
			for (Iterator<?> iterator = u.iterator(); iterator.hasNext();) {
				user = (User) iterator.next();
			}
			log.info("User was getted by name: " + username);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;

	}

}
