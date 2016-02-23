package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.Advertisement;
import edu.ubb.bsc.repo.model.Sheetmusic;

public interface SheetMusicDAO {

	List<Sheetmusic> getAllSheetmusic() throws RepositoryException;

	Sheetmusic getSheetmusicById(int id) throws RepositoryException;

	List<Sheetmusic> getSheetmusicByFilter(String pattern) throws RepositoryException;

	String insertSheetmusic(Sheetmusic user) throws RepositoryException;

	void updateSheetmusic(Sheetmusic user) throws RepositoryException;

	void deleteSheetmusic(Sheetmusic user) throws RepositoryException;
	
	Sheetmusic getSheetmusicByName(String username) throws RepositoryException;
}
