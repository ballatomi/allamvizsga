package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.InstrumentDAO;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.model.Instrument;

public class InstrumentServiceImpl implements InstrumentService{

	private DAOFactory df;
	private InstrumentDAO ud;
	
	public InstrumentServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			ud = df.getInstrumentDAO();
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
	}
	
	public List<Instrument> getAllInstrument() throws ServiceException {
		List<Instrument> object = null;
		try {
			object = ud.getAllInstrument();
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return object;
	}

	public Instrument getInstrumentById(int id) throws ServiceException {
		Instrument object = null;
		try {
			object = ud.getInstrumentById(id);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return object;
	}

	public List<Instrument> getInstrumentByFilter(String pattern) throws ServiceException {
		List<Instrument> object = null;
		try {
			object = ud.getInstrumentByFilter(pattern);
		} catch (RepositoryException e) {
			throw new ServiceException();
		}
		return object;
	}

	public String insertInstrument(Instrument user) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateInstrument(Instrument user) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	public void deleteInstrument(Instrument user) throws ServiceException {
		// TODO Auto-generated method stub
	}

}
