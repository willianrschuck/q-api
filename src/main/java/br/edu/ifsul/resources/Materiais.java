package br.edu.ifsul.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Materiais {

	@Inject private SessionBean session;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response defaultMethod() {
		try {
			return Response.ok(session.getMateriais()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}
	
	
}
