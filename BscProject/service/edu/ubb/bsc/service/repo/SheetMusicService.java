package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.Sheetmusic;

public interface SheetMusicService {
	
	List<Sheetmusic> getAllSheetmusic() throws ServiceException;

	Sheetmusic getSheetmusicById(int id) throws ServiceException;

	List<Sheetmusic> getSheetmusicByFilter(String pattern) throws ServiceException;

	String insertSheetmusic(Sheetmusic user) throws ServiceException;

	void updateSheetmusic(Sheetmusic user) throws ServiceException;

	void deleteSheetmusic(Sheetmusic user) throws ServiceException;
	
	Sheetmusic getSheetmusicByName(String username) throws ServiceException;

}
