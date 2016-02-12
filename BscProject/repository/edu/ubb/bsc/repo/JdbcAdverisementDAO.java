package edu.ubb.bsc.repo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.Advertisement;

public class JdbcAdverisementDAO implements AdvertisementDAO {

	private static SessionFactory factoryAdv;
	private final static Logger log = LoggerFactory.getLogger(AdvertisementDAO.class);

	public JdbcAdverisementDAO() {
		try {
			factoryAdv = new AnnotationConfiguration().addAnnotatedClass(Advertisement.class).configure()
					.buildSessionFactory();
			log.info("AdvDao Factory was created!");
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Advertisement> getAllAdvertisement() throws RepositoryException {
		Session session = factoryAdv.openSession();
		Transaction tx = null;
		List<Advertisement> advert = null;
		try {
			tx = session.beginTransaction();
			advert = session.createQuery("FROM advertisement").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return advert;
	}

	public Advertisement getAdvertisementById(int id) throws RepositoryException {
		Session session = factoryAdv.openSession();
		Transaction tx = null;
		Advertisement adv = null;
		try {
			tx = session.beginTransaction();
			adv = (Advertisement) session.get(Advertisement.class, id);
			log.info("Get Advertisement by ID: " + adv);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return adv;
	}

	public List<Advertisement> getAdvertisementByFilter(String pattern) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertAdvertisement(Advertisement adv) throws RepositoryException {
		Session session = factoryAdv.openSession();
		Transaction tx = null;
		Integer AdvId = 0;

		try {
			tx = session.beginTransaction();
			AdvId = (Integer) session.save(adv); // save to DB
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		log.info("New Adv Inserted!");
		return AdvId.toString();
	}

	public void updateAdvertisement(Advertisement adv) throws RepositoryException {
		Session session = factoryAdv.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(adv);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public void deleteAdvertisement(Advertisement adv) throws RepositoryException {
		Session session = factoryAdv.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (adv != null) {
				session.delete(adv);
				log.info("User with ID= " + adv.getAdvId()+ " deleted!");
			} else {
				log.info("User with ID= " + adv.getAdvId() + " not exist!");
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

	public Advertisement getAdvertisementByName(String username) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
