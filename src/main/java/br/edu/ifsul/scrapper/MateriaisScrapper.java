package br.edu.ifsul.scrapper;

import br.edu.ifsul.modelo.Arquivo;
import br.edu.ifsul.modelo.MaterialDisciplina;
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

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Object scrap(String token) throws Exception {

        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_MATERIAL_DE_AULA)
                .method(Connection.Method.POST)
                .cookie(QAcademico.getSessionCookieName(), token)
                .data("ANO_PERIODO", "2019_1")
                .execute();

        Document document = response.parse();

        Elements tabelaMateriais = document.select("body > table > tbody > tr:eq(1) > td > table > tbody > tr:eq(1) > td:eq(1) > table:eq(3)");
        Elements linhasTabela = tabelaMateriais.select("tbody > tr");

        List<MaterialDisciplina> materiaisDisciplina = new ArrayList<>();


        MaterialDisciplina diciplinaAtual = new MaterialDisciplina();
        for (Element linha: linhasTabela) {

            System.out.println(linha);

            if (isTituloDisciplina(linha)) {

                String nomeDisciplina = linha.select("td:eq(1)").text();
                diciplinaAtual = new MaterialDisciplina();
                diciplinaAtual.setDisciplina(nomeDisciplina);
                materiaisDisciplina.add(diciplinaAtual);

            } else if (isMaterialDisciplina(linha)) {

                Date data = sdf.parse(linha.select("td:eq(0)").text());
                Elements link = linha.select("td:eq(1) a");
                diciplinaAtual.addArquivo(new Arquivo(QAcademico.BASE_URL + link.attr("href"), link.text(), data));

            }

        }

        return materiaisDisciplina;

    }

    private boolean isTituloDisciplina(Element linha) {
        return linha.hasClass("rotulo") && linha.attr("bgcolor").equals("#E6E7E8");
    }

    private boolean isMaterialDisciplina(Element linha) {
        return linha.hasClass("conteudoTexto") && linha.attr("bgcolor").equals("#FFFFFF");
    }

}
