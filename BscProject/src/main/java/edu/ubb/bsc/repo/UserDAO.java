package edu.ubb.bsc.repo;

import java.util.List;
import edu.ubb.bsc.repo.model.*;


public interface UserDAO {

	List<User> getAllUsers() throws RepositoryException;

	User getUserById(int id) throws RepositoryException;

	List<User> getUsersByFilter(String pattern) throws RepositoryException;

	String insertUser(User user) throws RepositoryException;

	void updateUser(User user) throws RepositoryException;
	
	void updateUserRigth(User user) throws RepositoryException;
	
	void deleteUser(User user) throws RepositoryException;
	
	User loginAuthenticateUser (String userName, String password) throws RepositoryException;

	User getUserByName(String username) throws RepositoryException;

}
