package edu.ubb.bsc.repo.test;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.SheetMusicDAO;
import edu.ubb.bsc.repo.UserDAO;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.User;

public class TestDB {

	
	//private final static Logger log = LoggerFactory.getLogger(TestDB.class);
	private DAOFactory df = DAOFactory.getInstance(); 
	public TestDB(){
	}

	public void testUser(){
		UserDAO ud = df.getUserDAO();

		User user = new User();
		user.setUserName("Tamas");
		user.setUserPassword("Tamas");
		user.setUserMail("Tamas@yahoo.com");
		user.setUserRight(1);
		user.setUserTel("0123");

		//ud.insertUser(user);
		ud.getUserByName("Tamas");
	}
	
	public void testSheetMusic(){
		SheetMusicDAO sd = df.getSheetMusicDAO();
		SheetMusic sm = sd.getSheetmusicById(35);
		System.out.println(sm);
	}
	
	public static void main(String[] args) {
		TestDB test = new TestDB();
		test.testSheetMusic();
		
	}

}
