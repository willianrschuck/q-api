package br.edu.ifsul.scrapper;

import br.edu.ifsul.encryption.RSAUtil;
import br.edu.ifsul.modelo.LoginParameters;
import br.edu.ifsul.modelo.QAcademico;
import br.edu.ifsul.encryption.StringEncryptor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.math.BigInteger;

public class LoginScrapper {

    private String sessionCookieName;
    private String sessionToken;

    public String doLogin(String username, String password) throws Exception {
        inicializeSession();

        StringEncryptor rsa = createEncryptor();
        LoginParameters loginParameters = new LoginParameters(rsa);
        loginParameters.insertUsername(username);
        loginParameters.insertPassword(password);

        authenticateSession(loginParameters);

        return sessionToken;
    }

    private void inicializeSession() throws Exception {
        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_CHAVES)
                .method(Connection.Method.HEAD)
                .execute();

        sessionCookieName = response.cookies().keySet().stream()
                .filter(s -> s.startsWith(QAcademico.SESSION_COOKIE_PREFIX))
                .findFirst()
                .orElseThrow(() -> new Exception("Cannot inicialize session with Q-Acadêmico"));

        sessionToken = response.cookie(sessionCookieName);
    }

    private StringEncryptor createEncryptor() throws Exception {
        String[] parameters = requestEncryptionParameters();
        BigInteger expoenteEncriptacao = new BigInteger(parameters[0], 16);
        BigInteger modulo = new BigInteger(parameters[1], 16);

        return new RSAUtil(expoenteEncriptacao, modulo);
    }

    private String[] requestEncryptionParameters() throws Exception {

        Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_CHAVES)
                .method(Connection.Method.GET)
                .cookie(sessionCookieName, sessionToken)
                .execute();

        String[] linhas = response.body().split("\n");
        String modulo = removerEspacosEAspas(linhas[1]);
        String expoente = removerEspacosEAspas(linhas[3]);

        return new String[] { modulo, expoente };
    }

    private static String removerEspacosEAspas(String string) {
        return string.replaceAll("[\"\\s,]", "");
    }

    private void authenticateSession(LoginParameters loginParameters) throws Exception {
        Connection.Response response = Jsoup.connect(QAcademico.URL_VALIDAR_LOGIN)
                .method(Connection.Method.POST)
                .data("LOGIN", loginParameters.getEncryptedUsername())
                .data("SENHA", loginParameters.getEncryptedPassword())
                .data("TIPO_USU", loginParameters.getEncryptedUserType())
                .cookie(sessionCookieName, sessionToken)
                .followRedirects(true)
                .execute();

        String pageTitle = response.parse().select("head > title").text();

        if (pageTitle.contains("Acesso negado")) {
            throw new Exception("Acesso negado");
        }
    }

}
