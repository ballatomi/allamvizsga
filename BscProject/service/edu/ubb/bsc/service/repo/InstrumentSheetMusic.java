package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.InstrumentSheetmusic;

public interface InstrumentSheetMusic {
	List<InstrumentSheetmusic> getAllInstrumentSheetmusic() throws ServiceException;

	InstrumentSheetmusic getInstrumentSheetmusicById(int id) throws ServiceException;

	List<InstrumentSheetmusic> getInstrumentSheetmusicByInstrumentId(int id) throws ServiceException;
	
	List<InstrumentSheetmusic> getInstrumentSheetmusicBySheetmusicId(int id) throws ServiceException;
	
	String insertInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException;

	void updateInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException;

	void deleteInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException;
}
