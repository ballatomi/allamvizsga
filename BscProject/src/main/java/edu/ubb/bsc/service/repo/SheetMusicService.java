package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetMusic;

public interface SheetMusicService {
	
	List<SheetMusic> getAllSheetmusic() throws ServiceException;

	SheetMusic getSheetmusicById(int id) throws ServiceException;

	List<SheetMusic> getSheetmusicByFilter(String pattern) throws ServiceException;

	List<SheetMusic> getSheetmusicByUserID (int id) throws ServiceException;
	
	String insertSheetmusic(SheetMusic user) throws ServiceException;

	void updateSheetmusic(SheetMusic user) throws ServiceException;

	void deleteSheetmusic(SheetMusic user) throws ServiceException;
	
	SheetMusic getSheetmusicByName(String username) throws ServiceException;

}
