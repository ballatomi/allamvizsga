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

import edu.ubb.bsc.repo.model.SongGenre;

public class JdbcSongGenreDAO implements SongGenreDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSongGenreDAO.class);

	public JdbcSongGenreDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(SongGenre.class).configure()
					.buildSessionFactory();
			log.info("SongGenreDao Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public List<SongGenre> getAllGenre() throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SongGenre> object = null;
		try {
			tx = session.beginTransaction();
			object = session.createCriteria(SongGenre.class).list();
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

	public SongGenre getGenreById(int id) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SongGenre> getGenreByFilter(String pattern) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertGenre(SongGenre user) throws RepositoryException {
		Session session = factory.openSession();
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

		log.info("New Genre Inserted!");
		return id.toString();
	}

	public void updateGenre(SongGenre user) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	public void deleteGenre(SongGenre genre) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (genre != null) {
				
				//delete the foreign keys from sheet music
				Query query = session
						.createQuery("DELETE FROM SheetMusic WHERE song_genreID = :song_genreID");
				query.setParameter("song_genreID", genre.getSongGenreId());
				query.executeUpdate();
				
				session.delete(genre);
				log.info("User with ID= " + genre + " deleted!");
			} else {
				log.info("User with ID= " + genre+ " not exist!");
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

	public SongGenre getGenreByName(String username) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		JdbcSongGenreDAO cd = new JdbcSongGenreDAO();
		// cd.getCategoryById(2);

		SongGenre genre = new SongGenre();
		genre.setSongGenreId(21);
		cd.deleteGenre(genre);
		
	}
}
