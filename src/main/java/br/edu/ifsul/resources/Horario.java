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
@Path("horario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Horario {

	@Inject private SessionBean sessionBean;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response defaultMethod() {
		try {
			return Response.ok(sessionBean.getHorario()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}
	
	
}
