package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.Instrument;

public interface InstrumentService {
	List<Instrument> getAllInstrument() throws ServiceException;

	Instrument getInstrumentById(int id) throws ServiceException;

	List<Instrument> getInstrumentByFilter(String pattern) throws ServiceException;

	String insertInstrument(Instrument user) throws ServiceException;

	void updateInstrument(Instrument user) throws ServiceException;

	void deleteInstrument(Instrument user) throws ServiceException;

}
