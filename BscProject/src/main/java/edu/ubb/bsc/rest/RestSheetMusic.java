package edu.ubb.bsc.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataParam;

import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SheetmusicComment;
import edu.ubb.bsc.repo.model.SongGenre;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.InstrumentService;
import edu.ubb.bsc.service.repo.InstrumentServiceImpl;
import edu.ubb.bsc.service.repo.InstrumentSheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicCommentServiceImpl;
import edu.ubb.bsc.service.repo.SheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.SongGenreServiceImpl;

@Path("sheet")
public class RestSheetMusic {

	private final static Logger log = LoggerFactory.getLogger(RestSheetMusic.class);

	/**
	 * get Instrument Sheetmusic by sheetmusic ID
	 * 
	 * @return
	 */
	@GET
	@Path("/getInstrumentSheetmusicBySheetID/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<InstrumentSheetmusic> getInstrumentSheetmusicBySheetID(@PathParam("id") Integer id) {
		InstrumentSheetMusicServiceImpl ismService;
		List<InstrumentSheetmusic> is = new ArrayList<InstrumentSheetmusic>();
		try {
			ismService = new InstrumentSheetMusicServiceImpl();
			is = ismService.getInstrumentSheetmusicBySheetmusicId(id);

			log.info("Get InstrumentSheetmusic by sheet music ID");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return is;
	}

	/**
	 * Get Sheetmusic by sheetmusic ID
	 * @param id
	 * @return SheetMusic
	 */
	@GET
	@Path("/getSheetmusicBySheetID/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SheetMusic getSheetmusicBySheetID(@PathParam("id") Integer id) {
		SheetMusicServiceImpl smService;
		SheetMusic sm = new SheetMusic();
		try {
			smService = new SheetMusicServiceImpl();
			sm = smService.getSheetmusicById(id);

			log.info("Get Sheetmusic by sheet music ID");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return sm;
	}
	
	@GET
	@Path("/getSheetMusicByInstrument/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusicByInstrumentID(@PathParam("id") Integer id) {
		InstrumentSheetMusicServiceImpl ismService;
		List<SheetMusic> is = new ArrayList<SheetMusic>();
		try {
			ismService = new InstrumentSheetMusicServiceImpl();
//			is = ismService.getInstrumentSheetmusicByInstrumentId(id);
			is = ismService.getSheetMusicByInstrumentId(id);

			log.info("Get InstrumentSheetmusic by Instrument ID");
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return is;
	}

	/**
	 * Post comment to Sheetmusic
	 * @param id
	 * @param comment
	 * @param request
	 * @return List<SheetMusic>
	 */
	@PUT
	@Path("/postComment")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetmusicComment> postCommentOnSheetMusic(@FormDataParam("id") Integer id,
			@FormDataParam("comment") String comment, @Context HttpServletRequest request) {
		SheetMusicCommentServiceImpl smcService;
		List<SheetmusicComment> commentList = new ArrayList<SheetmusicComment>();

		try {
			smcService = new SheetMusicCommentServiceImpl();
			SheetmusicComment smcomment = new SheetmusicComment();

			// Logged in user
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");
			System.out.println("Logged in user: " + user);
			if (user != null && id != null) {
				smcomment.setUser(user);

				SheetMusic sm = new SheetMusic();
				sm.setSheetMusicId(id);
				smcomment.setSheetMusic(sm);
				smcomment.setComment(comment);
				smcomment.setPostedDate(new Date());

				smcService.insertSheetmusicComment(smcomment);
				log.info("Post SheetmusicComment saved");

				commentList = smcService.getSheetmusicCommentBySheetMusicId(id);
				log.info("Get SheetmusicComment by SheetMusicID=" + id);

			}
		} catch (ServiceException e) {
			log.error("Error in post SheetmusicComment", e);
		}
		return commentList;
	}

	/**
	 * Get comments on a SheetMusic
	 * @param id
	 * @return List<SheetMusic>
	 */
	@GET
	@Path("/getComments/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetmusicComment> getCommentListBySMID(@PathParam("id") Integer id) {
		SheetMusicCommentServiceImpl smcService;
		List<SheetmusicComment> commentList = new ArrayList<SheetmusicComment>();

		try {
			smcService = new SheetMusicCommentServiceImpl();
			commentList = smcService.getSheetmusicCommentBySheetMusicId(id);

			log.info("Get SheetmusicComment by SheetMusicID");

		} catch (ServiceException e) {
			log.error("Error in getting SheetmusicComment by SheetMusicID", e);
		}
		return commentList;
	}

	/**
	 * Get sheet music by pattern, search in sheet music name
	 * @param pattern 
	 * @return List<SheetMusic>
	 */
	@GET
	@Path("/getSheetMusicByPattern/{pattern}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusicByPattern(@PathParam("pattern") String pattern) {
		SheetMusicServiceImpl service;
		List<SheetMusic> smList = new ArrayList<SheetMusic>();

		try {
			service = new SheetMusicServiceImpl();
			smList = service.getSheetmusicByFilter(pattern);

			log.info("Get sheet music by pattern");
			
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return smList;
	}
	

	
	
	
	
	/////////////////////////////////////////////////////
	//// Get ALL
	/////////////////////////////////////////////////////

	/**
	 * Get all sheet music
	 * 
	 * @return List<Sheetmusic>
	 */
	@POST
	@Path("/getAllSheetMusic")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusic() {
		SheetMusicServiceImpl service;
		List<SheetMusic> smList = new ArrayList<SheetMusic>();

		try {
			service = new SheetMusicServiceImpl();
			smList = service.getAllSheetmusic();

			log.info("Get all sheet music");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return smList;
	}

	/**
	 * get Instrument Sheetmusic
	 * 
	 * @return List<InstrumentSheetmusic>
	 */
	@POST
	@Path("/getAllInstrumentSheetmusic")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<InstrumentSheetmusic> getInstrumentSheetmusic() {
		InstrumentSheetMusicServiceImpl ismService;
		List<InstrumentSheetmusic> is = new ArrayList<InstrumentSheetmusic>();
		try {

			ismService = new InstrumentSheetMusicServiceImpl();
			is = ismService.getAllInstrumentSheetmusic();

			log.info("Get all instrument sheet music");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return is;
	}

	/**
	 * Get all song genre
	 * 
	 * @return List<Sheetmusic>
	 */
	@GET
	@Path("/getAllGenre")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SongGenre> getSongGenre() {
		SongGenreServiceImpl service;
		List<SongGenre> sgList = new ArrayList<SongGenre>();

		try {
			service = new SongGenreServiceImpl();
			sgList = service.getAllGenre();

			log.info("Get all sheet music");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return sgList;
	}

	/**
	 * Get all Instrument
	 * 
	 * @return List<Sheetmusic>
	 */
	@GET
	@Path("/getAllInstrument")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Instrument> getInstruments() {
		InstrumentService service;
		List<Instrument> list = new ArrayList<Instrument>();

		try {
			service = new InstrumentServiceImpl();
			list = service.getAllInstrument();

			log.info("Get all Instrument");

			// for (Iterator<?> iterator = smList.iterator();
			// iterator.hasNext();) {
			// Sheetmusic a = (Sheetmusic) iterator.next();
			// System.out.println(a.getName());
			// System.out.println(a.getFilePdf());
			// }
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return list;
	}
}
