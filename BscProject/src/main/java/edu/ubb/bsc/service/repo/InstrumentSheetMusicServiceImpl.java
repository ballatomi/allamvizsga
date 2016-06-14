package edu.ubb.bsc.service.repo;

import java.util.Iterator;
import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.InstrumentSheetMusicDAO;
import edu.ubb.bsc.repo.JdbcInstrumentSheetMusicDAO;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;

public class InstrumentSheetMusicServiceImpl implements InstrumentSheetMusicService {

	private DAOFactory df;
	private InstrumentSheetMusicDAO sd;
	
	public InstrumentSheetMusicServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			sd = df.getInstrumentSheetMusicDAO();
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
	}
	
	public List<InstrumentSheetmusic> getAllInstrumentSheetmusic() throws ServiceException {
		List<InstrumentSheetmusic> object = null;
		try {
			object = sd.getAllInstrumentSheetmusic();
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}

	public InstrumentSheetmusic getInstrumentSheetmusicById(int id) throws ServiceException {
		InstrumentSheetmusic object = null;
		try {
			object = sd.getInstrumentSheetmusicById(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}

	public List<InstrumentSheetmusic> getInstrumentSheetmusicByInstrumentId(int id) throws ServiceException {
		List<InstrumentSheetmusic> object = null;
		try {
			object = sd.getInstrumentSheetmusicByInstrumentId(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}

	public List<SheetMusic> getSheetMusicByInstrumentId(int id) throws ServiceException {
		List<SheetMusic> object = null;
		try {
			object = sd.getSheetMusicByInstrumentId(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}

	
	public List<SheetMusic> getSheetMusicByGenreId(int id) throws ServiceException {
		List<SheetMusic> object = null;
		try {
			object = sd.getSheetMusicByGenreId(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}
	
	public List<InstrumentSheetmusic> getInstrumentSheetmusicBySheetmusicId(int id) throws ServiceException {
		List<InstrumentSheetmusic> object = null;
		try {
			object = sd.getInstrumentSheetmusicBySheetmusicId(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return object;
	}

	public String insertInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException {
		String retmess = null;
		try {
			retmess = sd.insertInstrumentSheetmusic(is);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		return retmess;
	}

	public void updateInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException {
		try {
			sd.updateInstrumentSheetmusic(is);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
	}

	public void deleteInstrumentSheetmusic(InstrumentSheetmusic is) throws ServiceException {
		try {
			sd.deleteInstrumentSheetmusic(is);
		} catch (RepositoryException e) {
			throw new ServiceException("Error in Service InstrumentSheetMusic",e);
		}
		
	}
	
	public static void main(String[] args) {
		JdbcInstrumentSheetMusicDAO im = new JdbcInstrumentSheetMusicDAO();
		// cd.getCategoryById(2);
		List<InstrumentSheetmusic> sm = im.getAllInstrumentSheetmusic();
		for (Iterator<?> iterator = sm.iterator(); iterator.hasNext();) {
			InstrumentSheetmusic a = (InstrumentSheetmusic) iterator.next();
			System.out.println(a.getInstrument());
			System.out.println(a.getSheetMusic());
		}
	}



}
