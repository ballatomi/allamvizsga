package edu.ubb.bsc.repo;

public abstract class DAOFactory {
	
	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	public abstract UserDAO getUserDAO();
	public abstract AdvertisementDAO getAdvertisementDAO();
}
