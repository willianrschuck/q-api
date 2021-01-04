package br.edu.ifsul.scrapper;

import br.edu.ifsul.modelo.Arquivo;
import br.edu.ifsul.modelo.QAcademico;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MateriaisScrapper {

    public Object scrap(String token) throws Exception {

        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_MATERIAL_DE_AULA)
                .method(Connection.Method.GET)
                .cookie(QAcademico.getSessionCookieName(), token)
                .execute();

        Document document = response.parse();

        Elements tabelaMateriais = document.select("body > table > tbody > tr:eq(1) > td > table > tbody > tr:eq(1) > td:eq(1) > table:eq(3)");
        Elements elements = tabelaMateriais.select("tbody > tr.conteudoTexto");

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
