package edu.ubb.bsc.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.RepositoryException;
import edu.ubb.bsc.repo.UserDAO;
import edu.ubb.bsc.repo.model.User;


@Path("/test")
public class Test {

	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public User findAll() {
		System.out.println("GET: findAll");
		User u = new User();
		u.setUserName("tomi");
		u.setUserPassword("ad");
		u.setUserMail("a@b.c");
		return u;
	}
	
	@GET
	@Path("/loggedIn")
	@Produces({ MediaType.APPLICATION_XML })
	public User loggedIn(@Context HttpServletRequest request) {

	
		DAOFactory df = DAOFactory.getInstance();
		UserDAO ud = df.getUserDAO();
		User user = ud.getUserById(13);
		System.out.println(user);
		
		return user;
	}
	


	

	
}
