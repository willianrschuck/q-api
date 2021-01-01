package br.edu.ifsul.resources;

import br.edu.ifsul.encryption.RSAUtil;
import br.edu.ifsul.modelo.QAcademico;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.Serializable;
import java.math.BigInteger;

public class Scapper implements Serializable {
	private static final long serialVersionUID = 1L;

	public String doLogin(String username, String password) throws Exception {

		Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_CHAVES)
				.method(Connection.Method.GET)
				.execute();

		String aspToken = response.cookie(QAcademico.SESSION_COOKIE_NAME);
		String[] linha = response.body().split("\n");

		BigInteger expoenteEncriptacao = new BigInteger(limparInformacaoChave(linha[1]), 16);
		BigInteger modulo = new BigInteger(limparInformacaoChave(linha[3]), 16);
		
		RSAUtil rsa = new RSAUtil(expoenteEncriptacao, modulo);
		
		String encriptedName = rsa.encriptarString(username);
		String encriptedPassword = rsa.encriptarString(password);
		String encriptedUserType = rsa.encriptarString("1");

		response = Jsoup.connect(QAcademico.URL_VALIDAR_LOGIN)
				.method(Connection.Method.POST)
				.data("LOGIN", encriptedName)
				.data("SENHA", encriptedPassword)
				.data("TIPO_USU", encriptedUserType)
				.cookie(QAcademico.SESSION_COOKIE_NAME, aspToken)
				.followRedirects(true)
				.execute();

		String pageTitle = response.parse().select("head > title").text();
		if (pageTitle.contains("Acesso negado")) {
			throw new Exception("Acesso negado");
		}
		return aspToken;

	}
	
	private static String limparInformacaoChave(String string) {
		return string.replaceAll("[\"\\s,]", "");
	}

	public String getHorario(String token) throws Exception {

		Connection.Response response = Jsoup.connect(QAcademico.URL_OBTER_HORARIO)
				.method(Connection.Method.GET)
				.cookie(QAcademico.SESSION_COOKIE_NAME, token)
				.execute();

		return response.body();

	}
	
}