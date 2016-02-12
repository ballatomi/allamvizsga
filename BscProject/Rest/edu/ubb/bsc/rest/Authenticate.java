package edu.ubb.bsc.rest;

import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.spi.container.ContainerRequest;

import edu.ubb.bsc.repo.DAOFactory;
import edu.ubb.bsc.repo.UserDAO;
import edu.ubb.bsc.repo.model.User;
import edu.ubb.bsc.service.repo.ServiceException;
import edu.ubb.bsc.service.repo.UserServiceImpl;
import edu.ubb.bsc.service.security.SecurityUser;

@Path("login")
public class Authenticate {

	@POST
	@Path("/log")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String login(User user, @Context HttpServletRequest request)
			throws LoginException, JSONException, ServiceException {
		UserServiceImpl service = new UserServiceImpl();
		JSONObject jo = new JSONObject();

		user = service.loginAuthenticateUser(user.getUserName(), user.getUserPassword());

		if (user == null) {
			jo.put("Message", "Incorrect username or password!");
			jo.put("display_type", "initial");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("logged-user", user);

			System.out.println(user);
			jo.put("Message", "Log in success!");
			jo.put("display_type", "initial");
		}
		return jo.toString();
	}

	@POST
	@Path("/reg")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String registration(User user) throws JSONException, ServiceException {
		UserServiceImpl service = new UserServiceImpl();
		JSONObject jo = new JSONObject();

		user.setUserRight(1);
		user.setUserLastLogin(new Date());
		service.insertUser(user);

		System.out.println(user);
		jo.put("regMessage", "Registration was successfull!");
		jo.put("display_type", "initial");

		return jo.toString();
	}

	@POST
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public String logout(@Context HttpServletRequest request) throws LoginException, JSONException, ServiceException {
		UserServiceImpl service = new UserServiceImpl();
		JSONObject jo = new JSONObject();

		HttpSession session = request.getSession();
		session.invalidate();

		jo.put("Message", "Logged out in success!");
		jo.put("display_type", "initial");

		return jo.toString();
	}

}
