package bean;

import java.io.Serializable;

public class Resposta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Teste			teste;
	private Palavra			palavra;
	private String			resposta;
	private Boolean			erro;
	private Boolean			repeticao;
	private Boolean			erroRepeticao;
	private Integer			posicao;
	private Float			tempoReacao;
	
	public Boolean getErro() {
		return erro;
	}
	public void setErro(Boolean erro) {
		this.erro = erro;
	}
	public Teste getTeste() {
		return teste;
	}
	public void setTeste(Teste teste) {
		this.teste = teste;
	}
	public Palavra getPalavra() {
		return palavra;
	}
	public void setPalavra(Palavra palavra) {
		this.palavra = palavra;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public Boolean getErroRepeticao() {
		return erroRepeticao;
	}
	public void setErroRepeticao(Boolean erroRepeticao) {
		this.erroRepeticao = erroRepeticao;
	}
	public Integer getPosicao() {
		return posicao;
	}
	public Boolean getRepeticao() {
		return repeticao;
	}
	public void setRepeticao(Boolean repeticao) {
		this.repeticao = repeticao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	public Float getTempoReacao() {
		return tempoReacao;
	}
	public void setTempoReacao(Float tempoReacao) {
		this.tempoReacao = tempoReacao;
	}
}
