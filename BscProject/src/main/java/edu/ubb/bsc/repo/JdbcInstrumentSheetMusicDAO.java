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

import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SongGenre;

public class JdbcInstrumentSheetMusicDAO implements InstrumentSheetMusicDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcSongGenreDAO.class);

	public JdbcInstrumentSheetMusicDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(InstrumentSheetmusic.class).configure()
					.buildSessionFactory();
			log.info("JdbcInstrumentSheetMusicDAO Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public List<InstrumentSheetmusic> getAllInstrumentSheetmusic() throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<InstrumentSheetmusic> object = null;
		try {
			tx = session.beginTransaction();
			object = session.createCriteria(InstrumentSheetmusic.class).list();
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

	public InstrumentSheetmusic getInstrumentSheetmusicById(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		InstrumentSheetmusic object = null;
		try {
			tx = session.beginTransaction();
			object = (InstrumentSheetmusic) session.get(InstrumentSheetmusic.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get InstrumentSheetmusic by ID!", e);
			throw new RepositoryException("Error get InstrumentSheetmusic by ID", e);
		} finally {
			session.close();
		}
		return object;
	}

	public List<InstrumentSheetmusic> getInstrumentSheetmusicByInstrumentId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<InstrumentSheetmusic> object = null;
		try {
			tx = session.beginTransaction();
			//object = (InstrumentSheetmusic) session.get(Instrument.class, id);
			//object = session.createCriteria(Instrument.class).list();
			
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM InstrumentSheetmusic WHERE instrumentID = :instrumentID");
			query.setParameter("instrumentID", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get Instrument by ID!", e);
			throw new RepositoryException("Error get Instrument by ID", e);
		} finally {
			session.close();
		}
		return object;
	}
	
	public List<SheetMusic> getSheetMusicByInstrumentId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetMusic> object = null;
		try {
			tx = session.beginTransaction();
			//object = (InstrumentSheetmusic) session.get(Instrument.class, id);
			//object = session.createCriteria(Instrument.class).list();
			
			Query query = session
					.createQuery("select sheetMusic FROM InstrumentSheetmusic WHERE instrumentID = :instrumentID");
			query.setParameter("instrumentID", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get SheetMusic by Instrument ID!", e);
			throw new RepositoryException("Error get Instrument by ID", e);
		} finally {
			session.close();
		}
		return object;
	}
	
	public List<SheetMusic> getSheetMusicByGenreId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SheetMusic> object = null;
		try {
			
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM SheetMusic WHERE song_genreId = :songGenreId");
			query.setParameter("songGenreId", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get SheetMusic by GenreID!", e);
			throw new RepositoryException("Error get Genre by ID", e);
		} finally {
			session.close();
		}
		return object;
	}
	

	public List<InstrumentSheetmusic> getInstrumentSheetmusicBySheetmusicId(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<InstrumentSheetmusic> object = null;
		try {
			tx = session.beginTransaction();
			
			//need to set From [Mapped class-on table]
			Query query = session
					.createQuery("FROM InstrumentSheetmusic WHERE sheetMusicID = :sheetMusicID");
			query.setParameter("sheetMusicID", id);
			object = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Error get InstrumentSheetmusic by SheetmusicID!", e);
			throw new RepositoryException("Error get InstrumentSheetmusic by SheetmusicID", e);
		} finally {
			session.close();
		}
		return object;
	}

	public String insertInstrumentSheetmusic(InstrumentSheetmusic sheetmusic) throws RepositoryException {
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

		log.info("New InstrumentSheetmusic Inserted!");
		return UserId.toString();
	}

	public void updateInstrumentSheetmusic(InstrumentSheetmusic sheetmusic) throws RepositoryException {
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

	public void deleteInstrumentSheetmusic(InstrumentSheetmusic sheetmusic) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (sheetmusic != null) {
				session.delete(sheetmusic);
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

	public static void main(String[] args) {
		JdbcInstrumentSheetMusicDAO cd = new JdbcInstrumentSheetMusicDAO();
		List<SheetMusic> sm = cd.getSheetMusicByGenreId(3);
		
		for (SheetMusic sheetMusic : sm) {
			System.out.println(sheetMusic);
		}
		// cd.getCategoryById(2);
		// List<InstrumentSheetmusic> sm = cd.getAllInstrumentSheetmusic();
//		Instrument is = cd.getInstrumentSheetmusicByInstrumentId(1);
//
		
//		System.out.println(is.getName());
//		List<InstrumentSheetmusic> smi = cd.getInstrumentSheetmusicByInstrumentId(18);
//


	}




}
