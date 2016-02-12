package edu.ubb.bsc.repo;

public class JdbcDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new JdbcUserDAO();
	}

	@Override
	public AdvertisementDAO getAdvertisementDAO() {
		return new JdbcAdverisementDAO();
	}

}
