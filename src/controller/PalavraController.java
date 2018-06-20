package controller;

import java.util.List;

import bean.Palavra;
import dao.PalavraDAO;

public class PalavraController {
	
	PalavraDAO 		palavraDao;
	Palavra			palavra;
	List<Palavra>	palavras;
	
	public PalavraController(){
		palavraDao = new PalavraDAO();
	}

	public Palavra buscar(Integer idPalavra) {
		palavra = palavraDao.buscar(idPalavra);
		return palavra;
	}
	
	public List<Palavra> listar(){
		palavras = palavraDao.listar();
		return palavras;
	}
	
	public void atualizar(Palavra palavra){
		palavraDao.atualizar(palavra);
	}

}
