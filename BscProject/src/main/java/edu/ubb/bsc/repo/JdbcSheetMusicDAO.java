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

import edu.ubb.bsc.repo.model.SheetMusic;

public class JdbcSheetMusicDAO implements SheetMusicDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSheetMusicDAO.class);

	public JdbcSheetMusicDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(SheetMusic.class).configure()
					.buildSessionFactory();
			log.info("UserDao Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SheetMusic> getAllSheetmusic() throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetMusic> object = null;
		try {
			tx = session.beginTransaction();
			object = session.createCriteria(SheetMusic.class).list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get all!", e);
			throw new RepositoryException("Error get all", e);
		} finally {
			session.close();
		}
		return object;
	}

	public SheetMusic getSheetmusicById(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		SheetMusic object = null;
		try {
			tx = session.beginTransaction();
			object = (SheetMusic) session.get(SheetMusic.class, id);

			if (object != null) {
				System.out.print("\t" + object.getSheetMusicId());
				System.out.print("\t" + object.getName());
				System.out.print("\t" + object.getUploadDate());
			}

			// increment the views number
			int viewsNum = object.getViewsNum();
			object.setViewsNum(viewsNum + 1);
			this.updateSheetmusic(object);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get Sheetmusic by ID!", e);
			throw new RepositoryException("Error get Sheetmusic by ID", e);
		} finally {
			session.close();
		}
		return object;
	}

	public List<SheetMusic> getSheetmusicByFilter(String pattern) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetMusic> sm = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM SheetMusic WHERE name like :pattern");
			query.setParameter("pattern", "%"+pattern+"%");
			
			sm = query.list();
			
//			List<?> u = query.list();
//			for (Iterator<?> iterator = u.iterator(); iterator.hasNext();) {
//				sm = (List<SheetMusic>) iterator.next();
//			}
			log.info("SheetMusic was getted by pattern: " + pattern);
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return sm;
	}

	public String insertSheetmusic(SheetMusic sheetmusic) throws RepositoryException {
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
			log.error("Error insert!", e);
			throw new RepositoryException("Error insert", e);
		} finally {
			session.close();
		}

		log.info("New SheetMusic Inserted!");
		return UserId.toString();
	}

	public void updateSheetmusic(SheetMusic sheetmusic) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(sheetmusic);

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

	@SuppressWarnings("null")
	public void deleteSheetmusic(SheetMusic sheetmusic) throws RepositoryException {
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
			log.error("Error delete!", e);
			throw new RepositoryException("Error delete", e);
		} finally {
			session.close();
		}
	}

	public SheetMusic getSheetmusicByName(String username) throws RepositoryException {
		return null;
	}

	public static void main(String[] args) {
		JdbcSheetMusicDAO cd = new JdbcSheetMusicDAO();
		// cd.getCategoryById(2);
		//List<SheetMusic> sm = cd.getAllSheetmusic();
		List<SheetMusic> sm = cd.getSheetmusicByFilter("See");
		
		for (Iterator<?> iterator = sm.iterator(); iterator.hasNext();) {
			SheetMusic a = (SheetMusic) iterator.next();
			System.out.println(a.getSheetMusicId());
			System.out.println(a.getName());
		}
	}

}
