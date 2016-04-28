
package edu.ubb.bsc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SheetmusicFavorite;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicFavoritesImpl;

/**
 * @author Tamas
 *
 */
@Path("sheet/favorite")
public class RestFavoriteSheetmusic {

	private final static Logger log = LoggerFactory.getLogger(RestFavoriteSheetmusic.class);

	/**
	 * Get user's favorite
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@Path("/getUsersFavorite")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetmusicFavorite> getUsersFavorite(@Context HttpServletRequest request) {
		SheetMusicFavoritesImpl smfImpl;
		List<SheetmusicFavorite> favList = new ArrayList<SheetmusicFavorite>();

		try {
			smfImpl = new SheetMusicFavoritesImpl();

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");

			if (user != null) {
				favList = smfImpl.getSheetmusicFavoritesByUserId(user.getUserId());
			}

		} catch (ServiceException e) {
			log.error("Error in get SheetmusicFavorite", e);
		}
		return favList;
	}

	@GET
	@Path("/isFavoriteUserSheetmusic/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String isFavoriteUserSheetmusic(@PathParam("id") Integer smID,
			@Context HttpServletRequest request) {
		SheetMusicFavoritesImpl smfImpl;
		
		String resp = null;
		try {
			smfImpl = new SheetMusicFavoritesImpl();

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");

			if (user != null) {
				resp = smfImpl.isFavoriteSheetMusicOfUser(smID, user.getUserId()) ? "false" : "true";
			}

		} catch (ServiceException e) {
			log.error("Error in get SheetmusicFavorite", e);
		}
		return resp;
	}

	/**
	 * Save sheetmusic to user's favorite
	 * 
	 * @param sid
	 * @param request
	 * @return user's favorite
	 */
	@PUT
	@Path("/addToFavorites/{id}")
	public void saveSheetMusicFavorite(@PathParam("id") Integer sid,
			@Context HttpServletRequest request) {
		SheetMusicFavoritesImpl smfImpl;

		try {
			smfImpl = new SheetMusicFavoritesImpl();

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");
			System.out.println("Logged in user: " + user);

			SheetmusicFavorite fav = new SheetmusicFavorite();
			fav.setUser(user);

			SheetMusic sm = new SheetMusic();
			sm.setSheetMusicId(sid);
			fav.setSheetMusic(sm);

			smfImpl.insertSheetmusicFavorite(fav);

		} catch (ServiceException e) {
			log.error("Error in save SheetmusicFavorite", e);
		}
	}

	@DELETE
	@Path("/deleteFromFavorites/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public void deleteSheetMusicFavorite(@PathParam("id") Integer sid,
			@Context HttpServletRequest request) {
		SheetMusicFavoritesImpl smfImpl;

		try {
			smfImpl = new SheetMusicFavoritesImpl();

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");
			System.out.println("Logged in user: " + user);

			SheetmusicFavorite fav = new SheetmusicFavorite();
			fav.setUser(user);

			SheetMusic sm = new SheetMusic();
			sm.setSheetMusicId(sid);
			fav.setSheetMusic(sm);
			
			smfImpl.deleteSheetmusicFavorite(fav);

			System.out.println("deleted " + sid);

		} catch (ServiceException e) {
			log.error("Error in save SheetmusicFavorite", e);
		}
	}

}
