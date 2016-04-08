package edu.ubb.bsc.repo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.SheetMusic;
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
		// TODO Auto-generated method stub
		return null;
	}

	public void updateGenre(SongGenre user) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	public void deleteGenre(SongGenre user) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	public SongGenre getGenreByName(String username) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		JdbcSongGenreDAO cd = new JdbcSongGenreDAO();
		// cd.getCategoryById(2);
		List<SongGenre> sm = cd.getAllGenre();
		for (Iterator<?> iterator = sm.iterator(); iterator.hasNext();) {
			SongGenre a = (SongGenre) iterator.next();
			System.out.println(a.getSongGenreId());
			System.out.println(a.getSongGenreName());

		}
	}
}
