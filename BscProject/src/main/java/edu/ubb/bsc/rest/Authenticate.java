package edu.ubb.bsc.rest;

import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.UserServiceImpl;

@Path("login")
public class Authenticate {

	private final static Logger log = LoggerFactory.getLogger(Authenticate.class);

	@POST
	@Path("/log")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String login(User user, @Context HttpServletRequest request) throws LoginException, JSONException {
		UserServiceImpl service;
		JSONObject jo = new JSONObject();
		try {
			service = new UserServiceImpl();
			user = service.loginAuthenticateUser(user.getUserName(), user.getUserPassword());
		} catch (ServiceException e) {
			jo.put("Message", "Login error!");
			jo.put("login", "false");
			return jo.toString();
		}

		if (user == null) {
			jo.put("Message", "Incorrect username or password!");
			jo.put("login", "false");
		} else {
			
			HttpSession session = request.getSession();
			session.setAttribute("logged-user", user);

			jo.put("Message", "Log in success!");
			jo.put("login", "true");
			log.info("User login was successfull!");
		}
		return jo.toString();
	}

	@POST
	@Path("/reg")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String registration(User user) throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			UserServiceImpl service;
			service = new UserServiceImpl();

			user.setUserRight(1);
			user.setUserLastLogin(new Date());
			service.insertUser(user);

			System.out.println(user);
			jo.put("regMessage", "Registration was successfull!");
			jo.put("color", "green");
			log.info("User Registration was successfull!");

		} catch (ServiceException e) {
			jo.put("regMessage", "Registration not was successfull!");
			jo.put("color", "red");
			
		}
		return jo.toString();
	}

	@GET
	@Path("/loggedIn")
	@Produces({ MediaType.APPLICATION_JSON })
	public User loggedIn(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = new User();
		user = (User) session.getAttribute("logged-user");
		return user;
	}

	@GET
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public String logout(@Context HttpServletRequest request) throws LoginException, JSONException, ServiceException {
		JSONObject jo = new JSONObject();
		HttpSession session = request.getSession();
		session.invalidate();

		jo.put("Message", "Logged out in success!");

		return jo.toString();
	}

}
