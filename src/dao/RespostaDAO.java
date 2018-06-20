package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Palavra;
import bean.Resposta;
import bean.Teste;
import util.ConnectionFactory;

public class RespostaDAO {
	
	private PreparedStatement 	stmt;
	private String				query;
	private	ResultSet			rs;
	
	public Boolean salvar(Resposta resposta) {
		query = "INSERT INTO resposta ".
				concat(" (idTeste, ").
				concat("idPalavra, ").
				concat("resposta, ").
				concat("erro, ").
				concat("repeticao, ").
				concat("erroRepeticao, ").
				concat("posicao, ").
				concat("tempoReacao) ").
				concat("VALUES ( ? , ? , ? , ?, ? , ? , ? , ? )");
		
		Boolean salvo;
		
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			stmt 	.setInt(1, resposta.getTeste().getId());
			stmt 	.setInt(2, resposta.getPalavra().getId());
			stmt 	.setString(3, resposta.getResposta());
			stmt 	.setBoolean(4, resposta.getErro());
			stmt 	.setBoolean(5, resposta.getRepeticao());
			stmt 	.setBoolean(6, resposta.getErroRepeticao());
			stmt 	.setInt(7, resposta.getPosicao());
			stmt 	.setFloat(8, resposta.getTempoReacao());

			salvo	= stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar dados da resposta",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
			return salvo;	
		}
	
	public List<Resposta> listar(Integer idTeste) {
		query = "SELECT * FROM resposta";
		
		List<Resposta> 	respostas 	= null;
		Resposta 		resposta 	= null;
		Teste 			teste 		= null;
		Palavra	 		palavra 	= null;

		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			
			rs	= stmt.executeQuery();
			respostas = new ArrayList<Resposta>();
			
			while(rs.next()){
				resposta 	= new Resposta();
				teste 		= new Teste();
				palavra 	= new Palavra();
				
				teste.setId(Integer.parseInt(rs.getString("idTeste")));
				
				resposta 	.setTeste(teste);
				resposta 	.setPalavra(palavra);
				resposta 	.setResposta(rs.getString("resposta"));
				resposta 	.setRepeticao(rs.getBoolean("repeticao"));
				resposta 	.setErro(rs.getBoolean("erro"));
				resposta 	.setErroRepeticao(rs.getBoolean("erroRepeticao"));
				resposta 	.setPosicao(rs.getInt("posicao"));
				resposta 	.setTempoReacao(rs.getFloat("tempoReacao"));

				respostas 	.add(resposta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar respostas",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
			return respostas;	
	}

	public Resposta buscar(Integer idTeste, Integer idPalavra, Integer posicao) {
		query = "SELECT * FROM palavra WHERE idTeste = ? AND idPalavra = ? and posicao = ? ";
		
		Resposta 		resposta = null;
		Teste 			teste 		= null;
		Palavra	 		palavra 	= null;
		
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			stmt 	.setInt(1,idTeste);
			stmt 	.setInt(2,idPalavra);
			stmt 	.setInt(3,posicao);
			rs	= 	stmt.executeQuery();
			
			if(rs.next()){
				resposta 	= new Resposta();
				teste 		= new Teste();
				palavra 	= new Palavra();

				resposta 	.setTeste(teste);
				resposta 	.setPalavra(palavra);
				resposta 	.setResposta(rs.getString("resposta"));
				resposta 	.setRepeticao(rs.getBoolean("repeticao"));
				resposta 	.setErro(rs.getBoolean("erro"));
				resposta 	.setErroRepeticao(rs.getBoolean("erroRepeticao"));
				resposta 	.setPosicao(rs.getInt("posicao"));
				resposta 	.setTempoReacao(rs.getFloat("tempoReacao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar resposta",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
		return resposta;
	}

	public List<Resposta> listar(Teste teste) {
		query = "SELECT * FROM resposta WHERE idTeste = ?";
		
		List<Resposta> 	respostas 	= null;
		Resposta 		resposta 	= null;
		Palavra	 		palavra 	= null;

		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			
			stmt 	.setInt(1,teste.getId());
			
			rs	= stmt.executeQuery();
			respostas = new ArrayList<Resposta>();
			
			while(rs.next()){
				resposta 	= new Resposta();
				palavra 	= new Palavra();

				resposta 	.setTeste(teste);
				resposta 	.setPalavra(palavra);
				resposta 	.setResposta(rs.getString("resposta"));
				resposta 	.setRepeticao(rs.getBoolean("repeticao"));
				resposta 	.setErro(rs.getBoolean("erro"));
				resposta 	.setErroRepeticao(rs.getBoolean("erroRepeticao"));
				resposta 	.setPosicao(rs.getInt("posicao"));
				resposta 	.setTempoReacao(rs.getFloat("tempoReacao"));

				respostas 	.add(resposta);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar respostas",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
			return respostas;
	}
}
