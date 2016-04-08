package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SongGenre;

public interface SongGenreDAO {

	List<SongGenre> getAllGenre() throws RepositoryException;

	SongGenre getGenreById(int id) throws RepositoryException;

	List<SongGenre> getGenreByFilter(String pattern) throws RepositoryException;

	String insertGenre(SongGenre user) throws RepositoryException;

	void updateGenre(SongGenre user) throws RepositoryException;

	void deleteGenre(SongGenre user) throws RepositoryException;

	SongGenre getGenreByName(String username) throws RepositoryException;

}
