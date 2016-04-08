package edu.ubb.bsc.service.security;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.ubb.bsc.repo.model.User;

public class SecurityUser implements SecurityContext {

	private final User user;
	private final HttpSession session;

	public SecurityUser(HttpSession session, User user) {
		this.session = session;
		this.user = user;
	}

	public Principal getUserPrincipal() {
		return new Principal() {

			public String getName() {
				return user.getUserName();
			}
		};
	}

	public boolean isUserInRole(String role) {
		if (null == session || !session.isNew()) {
			// Forbidden
			Response denied = Response.status(Response.Status.FORBIDDEN).entity("Permission Denied").build();
			throw new WebApplicationException(denied);
		}

		try {
			int right = user.getUserRight();
			int roleNum = Integer.parseInt(role);
			return right == roleNum;
		} catch (Exception e) {
		}
		return false;
	}

	public boolean isSecure() {
		return (null != session) ? session.isNew() : false;
	}

	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

}
