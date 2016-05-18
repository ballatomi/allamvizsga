package edu.ubb.bsc.repo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;

public class JdbcInstrumentDAO implements InstrumentDAO {

	private static SessionFactory factory;
	private final static Logger log = LoggerFactory.getLogger(JdbcInstrumentDAO.class);

	public JdbcInstrumentDAO() {
		try {
			factory = new AnnotationConfiguration().addAnnotatedClass(Instrument.class).configure()
					.buildSessionFactory();
			log.info("Instrument Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public List<Instrument> getAllInstrument() throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Instrument> object = null;
		try {
			tx = session.beginTransaction();
			object = session.createCriteria(Instrument.class).list();
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

	public Instrument getInstrumentById(int id) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		Instrument object = null;
		try {
			tx = session.beginTransaction();
			object = (Instrument) session.get(Instrument.class, id);

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

	public List<Instrument> getInstrumentByFilter(String pattern) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Instrument> object = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM Instrument WHERE name = :name");
			query.setParameter("name", pattern);
			object = query.list();

//			for (Iterator<?> iterator = object.iterator(); iterator.hasNext();) {
//				Instrument inst = (Instrument) iterator.next();
//				System.out.println(inst.getName());
//				
//			}

		
			log.info("Instrument was get by pattern: " + pattern);
			
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

	public String insertInstrument(Instrument user) throws RepositoryException {
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

		log.info("New Instrument Inserted!");
		return id.toString();
	}
	public void updateInstrument(Instrument user) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	public void deleteInstrument(Instrument instrument) throws RepositoryException {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (instrument != null) {
				
				//delete the foreign keys
				Query query = session
						.createQuery("DELETE FROM InstrumentSheetmusic WHERE instrumentId = :instrumentId");
				query.setParameter("instrumentId", instrument.getInstrumentId());
				query.executeUpdate();
				
				session.delete(instrument);
				log.info("Instrument with ID= " + instrument + " deleted!");
			} else {
				log.info("Instrument with ID= " + instrument+ " not exist!");
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

	public Instrument getInstrumentByName(String username) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		JdbcInstrumentDAO cd = new JdbcInstrumentDAO();
		// cd.getCategoryById(2);
		//List<Instrument> sm = cd.getAllInstrument();
//		List<Instrument> sm = cd.getInstrumentByFilter("Trumpet");
		
		Instrument i = new Instrument();
		i.setInstrumentId(21);
		cd.deleteInstrument(i);
		
	}

}
