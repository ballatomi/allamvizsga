package edu.ubb.bsc.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import antlr.ParserSharedInputState;
import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SongGenre;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.InstrumentService;
import edu.ubb.bsc.service.repo.InstrumentServiceImpl;
import edu.ubb.bsc.service.repo.InstrumentSheetMusicImpl;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.SongGenreServiceImpl;

@Path("sheet")
public class SheetMusicRest {

	private final static Logger log = LoggerFactory.getLogger(SheetMusicRest.class);

	/**
	 * /** Upload sheet music
	 * 
	 * @param uploadedInputStreamPdf
	 * @param uploadedInputStreamSound
	 * @param name
	 * @param length
	 * @param license
	 * @param genre_id
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/upload")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public String uploadSheetMusic(@FormDataParam("filepdf") InputStream uploadedInputStreamPdf,
			@FormDataParam("filesound") InputStream uploadedInputStreamSound, @FormDataParam("name") String name,
			@FormDataParam("length") Integer length, @FormDataParam("license") String license,
			@FormDataParam("genre_id") Integer genre_id, @FormDataParam("instrumentList") List<FormDataBodyPart> v1,
			@Context HttpServletRequest request) throws JSONException {

		SheetMusicServiceImpl service;
		JSONObject jo = new JSONObject();

		try {
			SheetMusic sm = new SheetMusic();
			sm.setName(name);
			sm.setLength(length);
			sm.setLicense(license);
			sm.setUploadDate(new Date());
			sm.setViewsNum(0);

			byte[] bytesPdf = IOUtils.toByteArray(uploadedInputStreamPdf);
			sm.setFilePdf(bytesPdf);
			if (bytesPdf.length < 10) {
				throw new ServiceException("PDF File not valide!");
			}

			SongGenre sg = new SongGenre();
			sg.setSongGenreId(genre_id);
			sm.setSongGenre(sg);

			// Logged in user
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("logged-user");
			System.out.println("Logged in user: " + user);
			sm.setUser(user);

			byte[] bytesSound = IOUtils.toByteArray(uploadedInputStreamSound);
			sm.setFileSound(bytesSound);
			if (bytesSound.length < 10) {
				throw new ServiceException("Sound File not valide!");
			}
			System.err.println(sm);

			service = new SheetMusicServiceImpl();
			service.insertSheetmusic(sm);

			// Insert InstrumentSheetmusic n-m relation
			for (FormDataBodyPart instrId : v1) {
				String v = instrId.getValueAs(String.class);
				System.out.println("Form: " + v);
				String[] id = v.split(",");
				for (String string : id) {
					Instrument i = new Instrument();
					InstrumentSheetMusicImpl ishmImpl = new InstrumentSheetMusicImpl();
					i.setInstrumentId(Integer.parseInt(string));
					InstrumentSheetmusic ism = new InstrumentSheetmusic();

					ism.setInstrument(i);
					ism.setSheetMusic(sm);

					ishmImpl.insertInstrumentSheetmusic(ism);
				}
			}

			log.info("Sheetmusic uploaded");

		} catch (ServiceException e) {
			jo.put("message", "Error in saving sheet music! " + e);
			jo.put("display_type", "initial");
			jo.put("alert_type", "alert alert-danger");
			log.error("Error in saving sheet music!", e);
			return jo.toString();
		} catch (IOException e) {
			jo.put("message", "Error, file failed!");
			jo.put("display_type", "initial");
			jo.put("alert_type", "alert alert-danger");
			log.error("Error, file failed!", e);
			return jo.toString();
		}

		jo.put("message", "Upload was successfull!");
		jo.put("display_type", "initial");
		jo.put("alert_type", "alert alert-success");
		return jo.toString();
	}

	/**
	 * Get all sheet music
	 * 
	 * @return List<Sheetmusic>
	 */
	@POST
	@Path("/getAll")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SheetMusic> getSheetMusic() {
		SheetMusicServiceImpl service;
		List<SheetMusic> smList = new ArrayList<SheetMusic>();
		SheetMusic s = new SheetMusic();
		
		try {
			service = new SheetMusicServiceImpl();
			smList = service.getAllSheetmusic();
			
			log.info("Get all sheet music");

			// for (Iterator<?> iterator = smList.iterator();
			// iterator.hasNext();) {
			// SheetMusic a = (SheetMusic) iterator.next();
			// System.out.println(a.getName());
			// System.out.println(a.getFilePdf());
			// }
		} catch (ServiceException e) {
			log.error("Error in getting sheetMusic", e);
		}
		return smList;
	}
	
	/**
	 * get Instrument Sheetmusic
	 * @return
	 */
	@POST
	@Path("/getAllInstrumentSheetmusic")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<InstrumentSheetmusic> getInstrumentSheetmusic() {
		InstrumentSheetMusicImpl ismService;
		List<InstrumentSheetmusic> is = new ArrayList<InstrumentSheetmusic>();
		try {

			ismService = new InstrumentSheetMusicImpl();
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
	@POST
	@Path("/getAllGenre")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SongGenre> getSongGenre() {
		SongGenreServiceImpl service;
		List<SongGenre> sgList = new ArrayList<SongGenre>();

		try {
			service = new SongGenreServiceImpl();
			sgList = service.getAllGenre();

			log.info("Get all sheet music");

			// for (Iterator<?> iterator = smList.iterator();
			// iterator.hasNext();) {
			// Sheetmusic a = (Sheetmusic) iterator.next();
			// System.out.println(a.getName());
			// System.out.println(a.getFilePdf());
			// }
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
	@POST
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
