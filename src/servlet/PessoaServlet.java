package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.Escolaridade;
import bean.PerfilSocioeconomico;
import bean.Pessoa;
import bean.Sexo;
import controller.PessoaController;

/**
 * Servlet implementation class PessoaServlet
 */
@WebServlet("/pessoas")
public class PessoaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Gson 					gson;
	GsonBuilder 			gsonBuilder;
	Pessoa 					pessoa;
	Sexo					sexo;
	PerfilSocioeconomico	socioeconomico;
	Escolaridade			escolaridade;
	Date					dataNascimento;
	HttpSession				sessao;
	String 					rStrg;
	PessoaController 		pc;

	public void init() {
		pc = new PessoaController();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sessao = request.getSession(true);
		SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
		pessoa = new Pessoa();
		sexo = new Sexo();
		escolaridade = new Escolaridade();
		socioeconomico = new PerfilSocioeconomico();
		
		sexo.setId(Integer.parseInt(request.getParameter("idSexo")));
		escolaridade.setId(Integer.parseInt(request.getParameter("idEscolaridade")));
		socioeconomico.setId(Integer.parseInt(request.getParameter("idSocioeconomico")));
		try {
			dataNascimento = formatoData.parse(request.getParameter("dataNascimento"));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao converter data de nascimento.");
		}
		
		pessoa.setSexo(sexo);
		pessoa.setEscolaridade(escolaridade);
		pessoa.setPerfilSocieconomico(socioeconomico);
		pessoa.setDataNacimento(dataNascimento);
		
		pc.salvar(pessoa);
		sessao.setAttribute("pessoa",pessoa);
		response.sendRedirect("orientacoes.html");
	}

}
