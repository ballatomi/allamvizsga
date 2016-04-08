package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.Instrument;

public interface InstrumentDAO {

	List<Instrument> getAllInstrument() throws RepositoryException;

	Instrument getInstrumentById(int id) throws RepositoryException;

	List<Instrument> getInstrumentByFilter(String pattern) throws RepositoryException;

	String insertInstrument(Instrument user) throws RepositoryException;

	void updateInstrument(Instrument user) throws RepositoryException;

	void deleteInstrument(Instrument user) throws RepositoryException;

	Instrument getInstrumentByName(String username) throws RepositoryException;

}
