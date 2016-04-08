package edu.ubb.bsc.repo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.SheetmusicComment;

public class JdbcSheetMusicCommentDAO implements SheetMusicCommentDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSheetMusicCommentDAO.class);

	public JdbcSheetMusicCommentDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(SheetmusicComment.class).configure()
					.buildSessionFactory();
			log.info("JdbcSheetMusicCommentDAO Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public List<SheetmusicComment> getAllSheetMusicComment() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public SheetmusicComment getSheetmusicCommentById(int id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SheetmusicComment> getSheetmusicCommentBySheetMusicId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetmusicComment> object = null;
		try {
			tx = session.beginTransaction();

			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM SheetmusicComment WHERE sheetMusicID = :sheetMusicID");
			query.setParameter("sheetMusicID", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get SheetmusicComment by ID!", e);
			throw new RepositoryException("Error get SheetmusicComment by ID", e);
		} finally {
			session.close();
		}
		return object;
	}

	public String insertSheetmusicComment(SheetmusicComment comment) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = 0;
		try {
			tx = session.beginTransaction();
			UserId = (Integer) session.save(comment); // save to DB
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error insert!", e);
			throw new RepositoryException("Error insert", e);
		} finally {
			session.close();
		}
		log.info("New SheetmusicComment Inserted!");
		return UserId.toString();
	}

	public void updateSheetmusicComment(SheetmusicComment comment) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(comment);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error update!", e);
			throw new RepositoryException("Error update", e);
		} finally {
			session.close();
		}
	}

	public void deleteSheetmusicComment(SheetmusicComment comment) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (comment != null) {
				session.delete(comment);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error delete!", e);
			throw new RepositoryException("Error delete", e);
		} finally {
			session.close();
		}
	}
	
	

}
