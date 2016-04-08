package edu.ubb.bsc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	
}
