package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.SheetMusicDAO;
import edu.ubb.bsc.repo.model.Sheetmusic;

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
	
	public List<Sheetmusic> getAllSheetmusic() throws ServiceException {
		List<Sheetmusic> object = null;
		try {
			object = sd.getAllSheetmusic();
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public Sheetmusic getSheetmusicById(int id) throws ServiceException {
		Sheetmusic object = null;
		try {
			object = sd.getSheetmusicById(id);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public List<Sheetmusic> getSheetmusicByFilter(String pattern) throws ServiceException {
		List<Sheetmusic> object = null;
		try {
			object = sd.getSheetmusicByFilter(pattern);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public String insertSheetmusic(Sheetmusic user) throws ServiceException {
		String retmess = null;
		try {
			retmess = sd.insertSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return retmess;
	}

	public void updateSheetmusic(Sheetmusic user) throws ServiceException {
		try {
			sd.updateSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public void deleteSheetmusic(Sheetmusic user) throws ServiceException {
		try {
			sd.deleteSheetmusic(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public Sheetmusic getSheetmusicByName(String name) throws ServiceException {
		Sheetmusic object = null;
		try {
			object = sd.getSheetmusicByName(name);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

}
