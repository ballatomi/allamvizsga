package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetmusicFavorite;

public interface SheetMusicFavorites {

	List<SheetmusicFavorite> getSheetmusicFavoritesByUserId(int id) throws ServiceException;
	
	Boolean isFavoriteSheetMusicOfUser(int userID, int smID) throws ServiceException;

	String insertSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException;

	void updateSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException;

	void deleteSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException;

}
