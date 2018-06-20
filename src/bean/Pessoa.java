package bean;

import java.io.Serializable;
import java.util.Date;

public class Pessoa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date					dataNacimento;
	private Integer 				id;
	private Sexo 					sexo;
	private Escolaridade 			escolaridade;
	private PerfilSocioeconomico	perfilSocieconomico;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataNacimento() {
		return dataNacimento;
	}
	public void setDataNacimento(Date dataNacimento) {
		this.dataNacimento = dataNacimento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}
	public PerfilSocioeconomico getPerfilSocieconomico() {
		return perfilSocieconomico;
	}
	public void setPerfilSocieconomico(PerfilSocioeconomico perfilSocieconomico) {
		this.perfilSocieconomico = perfilSocieconomico;
	}
}