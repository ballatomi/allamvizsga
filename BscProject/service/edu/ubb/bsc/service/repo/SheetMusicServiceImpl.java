package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.SheetMusicDAO;
import edu.ubb.bsc.repo.model.SheetMusic;

public class SheetMusicServiceImpl implements SheetMusicService{

	private DAOFactory df;
	private SheetMusicDAO sd;
	
	public SheetMusicServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			sd = df.getSheetMusicDAO();
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}
	
	public List<SheetMusic> getAllSheetmusic() throws ServiceException {
		List<SheetMusic> object = null;
		try {
			object = sd.getAllSheetmusic();
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public SheetMusic getSheetmusicById(int id) throws ServiceException {
		SheetMusic object = null;
		try {
			object = sd.getSheetmusicById(id);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public List<SheetMusic> getSheetmusicByFilter(String pattern) throws ServiceException {
		List<SheetMusic> object = null;
		try {
			object = sd.getSheetmusicByFilter(pattern);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public String insertSheetmusic(SheetMusic user) throws ServiceException {
		String retmess = null;
		try {
			retmess = sd.insertSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return retmess;
	}

	public void updateSheetmusic(SheetMusic user) throws ServiceException {
		try {
			sd.updateSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public void deleteSheetmusic(SheetMusic user) throws ServiceException {
		try {
			sd.deleteSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public SheetMusic getSheetmusicByName(String name) throws ServiceException {
		SheetMusic object = null;
		try {
			object = sd.getSheetmusicByName(name);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

}
