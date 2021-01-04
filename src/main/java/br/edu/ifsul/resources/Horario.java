package br.edu.ifsul.resources;

import br.edu.ifsul.scrapper.HorarioScrapper;

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

	@Inject private HorarioScrapper horarioScrapper;

	@GET
	public Response get(@QueryParam("token") String token, @QueryParam("ano") String ano, @QueryParam("periodo") String periodo) {
		try {
			Object o = horarioScrapper.scrap(token, ano, periodo);
			return Response.ok(o).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
