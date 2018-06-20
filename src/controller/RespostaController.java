package controller;

import java.util.List;

import bean.Resposta;
import bean.Teste;
import dao.RespostaDAO;

public class RespostaController {
	
	RespostaDAO respostaDao;
	Resposta 	resposta;
	List<Resposta> respostas;
	
	public RespostaController(){
		respostaDao = new RespostaDAO();
	}
	
	public void salvar(Resposta resposta) {
		resposta.setErro(false);
		resposta.setRepeticao(false);
		resposta.setErroRepeticao(false);
		
		if(resposta.getPosicao() > 100)
			verificaErroRepeticao(resposta);
		
		if(resposta.getResposta().split(" ").length>1)
			resposta.setErro(true);
		
		if(resposta.getTempoReacao()<0)
			resposta.setTempoReacao(resposta.getTempoReacao()*-1);
		
		resposta.setTempoReacao(resposta.getTempoReacao()/new Float(1000.00));
		respostaDao.salvar(resposta);
	}

	private void verificaErroRepeticao(Resposta resposta) {
		Resposta r = null;
		resposta.setRepeticao(true);
		r = buscar(resposta.getTeste().getId(),resposta.getPalavra().getId(),resposta.getPosicao()-100);
		
		if(!resposta.getResposta().equals(r.getResposta()))
			resposta.setErroRepeticao(true);
	}

	private Resposta buscar(Integer idTeste, Integer idPalavra, Integer posicao) {
		resposta = respostaDao.buscar(idTeste,idPalavra,posicao);
		return resposta;
	}

	public List<Resposta> listar() {
		return null;
	}

	public Resposta buscar(Integer idResposta) {
		
		return null;
	}

	public List<Resposta> listar(Teste teste) {
		respostas = respostaDao.listar(teste);
		return respostas;
	}
}
