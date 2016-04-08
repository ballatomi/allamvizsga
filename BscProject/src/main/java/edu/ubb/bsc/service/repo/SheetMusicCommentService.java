package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.SheetmusicComment;

public interface SheetMusicCommentService {
	List<SheetmusicComment> getAllSheetMusicComment() throws ServiceException;

	SheetmusicComment getSheetmusicCommentById(int id) throws ServiceException;

	List<SheetmusicComment> getSheetmusicCommentBySheetMusicId(int id) throws ServiceException;
	
	String insertSheetmusicComment(SheetmusicComment comment) throws ServiceException;

	void updateSheetmusicComment(SheetmusicComment comment) throws ServiceException;

	void deleteSheetmusicComment(SheetmusicComment comment) throws ServiceException;
}
