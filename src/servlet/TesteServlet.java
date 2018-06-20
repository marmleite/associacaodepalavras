package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Historia;
import bean.Pessoa;
import bean.Teste;
import controller.TesteController;

/**
 * Servlet implementation class TesteServlet
 */
@WebServlet("/testes")
public class TesteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TesteController 	tc;
	private Teste 				teste;
	private Historia			historiaSugerida;
	private HttpSession			sessao;
	private String				resultado;
	
	public void init() {
		tc 	= new TesteController();
		historiaSugerida = new Historia();
		teste = new Teste();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		sessao = request.getSession();
		historiaSugerida.setId(Integer.parseInt(request.getParameter("idHistoria")));
		teste.setHistoriaSugerida(historiaSugerida);
		teste.setPessoa((Pessoa) sessao.getAttribute("pessoa"));
		tc.criar(teste);
		sessao.setAttribute("teste",teste);
		//response.sendRedirect("teste.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		teste = tc.buscar(Integer.parseInt(request.getParameter("idTeste")));
		teste.setCompleto(true);
		tc.atualizar(teste);
		resultado = tc.corrigir(teste);
		PrintWriter out = response.getWriter();
		out.print(resultado);
		out.flush();
			
	}
	
}
