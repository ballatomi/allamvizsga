package edu.ubb.bsc.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import edu.ubb.bsc.repo.model.Sheetmusic;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.SheetMusicServiceImpl;

@Path("sheet")
@Produces("application/json")
@Consumes("application/json")
public class SheetMusicRest {

	@POST
	@Path("/upload")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String uploadSheetMusic(Sheetmusic sm) throws JSONException {
		SheetMusicServiceImpl service;
		JSONObject jo = new JSONObject();
		
		try {
			System.out.println(sm.getName());
			System.out.println(sm.getLength());
			System.out.println(sm.getFilePdf().toString());
			
			sm.setUploadDate(new Date());
			service = new SheetMusicServiceImpl();
			service.insertSheetmusic(sm);
			
		} catch (ServiceException e) {
			jo.put("Message", "Error!");
			jo.put("display_type", "initial");
			return jo.toString();
		}
		
		jo.put("Message", "Upload was successfull!");
		return jo.toString();
	}

}
