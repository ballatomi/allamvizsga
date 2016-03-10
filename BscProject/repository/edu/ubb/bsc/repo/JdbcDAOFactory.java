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

	@Override
	public SheetMusicDAO getSheetMusicDAO() {
		return new JdbcSheetMusicDAO();
	}

	@Override
	public SongGenreDAO getSongGenreDAO() {
		return new JdbcSongGenreDAO();
	}

	@Override
	public InstrumentSheetMusicDAO getInstrumentSheetMusicDAO() {
		return new JdbcInstrumentSheetMusicDAO();
	}
	
	@Override
	public InstrumentDAO getInstrumentDAO() {
		return new JdbcInstrumentDAO();
	}

}
