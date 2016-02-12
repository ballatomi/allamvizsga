package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.UserDAO;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.security.PasswordEncrypter;

public class UserServiceImpl implements UserService {

	private DAOFactory df;
	private UserDAO ud;

	public UserServiceImpl() throws ServiceException {
		try {
			df = DAOFactory.getInstance();
			ud = df.getUserDAO();
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public List<User> getAllUsers() throws ServiceException {
		List<User> users = null;
		try {
			users = ud.getAllUsers();
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return users;
	}

	public User getUserById(int id) throws ServiceException {
		User user = null;
		try {
			user = ud.getUserById(id);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return user;
	}

	public List<User> getUsersByFilter(String pattern) throws ServiceException {
		List<User> users = null;
		try {
			users = ud.getUsersByFilter(pattern);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return users;
	}

	public String insertUser(User user) throws ServiceException {
		String retmess = null;
		try {
			user.setUserPassword(PasswordEncrypter.generateHashedPassword(user.getUserPassword()));
			retmess = ud.insertUser(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return retmess;
	}

	public void updateUser(User user) throws ServiceException {
		try {
			user.setUserPassword(PasswordEncrypter.generateHashedPassword(user.getUserPassword()));
			ud.updateUser(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public void deleteUser(User user) throws ServiceException {
		try {
			ud.deleteUser(user);
		} catch (RepositoryException e) {
			new ServiceException();
		}
	}

	public User loginAuthenticateUser(String userName, String password) throws ServiceException {
		User user = null;
		try {
			password = PasswordEncrypter.generateHashedPassword(password);
			
			user = ud.loginAuthenticateUser(userName, password);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return user;
	}

	public User getUserByName(String name) throws ServiceException {
		User user = null;
		try {
			user = ud.getUserByName(name);
		} catch (RepositoryException e) {
			new ServiceException();
		}
		return user;
	}

}
