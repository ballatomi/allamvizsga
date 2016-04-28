package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetmusicFavorite;

public interface SheetMusicFavoriteDAO {

	List<SheetmusicFavorite> getSheetmusicFavoritesByUserId(int id) throws RepositoryException;

	Boolean isFavoriteSheetMusicOfUser(int smID, int userID) throws RepositoryException;
	
	String insertSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException;

	void updateSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException;

	void deleteSheetmusicFavorite(SheetmusicFavorite fav) throws RepositoryException;

}
