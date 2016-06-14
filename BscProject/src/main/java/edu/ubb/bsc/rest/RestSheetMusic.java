package edu.ubb.bsc.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import edu.ubb.bsc.service.repo.SongGenreService;
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
	 * 
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

	/**
	 * Get SheetMusic by instrument ID
	 * @param id
	 * @return SheetMusic
	 */
	@GET
	@Path("/getSheetMusicByInstrument/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusicByInstrumentID(@PathParam("id") Integer id) {
		InstrumentSheetMusicServiceImpl ismService;
		List<SheetMusic> is = new ArrayList<SheetMusic>();
		try {
			ismService = new InstrumentSheetMusicServiceImpl();
			// is = ismService.getInstrumentSheetmusicByInstrumentId(id);
			is = ismService.getSheetMusicByInstrumentId(id);

			log.info("Get Sheetmusic by Instrument ID");
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return is;
	}
	
	/**
	 * Get SheetMusic by music genre ID
	 * @param id
	 * @return SheetMusic
	 */
	@GET
	@Path("/getSheetMusicByGenre/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusicByGenreID(@PathParam("id") Integer id) {
		InstrumentSheetMusicServiceImpl ismService;
		List<SheetMusic> is = new ArrayList<SheetMusic>();
		try {
			ismService = new InstrumentSheetMusicServiceImpl();
			// is = ismService.getInstrumentSheetmusicByInstrumentId(id);
			is = ismService.getSheetMusicByGenreId(id);

			log.info("Get Sheetmusic by Genre ID");
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return is;
	}	
	

	/**
	 * Post comment to Sheetmusic
	 * 
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
	 * 
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
	 * 
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

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return list;
	}

	/////////////////////////////////////////////////////
	//// Save elements
	/////////////////////////////////////////////////////

	/**
	 * Add new instrument
	 * 
	 * @param instrument
	 * @return
	 */
	@PUT
	@Path("/addInstrument/{instr}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addInstrument(@PathParam("instr") String instrument) {
		InstrumentService service;

		JSONObject jo = new JSONObject();
		try {
			service = new InstrumentServiceImpl();

			Instrument instr = new Instrument();
			instr.setName(instrument);
			String resp = service.insertInstrument(instr);

			try {
				jo.put("instrumentID", resp);
				jo.put("response", "Instrument was added succesfull");

			} catch (JSONException e) {
			}

			log.info("Get all Instrument");

		} catch (ServiceException e) {
			log.error("Error in adding Instrument", e);
		}
		return jo.toString();
	}

	/**
	 * Delete instrument 
	 * @param sid
	 * @return
	 */
	@DELETE
	@Path("/deleteInstrument/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String deleteInstrument(@PathParam("id") Integer sid) {
		InstrumentService service;
		JSONObject jo = new JSONObject();

		try {
			service = new InstrumentServiceImpl();

			Instrument instr = new Instrument();
			instr.setInstrumentId(sid);

			System.out.println("delete "+ sid);
			service.deleteInstrument(instr);
			try {
				jo.put("response", "Delete was succesfull");
			} catch (JSONException e) {
			}
		} catch (ServiceException e) {
			log.error("Error in deleting Instrument", e);
		}

		return jo.toString();
	}
	
	/**
	 * Add genre
	 * @param genre
	 * @return
	 */
	@PUT
	@Path("/addGenre/{g}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String addGenre(@PathParam("g") String genre) {
		SongGenreService service;
		
		JSONObject jo = new JSONObject();
		try {
			service = new SongGenreServiceImpl();

			SongGenre sg = new SongGenre();
			sg.setSongGenreName(genre);
			String resp = service.insertGenre(sg);

			try {
				jo.put("genreID", resp);
				jo.put("response", "Genre was added succesfull");

			} catch (JSONException e) {
			}

			log.info("Get all Instrument");

		} catch (ServiceException e) {
			log.error("Error in adding Instrument", e);
		}
		return jo.toString();
	}
	
	/**
	 * Delete genre
	 * @param sid
	 * @return
	 */
	@DELETE
	@Path("/deleteGenre/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String deleteGenre(@PathParam("id") Integer sid) {
		SongGenreService service;
		JSONObject jo = new JSONObject();

		try {
			service = new SongGenreServiceImpl();
			SongGenre sg = new SongGenre();
			sg.setSongGenreId(sid);
			
			System.out.println("delete "+ sid);
			service.deleteGenre(sg);
			try {
				jo.put("response", "Delete was succesfull");
			} catch (JSONException e) {
			}
		} catch (ServiceException e) {
			log.error("Error in deleting Genre", e);
		}
		return jo.toString();
	}

}
