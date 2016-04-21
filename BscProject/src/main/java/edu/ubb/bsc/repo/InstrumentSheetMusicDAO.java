package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;

public interface InstrumentSheetMusicDAO {
	List<InstrumentSheetmusic> getAllInstrumentSheetmusic() throws RepositoryException;

	InstrumentSheetmusic getInstrumentSheetmusicById(int id) throws RepositoryException;

	List<InstrumentSheetmusic> getInstrumentSheetmusicByInstrumentId(int id) throws RepositoryException;
	
	List<SheetMusic> getSheetMusicByInstrumentId(int id) throws RepositoryException;
	
	List<InstrumentSheetmusic> getInstrumentSheetmusicBySheetmusicId(int id) throws RepositoryException;
	
	String insertInstrumentSheetmusic(InstrumentSheetmusic user) throws RepositoryException;

	void updateInstrumentSheetmusic(InstrumentSheetmusic user) throws RepositoryException;

	void deleteInstrumentSheetmusic(InstrumentSheetmusic user) throws RepositoryException;
}
