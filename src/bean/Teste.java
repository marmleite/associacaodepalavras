package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Teste implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer 		id;
	private Date			dataAplicacao;
	private Float			mediaReacao;
	private Float			desvioPadrao;
	private Float			repeticoesErradas;
	private Boolean			completo;
	private Pessoa			pessoa;
	private Historia		historiaEscolhida;
	private Historia		historiaSugerida;
	private List<Palavra>	palavras;
	private List<Resposta> 	respostas;
	
	public List<Palavra> getPalavras() {
		return palavras;
	}
	public void setPalavras(List<Palavra> palavras) {
		this.palavras = palavras;
	}
	public List<Resposta> getRespostas() {
		return respostas;
	}
	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataAplicacao() {
		return dataAplicacao;
	}
	public void setDataAplicacao(Date dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
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
	public Float getRepeticoesErradas() {
		return repeticoesErradas;
	}
	public void setRepeticoesErradas(Float repeticoesErradas) {
		this.repeticoesErradas = repeticoesErradas;
	}
	public Boolean getCompleto() {
		return completo;
	}
	public void setCompleto(Boolean completo) {
		this.completo = completo;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Historia getHistoriaEscolhida() {
		return historiaEscolhida;
	}
	public void setHistoriaEscolhida(Historia historiaEscolhida) {
		this.historiaEscolhida = historiaEscolhida;
	}
	public Historia getHistoriaSugerida() {
		return historiaSugerida;
	}
	public void setHistoriaSugerida(Historia historiaSugerida) {
		this.historiaSugerida = historiaSugerida;
	}	
}
