package edu.ubb.bsc.repo;

public abstract class DAOFactory {
	
	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	public abstract UserDAO getUserDAO();
	public abstract AdvertisementDAO getAdvertisementDAO();
	public abstract SheetMusicDAO getSheetMusicDAO();
	public abstract SongGenreDAO getSongGenreDAO();
	public abstract InstrumentSheetMusicDAO getInstrumentSheetMusicDAO();
	public abstract InstrumentDAO getInstrumentDAO();
	public abstract SheetMusicCommentDAO getSheetMusicCommentDAO();

}
