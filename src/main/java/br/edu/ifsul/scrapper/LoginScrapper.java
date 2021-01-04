package br.edu.ifsul.scrapper;

import br.edu.ifsul.encryption.RSAUtil;
import br.edu.ifsul.modelo.QAcademico;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.math.BigInteger;

public class LoginScrapper {

    public String doLogin(String username, String password) throws Exception {

        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_CHAVES)
                .method(Connection.Method.GET)
                .execute();

        String aspTokenName = response.cookies().keySet().stream()
                .filter(s -> s.startsWith(QAcademico.SESSION_COOKIE_PREFIX))
                .findFirst()
                .orElse(null);

        QAcademico.setSessionCookieName(aspTokenName);

        String aspToken = response.cookie(aspTokenName);

        String[] linhas = response.body().split("\n");

        BigInteger expoenteEncriptacao = new BigInteger(removerEspacosEAspas(linhas[1]), 16);
        BigInteger modulo = new BigInteger(removerEspacosEAspas(linhas[3]), 16);

        RSAUtil rsa = new RSAUtil(expoenteEncriptacao, modulo);

        String encriptedName = rsa.encriptarString(username);
        String encriptedPassword = rsa.encriptarString(password);
        String encriptedUserType = rsa.encriptarString("1");

        response = Jsoup.connect(QAcademico.URL_VALIDAR_LOGIN)
                .method(Connection.Method.POST)
                .data("LOGIN", encriptedName)
                .data("SENHA", encriptedPassword)
                .data("TIPO_USU", encriptedUserType)
                .cookie(aspTokenName, aspToken)
                .followRedirects(true)
                .execute();

        String pageTitle = response.parse().select("head > title").text();
        if (pageTitle.contains("Acesso negado")) {
            throw new Exception("Acesso negado");
        }
        return aspToken;

    }

    private static String removerEspacosEAspas(String string) {
        return string.replaceAll("[\"\\s,]", "");
    }

}
