package edu.ubb.bsc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SongGenre;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.InstrumentService;
import edu.ubb.bsc.service.repo.InstrumentServiceImpl;
import edu.ubb.bsc.service.repo.InstrumentSheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.SongGenreServiceImpl;
import edu.ubb.bsc.service.repo.UserService;
import edu.ubb.bsc.service.repo.UserServiceImpl;

@Path("sheet/get")
public class GetAllElements {

	private final static Logger log = LoggerFactory.getLogger(RestSheetMusic.class);

	/////////////////////////////////////////////////////
	//// Get ALL
	/////////////////////////////////////////////////////

	/**
	 * Get all sheet music
	 * 
	 * @return List<Sheetmusic>
	 */
	@GET
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
	@GET
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
	
	/**
	 * Get all Users
	 * 
	 * @return List<User>
	 */
	@GET
	@Path("/getAllUsers")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUsers() {
		UserService service;
		List<User> list = new ArrayList<User>();

		try {
			service = new UserServiceImpl();
			list = service.getAllUsers();

			log.info("Get all Instrument");

		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return list;
	}
	
	
	
}
