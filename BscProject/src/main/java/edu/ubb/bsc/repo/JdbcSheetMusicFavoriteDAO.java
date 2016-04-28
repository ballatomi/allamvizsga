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

import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SheetmusicFavorite;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.rest.RestFavoriteSheetmusic;

public class JdbcSheetMusicFavoriteDAO implements SheetMusicFavoriteDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSheetMusicFavoriteDAO.class);

	public JdbcSheetMusicFavoriteDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(SheetmusicFavorite.class).configure()
					.buildSessionFactory();
			log.info("JdbcSheetMusicFavoriteDAO Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public List<SheetmusicFavorite> getSheetmusicFavoritesByUserId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetmusicFavorite> object = null;
		try {
			tx = session.beginTransaction();
			
			Query query = session
			.createQuery("FROM SheetmusicFavorite WHERE userID = :user");
			
			query.setParameter("user", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get SheetMusicFavorite by ID!", e);
			throw new RepositoryException("Error get SheetMusicFavorite by ID", e);
		} finally {
			session.close();
		}
		return object;
	}
	
	public Boolean isFavoriteSheetMusicOfUser(int smID, int userID) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Boolean object = false;
		try {
			tx = session.beginTransaction();
			
			Query query = session
			.createQuery("FROM SheetmusicFavorite WHERE userID = :user and sheetMusicID = :sm");
			
			query.setParameter("user", userID);
			query.setParameter("sm", smID);
			
			List<SheetmusicFavorite> sfm =  query.list();
			object = sfm.isEmpty() ? false : true;

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get SheetMusicFavorite by ID!", e);
			throw new RepositoryException("Error get SheetMusicFavorite by ID", e);
		} finally {
			session.close();
		}
		return object;
	}
	


	public String insertSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer UserId = 0;
		try {
			tx = session.beginTransaction();
			UserId = (Integer) session.save(fav); // save to DB
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error insert!", e);
			throw new RepositoryException("Error insert", e);
		} finally {
			session.close();
		}

		log.info("New InstrumentSheetmusic Inserted!");
		return UserId.toString();
	}

	public void updateSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(fav);

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

	public void deleteSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("delete FROM SheetmusicFavorite WHERE userID = :user and sheetMusicID = :sm");
			if (fav != null) {
				query.setParameter("user", fav.getUser().getUserId());
				query.setParameter("sm", fav.getSheetMusic().getSheetMusicId());
				
				query.executeUpdate();
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error delete!", e);
			throw new RepositoryException("Error delete favorite", e);
		} finally {
			session.close();
		}

	}
	
	public static void main(String[] args) {
		JdbcSheetMusicFavoriteDAO cd = new JdbcSheetMusicFavoriteDAO();
		SheetmusicFavorite fav = new SheetmusicFavorite();
		
		User u = new User();u.setUserId(14);
		SheetMusic sm = new SheetMusic();
		sm.setSheetMusicId(39);
		
		fav.setUser(u);
		fav.setSheetMusic(sm);
		
		System.out.println(cd.isFavoriteSheetMusicOfUser(35,15));
		
//		cd.deleteSheetmusicFavorite(fav);
//		cd.insertSheetmusicFavorite(fav);

		
	}




}
