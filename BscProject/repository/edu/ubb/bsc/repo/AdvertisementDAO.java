package edu.ubb.bsc.repo;

import java.util.List;

import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.model.Advertisement;

public interface AdvertisementDAO {

	List<Advertisement> getAllAdvertisement() throws RepositoryException;

	Advertisement getAdvertisementById(int id) throws RepositoryException;

	List<Advertisement> getAdvertisementByFilter(String pattern) throws RepositoryException;

	String insertAdvertisement(Advertisement user) throws RepositoryException;

	void updateAdvertisement(Advertisement user) throws RepositoryException;

	void deleteAdvertisement(Advertisement user) throws RepositoryException;
	
	Advertisement getAdvertisementByName(String username) throws RepositoryException;
}
