package controller;

import java.util.List;

import bean.Pessoa;
import dao.PessoaDAO;

public class PessoaController {
	
	PessoaDAO 		pessoaDao;
	List<Pessoa>	pessoas;
	
	public PessoaController(){
		pessoaDao = new PessoaDAO();
	}
	
	public Boolean salvar(Pessoa pessoa) {
		if(pessoaDao.salvar(pessoa))
			return true;
		return false;
	}
	
	public List<Pessoa> listar(){
		pessoas = pessoaDao.listar();
		return pessoas;
	}
}
