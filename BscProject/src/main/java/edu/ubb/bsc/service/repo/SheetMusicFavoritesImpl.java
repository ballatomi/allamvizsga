package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.SheetMusicFavoriteDAO;
import edu.ubb.bsc.repo.model.SheetmusicFavorite;

public class SheetMusicFavoritesImpl implements SheetMusicFavorites{

	private DAOFactory df;
	private SheetMusicFavoriteDAO ud;
	
	public SheetMusicFavoritesImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			ud = df.getSheetMusicFavoriteDAO();
		} catch (RepositoryException e) {
			throw new ServiceException("Error in SheetMusicFavorites service", e);
		}
	}
	
	public List<SheetmusicFavorite> getSheetmusicFavoritesByUserId(int id) throws ServiceException {
		
		List<SheetmusicFavorite> object = null;
		try {
			object = ud.getSheetmusicFavoritesByUserId(id);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return object;
	}
	
	public Boolean isFavoriteSheetMusicOfUser(int smID, int userID) throws ServiceException {
		try {
			return ud.isFavoriteSheetMusicOfUser(smID, userID);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
	}

	public String insertSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException {
		String retmess = null;
		try {
			retmess = ud.insertSheetmusicFavorite(fav);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return retmess;
	}

	public void updateSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException {
		try {
			ud.updateSheetmusicFavorite(fav);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
	}

	public void deleteSheetmusicFavorite(SheetmusicFavorite fav) throws ServiceException {
		try {
			ud.deleteSheetmusicFavorite(fav);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
	}



}
