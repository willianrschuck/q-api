package br.edu.ifsul.resources;

import br.edu.ifsul.scrapper.MateriaisScrapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Materiais {

	@Inject private MateriaisScrapper materiaisScrapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMateriais(@QueryParam("token") String token) {
		try {
			return Response.ok(materiaisScrapper.scrap(token)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

}
