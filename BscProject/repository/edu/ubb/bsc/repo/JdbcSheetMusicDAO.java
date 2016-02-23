package edu.ubb.bsc.repo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.Sheetmusic;
import edu.ubb.bsc.service.repo.ServiceException;

public class JdbcSheetMusicDAO implements SheetMusicDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSheetMusicDAO.class);

	public JdbcSheetMusicDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(Sheetmusic.class).configure()
					.buildSessionFactory();
			log.info("UserDao Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public List<Sheetmusic> getAllSheetmusic() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public Sheetmusic getSheetmusicById(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Sheetmusic object = null;
		try {
			tx = session.beginTransaction();
			object = (Sheetmusic) session.get(Sheetmusic.class, id);

			if (object != null) {
				System.out.print("\t" + object.getSheetMusicId());
				System.out.print("\t" + object.getName());
				System.out.print("\t" + object.getUploadDate());
			}

			//increment the views number
			int viewsNum = object.getViewsNum();
			object.setViewsNum(viewsNum + 1);
			this.updateSheetmusic(object);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return object;
	}

	public List<Sheetmusic> getSheetmusicByFilter(String pattern) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertSheetmusic(Sheetmusic sheetmusic) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = 0;

		try {
			tx = session.beginTransaction();
			UserId = (Integer) session.save(sheetmusic); // save to DB
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		log.info("New SheetMusic Inserted!");
		return UserId.toString();
	}

	public void updateSheetmusic(Sheetmusic sheetmusic) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(sheetmusic);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteSheetmusic(Sheetmusic sheetmusic) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (sheetmusic != null) {
				session.delete(sheetmusic);
				log.info("sheetmusic with ID= " + sheetmusic.getSheetMusicId() + " deleted!");
			} else {
				log.info("sheetmusic with ID= " + sheetmusic.getSheetMusicId() + " not exist!");
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

	public Sheetmusic getSheetmusicByName(String username) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
