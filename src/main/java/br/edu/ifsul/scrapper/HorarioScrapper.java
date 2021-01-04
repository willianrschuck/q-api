package br.edu.ifsul.scrapper;

import br.edu.ifsul.modelo.Horario;
import br.edu.ifsul.modelo.HorarioDisciplina;
import br.edu.ifsul.modelo.QAcademico;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public class HorarioScrapper {

    public Object scrap(String token, String ano, String periodo) throws Exception {

        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_HORARIO)
                .method(Connection.Method.GET)
                .cookie(QAcademico.getSessionCookieName(), token)
                .data("cmbanos", ano)
                .data("cmbperiodos", periodo)
                .execute();

        Document document = response.parse();

        Element tabelaHorario = document.selectFirst("html > body > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > table:nth-child(5)");

        Elements linahs = tabelaHorario.select("tr:not(:first-child)");

        Horario h = new Horario();

        for (Element e: linahs) {
            Elements colunas = e.select("td");
            Iterator<Element> iterator = colunas.iterator();


            String[] horario = iterator.next().select("div > font > strong").text().replace(" ", "").split("~");
            String horaInicio = horario[0];
            String horaFim = horario[1];

            while (iterator.hasNext()) {

                Element td = iterator.next();
                Element divInfoDisciplina = td.selectFirst("div > font > div");

                String nome = divInfoDisciplina.attr("title");
                String sigla = divInfoDisciplina.text();

                HorarioDisciplina hd = new HorarioDisciplina();
                hd.setInicio(horaInicio);
                hd.setFim(horaFim);
                hd.setNome(nome);
                hd.setSigla(sigla);

                h.addHorarioDisciplina(hd);

            }

        }

        return h;

    }

}
