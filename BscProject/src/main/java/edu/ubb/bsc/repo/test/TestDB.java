package edu.ubb.bsc.repo.test;

import java.util.List;

import edu.ubb.bsc.repo.JdbcSheetMusicFavoriteDAO;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SheetmusicFavorite;
import edu.ubb.bsc.repo.model.User;

public class TestDB {

	
	//private final static Logger log = LoggerFactory.getLogger(TestDB.class);
//	private DAOFactory df = DAOFactory.getInstance(); 
//	public TestDB(){
//	}
//
//	public void testUser(){
//		UserDAO ud = df.getUserDAO();
//
//		User user = new User();
//		user.setUserName("Tamas");
//		user.setUserPassword("Tamas");
//		user.setUserMail("Tamas@yahoo.com");
//		user.setUserRight(1);
//		user.setUserTel("0123");
//
//		//ud.insertUser(user);
//		ud.getUserByName("Tamas");
//	}
//	
//	public void testSheetMusic(){
//		SheetMusicDAO sd = df.getSheetMusicDAO();
//		SheetMusic sm = sd.getSheetmusicById(35);
//		System.out.println(sm);
//	}
//	
//	public void testSheetMusicComment(){
//		SheetMusicCommentDAO sd = df.getSheetMusicCommentDAO();
//		SheetmusicComment smc = new SheetmusicComment();
//		User u = new User();
//		u.setUserId(12);
//		smc.setUser(u);
//		
//		SheetMusic sm = new SheetMusic();
//		sm.setSheetMusicId(38);
//		smc.setSheetMusic(sm);
//		smc.setComment("It is a new comment");
//		sd.insertSheetmusicComment(smc);
//	}
	
	
	public static void main(String[] args) {
//		TestDB test = new TestDB();
//		test.testSheetMusic();
//		test.testSheetMusicComment();
		
		JdbcSheetMusicFavoriteDAO cd = new JdbcSheetMusicFavoriteDAO();
//		SheetmusicFavorite fav = new SheetmusicFavorite();
//		
//		User u = new User();u.setUserId(14);
//		SheetMusic sm = new SheetMusic();
//		sm.setSheetMusicId(39);
//		
//		fav.setUser(u);
//		fav.setSheetMusic(sm);
//		
//		cd.insertSheetmusicFavorite(fav);
		
		List<SheetmusicFavorite> sm1 = cd.getSheetmusicFavoritesByUserId(14);
		for (SheetmusicFavorite sheetMusic : sm1) {
			System.out.println(sheetMusic);
		}
		
		
	}

}
