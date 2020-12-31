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
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {
	
	@Inject private Scapper sessionBean;
	
	@GET
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		try {
			String token = sessionBean.doLogin(username, password);
			Map<String, String> bodyMap = new HashMap<>();
			bodyMap.put("token", token);
			return Response.ok(bodyMap).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
}
