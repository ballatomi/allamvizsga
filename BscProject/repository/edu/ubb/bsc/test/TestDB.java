package edu.ubb.bsc.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.UserDAO;
import edu.ubb.bsc.repo.model.User;

public class TestDB {

	private DAOFactory df;
	private UserDAO ud;
	private final static Logger log = LoggerFactory.getLogger(TestDB.class);
	
	public TestDB(){
		df = DAOFactory.getInstance();
		ud = df.getUserDAO();

		User user = new User();
		user.setUserName("Tamas");
		user.setUserPassword("Tamas");
		user.setUserMail("Tamas@yahoo.com");
		user.setUserRight(1);
		user.setUserTel("0123");

		ud.insertUser(user);
		 
	}
	
	public static void main(String[] args) {
		new TestDB();
	}

}
