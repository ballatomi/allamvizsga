package edu.ubb.bsc.repo;

import edu.ubb.bsc.repo.model.Sheetmusic;

public abstract class DAOFactory {
	
	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	public abstract UserDAO getUserDAO();
	public abstract AdvertisementDAO getAdvertisementDAO();
	public abstract SheetMusicDAO getSheetMusicDAO();

}
