package br.edu.ifsul.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {
	
	@Inject private SessionBean sessionBean;
	
	@GET
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		try {
			sessionBean.doLogin(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
}
