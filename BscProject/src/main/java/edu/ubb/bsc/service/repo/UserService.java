package edu.ubb.bsc.service.repo;

import java.util.List;

import edu.ubb.bsc.repo.model.User;

public interface UserService {

	List<User> getAllUsers() throws ServiceException;

	User getUserById(int id) throws ServiceException;

	List<User> getUsersByFilter(String pattern) throws ServiceException;

	String insertUser(User user) throws ServiceException;

	void updateUser(User user) throws ServiceException;

	void updateUserRigth(User user) throws ServiceException;
	
	void deleteUser(User user) throws ServiceException;

	User loginAuthenticateUser(String userName, String password) throws ServiceException;

	User getUserByName(String name) throws ServiceException;

}
