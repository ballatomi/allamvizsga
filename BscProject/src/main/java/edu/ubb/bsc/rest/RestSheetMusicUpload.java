package edu.ubb.bsc.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import edu.ubb.bsc.repo.model.Instrument;
import edu.ubb.bsc.repo.model.InstrumentSheetmusic;
import edu.ubb.bsc.repo.model.SheetMusic;
import edu.ubb.bsc.repo.model.SongGenre;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.InstrumentSheetMusicServiceImpl;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicServiceImpl;

@Path("sheetUpload")
public class RestSheetMusicUpload {
	private final static Logger log = LoggerFactory.getLogger(RestSheetMusic.class);

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
			try {
				for (FormDataBodyPart instrId : v1) {
					String v = instrId.getValueAs(String.class);
					System.out.println("Form: " + v);
					String[] id = v.split(",");
					for (String string : id) {
						Instrument i = new Instrument();
						InstrumentSheetMusicServiceImpl ishmImpl = new InstrumentSheetMusicServiceImpl();
						i.setInstrumentId(Integer.parseInt(string));
						InstrumentSheetmusic ism = new InstrumentSheetmusic();

						ism.setInstrument(i);
						ism.setSheetMusic(sm);

						ishmImpl.insertInstrumentSheetmusic(ism);
					}
				}
			} catch (NumberFormatException ex){}
			
			log.info("Sheetmusic uploaded");

		} catch (ServiceException e) {
			jo.put("message", "Error in saving sheet music! " + e);
			jo.put("alert_type", "alert alert-danger");
			log.error("Error in saving sheet music!", e);
			return jo.toString();
		} catch (IOException e) {
			jo.put("message", "Error, file failed!");
			jo.put("alert_type", "alert alert-danger");
			log.error("Error, file failed!", e);
			return jo.toString();
		} catch (Exception e) {
			jo.put("message", "Error in saving sheet music! " + e);
			jo.put("alert_type", "alert alert-danger");
			log.error("Error in saving sheet music!", e);
			return jo.toString();
		}

		jo.put("message", "Upload was successfull!");
		jo.put("alert_type", "alert alert-success");
		return jo.toString();
	}
}
