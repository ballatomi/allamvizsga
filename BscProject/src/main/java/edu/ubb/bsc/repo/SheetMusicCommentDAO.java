package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetmusicComment;

public interface SheetMusicCommentDAO {

	List<SheetmusicComment> getAllSheetMusicComment() throws RepositoryException;

	SheetmusicComment getSheetmusicCommentById(int id) throws RepositoryException;

	List<SheetmusicComment> getSheetmusicCommentBySheetMusicId(int id) throws RepositoryException;
	
	String insertSheetmusicComment(SheetmusicComment comment) throws RepositoryException;

	void updateSheetmusicComment(SheetmusicComment comment) throws RepositoryException;

	void deleteSheetmusicComment(SheetmusicComment comment) throws RepositoryException;
}
