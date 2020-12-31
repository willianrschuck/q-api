package br.edu.ifsul.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("horario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Horario {

	@Inject private Scapper scapper;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response get(@QueryParam("token") String token) {
		try {
			return Response.ok(scapper.getHorario(token)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
}
