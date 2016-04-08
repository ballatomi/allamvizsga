package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetMusic;

public interface SheetMusicDAO {

	List<SheetMusic> getAllSheetmusic() throws RepositoryException;

	SheetMusic getSheetmusicById(int id) throws RepositoryException;

	List<SheetMusic> getSheetmusicByFilter(String pattern) throws RepositoryException;

	String insertSheetmusic(SheetMusic user) throws RepositoryException;

	void updateSheetmusic(SheetMusic user) throws RepositoryException;

	void deleteSheetmusic(SheetMusic user) throws RepositoryException;
	
	SheetMusic getSheetmusicByName(String username) throws RepositoryException;
}
