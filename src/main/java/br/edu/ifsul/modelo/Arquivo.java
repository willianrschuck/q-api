package br.edu.ifsul.modelo;

import java.util.Date;

public class Arquivo {

	private String url;
	private String titulo;
	private Date dataPublicacao;

	
	public Arquivo() {}
	
	public Arquivo(String urlRelativa, String titulo, Date dataPublicacao) {
		this.url = urlRelativa;
		this.titulo = titulo;
		this.dataPublicacao = dataPublicacao;
	}

	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String urlRelativa) {
		this.url = urlRelativa;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Date getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
}
