package br.edu.ifsul.modelo;

public class QAcademico {

	public static final String SESSION_COOKIE_NAME = "ASPSESSIONIDQCDCTSBB";

	public static final String BASE_URL = "http://qacademico.ifsul.edu.br";
	public static final String APP_INDEX_URL = BASE_URL + "/qacademico/index.asp";
	
	public static final String URL_OBTER_CHAVES = BASE_URL + "/qacademico/lib/rsa/gerador_chaves_rsa.asp?form=frmLogin&action=%2Fqacademico%2Flib%2Fvalidalogin%2Easp";
	public static final String URL_VALIDAR_LOGIN = BASE_URL + "/qacademico/lib/validalogin.asp";

	public static final String URL_OBTER_HORARIO = APP_INDEX_URL + "?t=2010";
	public static final String URL_OBTER_MATERIAL_DE_AULA = APP_INDEX_URL + "?t=2061";
	
	private QAcademico() {}
	
}
