package br.edu.ifsul.resources;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.ifsul.encryption.RSAUtil;
import br.edu.ifsul.modelo.Arquivo;
import br.edu.ifsul.modelo.QAcademico;

@Named
@SessionScoped
public class SessionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private HttpClient httpClient = HttpClientBuilder
			.create()
			.setDefaultCookieStore(new BasicCookieStore())
			.setRedirectStrategy(new DefaultRedirectStrategy() {               
		        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
		            boolean isRedirect=false;
		            try {
		                isRedirect = super.isRedirected(request, response, context);
		            } catch (ProtocolException e) {
		                e.printStackTrace();
		            }
		            if (!isRedirect) {
		                int responseCode = response.getStatusLine().getStatusCode();
		                return responseCode == 301 || responseCode == 302;
		            }
		            return isRedirect;
		        }
			})
			.build();

	public void doLogin(String username, String password) throws Exception {
		
		BasicResponseHandler responseHandler = new BasicResponseHandler();

		HttpResponse response = httpClient.execute(new HttpGet(QAcademico.URL_OBTER_CHAVES));
		String resposta = responseHandler.handleResponse(response);
		String linha[] = resposta.split("\n");
		

		BigInteger expoenteEncriptacao = new BigInteger(limparInformacaoChave(linha[1]), 16);
		BigInteger modulo = new BigInteger(limparInformacaoChave(linha[3]), 16);
		
		RSAUtil rsa = new RSAUtil(expoenteEncriptacao, modulo);
		
		String nome = rsa.encriptarString(username);
		String senha = rsa.encriptarString(password);
		
		String tipoUsuario = rsa.encriptarString("1");
		
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	    postParameters.add(new BasicNameValuePair("LOGIN", nome));
	    postParameters.add(new BasicNameValuePair("SENHA", senha));
	    postParameters.add(new BasicNameValuePair("TIPO_USU", tipoUsuario));
	    
	    HttpPost post = new HttpPost("http://qacademico.ifsul.edu.br/qacademico/lib/validalogin.asp");
		post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		response = httpClient.execute(post);
		resposta = responseHandler.handleResponse(response);
		
	}
	
	private static String limparInformacaoChave(String string) {
		return string.replaceAll("[\"\\s,]", "");
	}

	public String getHorario() throws Exception {

		BasicResponseHandler responseHandler = new BasicResponseHandler();
		
		HttpResponse response = httpClient.execute(new HttpGet(QAcademico.URL_OBTER_HORARIO));
		String resposta = responseHandler.handleResponse(response);
		return resposta;
	}
	
	public List<Arquivo> getMateriais() throws Exception {
		
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		
		HttpResponse response = httpClient.execute(new HttpGet(QAcademico.URL_OBTER_MATERIAL_DE_AULA));
		String resposta = responseHandler.handleResponse(response);
		Document document = Jsoup.parse(resposta);

		Elements tabelaMateriais = document.select("body > table > tbody > tr:eq(1) > td > table > tbody > tr:eq(1) > td:eq(1) > table:eq(3)");
		Elements elements = tabelaMateriais.select("tbody > tr.conteudoTexto");
		
		System.out.println(elements);
		
		ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Element element : elements) {
			
			Date data = sdf.parse(element.select("td:eq(0)").text());
			Elements link = element.select("td:eq(1) a");
			
			arquivos.add(new Arquivo(QAcademico.BASE_URL + link.attr("href"), link.text(), data));
			
		}
		
		return arquivos;
		
	}
	
}
