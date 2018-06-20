package controller;

import java.util.ArrayList;
import java.util.List;

import bean.DetectorMentiras;
import bean.Historia;
import bean.Resposta;
import bean.Teste;
import dao.TesteDAO;

public class TesteController {
	
	TesteDAO			testeDao;
	Teste 				teste;
	List<Teste> 		testes;
	RespostaController	rc;
	DetectorMentiras	dm;
	Integer 			historiaEscolhida;
	
	public TesteController() {
		testeDao = new TesteDAO();
	}
	
	public void criar(Teste teste) {
		teste.setCompleto(false);
		testeDao.salvar(teste);
	}
	
	public void atualizar(Teste teste){
		testeDao.atualizar(teste);
	}
	
	public Teste buscar(Integer idTeste) {
		teste = testeDao.buscar(idTeste);
		return teste;
	}
	
	public List<Teste> listar() {
		testes = testeDao.listar();
		return testes;
	}
	
	public static void main(String[] args) {
		TesteController tc = new TesteController();
		Teste 		teste;
		Historia 	historiaSugerida;
		teste 				= new Teste();
		historiaSugerida	= new Historia();
		historiaSugerida	.setId(3);
		teste				.setHistoriaSugerida(historiaSugerida);
		List<Resposta> respostas = new ArrayList<Resposta>();
		Resposta resposta;
		for (int i = 0; i < 100; i++) {
			
			resposta = new Resposta();
			if(i%3==0) resposta.setErro(true);
			else resposta.setErro(false);
			if(i%5==0) resposta.setErroRepeticao(true);
			else resposta.setErroRepeticao(false);
			resposta 	.setPosicao(i);
			resposta 	.setTempoReacao(new Float(i%3));
			respostas.add(resposta);
		}
		teste.setRespostas(respostas);
		System.out.println(tc.corrigir(teste));
		
	}
	
	public Teste fazTeste(){
		Teste 		teste;
		Historia 	historiaSugerida;
		teste 				= new Teste();
		historiaSugerida	= new Historia();
		historiaSugerida	.setId(3);
		teste				.setHistoriaSugerida(historiaSugerida);
		List<Resposta> respostas = new ArrayList<Resposta>();
		Resposta resposta;
		for (int i = 0; i < 100; i++) {
			
			resposta = new Resposta();
			if(i%3==0) resposta.setErro(true);
			else resposta.setErro(false);
			if(i%5==0) resposta.setErroRepeticao(true);
			else resposta.setErroRepeticao(false);
			resposta 	.setPosicao(i);
			resposta 	.setTempoReacao(new Float(i%3));
			respostas.add(resposta);
		}
		teste.setRespostas(respostas);
		return teste;
	}
	
	public String corrigir(Teste teste) {
		dm = new DetectorMentiras();
		rc = new RespostaController();
		teste.setRespostas(rc.listar(teste));
		try {
			dm.corrigirTreinar(teste);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao corrigir e treinar a rede");
		}
		
		if(teste.getHistoriaEscolhida().getId() != teste.getHistoriaSugerida().getId())
			return "resultadoBom.html";
		else
			switch (teste.getHistoriaSugerida().getId()) {
			case 1:
				return "espiao.html";
			case 2:
				return "ladrao.html";
			case 3:
				return "testemunha.html";
			default:
				return "";
			}
	}
		
}
