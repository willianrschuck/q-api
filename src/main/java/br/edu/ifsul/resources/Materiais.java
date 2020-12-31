package br.edu.ifsul.resources;

import br.edu.ifsul.modelo.Arquivo;
import br.edu.ifsul.modelo.QAcademico;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
@Path("materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Materiais {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMateriais(@QueryParam("token") String token) {
		try {
			return Response.ok(handle(token)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

	public List<Arquivo> handle(String token) throws Exception {

		Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_MATERIAL_DE_AULA)
				.method(Connection.Method.GET)
				.cookie(QAcademico.SESSION_COOKIE_NAME, token)
				.execute();

		System.out.println(response.body());

		Document document = response.parse();

		Elements tabelaMateriais = document.select("body > table > tbody > tr:eq(1) > td > table > tbody > tr:eq(1) > td:eq(1) > table:eq(3)");
		Elements elements = tabelaMateriais.select("tbody > tr.conteudoTexto");

		System.out.println(elements);

		List<Arquivo> arquivos = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Element element : elements) {

			Date data = sdf.parse(element.select("td:eq(0)").text());
			Elements link = element.select("td:eq(1) a");

			arquivos.add(new Arquivo(QAcademico.BASE_URL + link.attr("href"), link.text(), data));

		}

		return arquivos;

	}
	
}
