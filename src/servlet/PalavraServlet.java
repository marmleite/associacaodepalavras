package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import bean.Palavra;
import bean.Teste;
import controller.PalavraController;
import util.FormatHttp;

/**
 * Servlet implementation class PalavraServlet
 */
@WebServlet("/palavras")
public class PalavraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private PalavraController 	pc;
    private Teste				teste;
    private List<Palavra> 		palavras;
    private HttpSession 		sessao;
	
    public void init() {
		pc = new PalavraController();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			sessao = request.getSession();
			teste  = (Teste) sessao.getAttribute("teste");
			palavras = pc.listar();
			teste.setPalavras(palavras);
			FormatHttp.asJson(response, teste);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
