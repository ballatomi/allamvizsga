package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SongGenre;

public interface SongGenreService {
	List<SongGenre> getAllGenre() throws ServiceException;

	SongGenre getGenreById(int id) throws ServiceException;

	List<SongGenre> getGenreByFilter(String pattern) throws ServiceException;

	String insertGenre(SongGenre user) throws ServiceException;

	void updateGenre(SongGenre user) throws ServiceException;

	void deleteGenre(SongGenre user) throws ServiceException;

	SongGenre getGenreByName(String username) throws ServiceException;
}
