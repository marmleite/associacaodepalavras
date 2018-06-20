package bean;

import java.io.Serializable;

public class Palavra implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer 	id;
	private String		texto;
	private String		caminhoAudio;
	private Float		mediaReacao;
	private Float		desvioPadrao;
	private Float		percentualErros;
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCaminhoAudio() {
		return caminhoAudio;
	}
	public void setCaminhoAudio(String caminhoAudio) {
		this.caminhoAudio = caminhoAudio;
	}
	public Float getMediaReacao() {
		return mediaReacao;
	}
	public void setMediaReacao(Float mediaReacao) {
		this.mediaReacao = mediaReacao;
	}
	public Float getDesvioPadrao() {
		return desvioPadrao;
	}
	public void setDesvioPadrao(Float desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}
	public Float getPercentualErros() {
		return percentualErros;
	}
	public void setPercentualErros(Float percentualErros) {
		this.percentualErros = percentualErros;
	}
	
}
