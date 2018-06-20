package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Palavra;
import bean.Resposta;
import bean.Teste;
import controller.RespostaController;
import util.FormatHttp;

/**
 * Servlet implementation class RespostaServlet
 */
@WebServlet("/respostas")
public class RespostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RespostaController 	rc;
	private Resposta 			resposta;
	private List<Resposta>		respostas;
	private Palavra				palavra;
	private Teste				teste;
	private Integer				idResposta;

	public void init() {
		rc = new RespostaController();
		palavra = new Palavra();
		teste = new Teste();
		resposta = new Resposta();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		idResposta = FormatHttp.getId(request);
		if (idResposta != null) {
			resposta = rc.buscar(idResposta);
			FormatHttp.asJson(response, resposta);
		} else{
			respostas = rc.listar();
			FormatHttp.asJson(response, respostas);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		palavra.setId(Integer.parseInt(request.getParameter("idPalavra")));
		teste.setId(Integer.parseInt(request.getParameter("idTeste")));
		resposta.setResposta(request.getParameter("resposta"));
		resposta.setPosicao(Integer.parseInt(request.getParameter("posicao")));
		resposta.setPalavra(palavra);
		resposta.setTeste(teste);
		resposta.setTempoReacao(Float.parseFloat(request.getParameter("tempoReacao")));
		rc.salvar(resposta);
	}	

}
