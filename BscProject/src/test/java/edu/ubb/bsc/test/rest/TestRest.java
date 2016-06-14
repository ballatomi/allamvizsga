package edu.ubb.bsc.test.rest;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.ubb.bsc.repo.model.User;

public class TestRest {

	
	public static void main(String[] args) {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/BscProject/rest/test/loggedIn");
		ClientResponse response =
		 wr.type(MediaType.APPLICATION_XML).
		 get(ClientResponse.class);
		
		User u = wr.accept(MediaType.APPLICATION_JSON)
				.get(User.class);
		
		System.out.println(u);
	}
}
