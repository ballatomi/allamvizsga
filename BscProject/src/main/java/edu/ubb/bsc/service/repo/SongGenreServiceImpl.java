package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.SongGenreDAO;
import edu.ubb.bsc.repo.model.SongGenre;

public class SongGenreServiceImpl implements SongGenreService{

	private DAOFactory df;
	private SongGenreDAO ud;
	
	public SongGenreServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			ud = df.getSongGenreDAO();
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
	}
	
	public List<SongGenre> getAllGenre() throws ServiceException {
		List<SongGenre> object = null;
		try {
			object = ud.getAllGenre();
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return object;
	}

	public SongGenre getGenreById(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SongGenre> getGenreByFilter(String pattern) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String insertGenre(SongGenre user) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateGenre(SongGenre user) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	public void deleteGenre(SongGenre user) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	public SongGenre getGenreByName(String username) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
