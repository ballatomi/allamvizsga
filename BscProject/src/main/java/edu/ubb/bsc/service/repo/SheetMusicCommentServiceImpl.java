package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.SheetMusicCommentDAO;
import edu.ubb.bsc.repo.model.SheetmusicComment;

public class SheetMusicCommentServiceImpl implements SheetMusicCommentService{

	private DAOFactory df;
	private SheetMusicCommentDAO sd;
	
	public SheetMusicCommentServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			sd = df.getSheetMusicCommentDAO();
		} catch (RepositoryException e) {
			new ServiceException("Error in Service SheetMusicComment",e);
		}
	}
	
	public List<SheetmusicComment> getAllSheetMusicComment() throws ServiceException {
		List<SheetmusicComment> object = null;
		try {
			object = sd.getAllSheetMusicComment();
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public SheetmusicComment getSheetmusicCommentById(int id) throws ServiceException {
		SheetmusicComment object = null;
		try {
			object = sd.getSheetmusicCommentById(id);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public List<SheetmusicComment> getSheetmusicCommentBySheetMusicId(int id) throws ServiceException {
		List<SheetmusicComment> object = null;
		try {
			object = sd.getSheetmusicCommentBySheetMusicId(id);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return object;
	}

	public String insertSheetmusicComment(SheetmusicComment comment) throws ServiceException {
		String retmess = null;
		try {
			retmess = sd.insertSheetmusicComment(comment);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return retmess;
	}

	public void updateSheetmusicComment(SheetmusicComment comment) throws ServiceException {
		try {
			sd.updateSheetmusicComment(comment);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public void deleteSheetmusicComment(SheetmusicComment comment) throws ServiceException {
		try {
			sd.deleteSheetmusicComment(comment);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

}
