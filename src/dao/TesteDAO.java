package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import bean.Historia;
import bean.Pessoa;
import bean.Teste;
import util.ConnectionFactory;

public class TesteDAO {
	
	private PreparedStatement 	stmt;
	private String				query;
	private	ResultSet			rs;
	
	public void salvar(Teste teste) {
		query = "INSERT INTO teste ".
				concat(" (dataAplicacao, ").
				concat("idHistoria, ").
				concat("idPessoa) ").
				concat("VALUES ( NOW() , ? , ? )");
	
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			stmt 	.setInt(1,teste.getHistoriaSugerida().getId());
			stmt 	.setInt(2,teste.getPessoa().getId());

			stmt.execute();
			rs		= stmt.getGeneratedKeys();
			
			if(rs.next())
				teste.setId(rs.getInt(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar teste",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
	}
	
	public void atualizar(Teste teste){
		query = "UPDATE teste SET ".
				concat(" mediaReacao = ?, ").
				concat(" desvioPadrao = ?, ").
				concat(" repeticoesErradas = ?, ").
				concat(" incompleto = ?, ").
				concat(" WHERE id = ?");
	
		
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);

			stmt 	.setFloat(1,teste.getMediaReacao());
			stmt 	.setFloat(2,teste.getDesvioPadrao());
			stmt 	.setFloat(3,teste.getRepeticoesErradas());
			stmt 	.setBoolean(4,teste.getCompleto());
	
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar teste",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
	}
	
	public Teste buscar(Integer idTeste) {
		query = "SELECT * FROM teste t ".
				concat("INNER JOIN historia hs ").
				concat("ON hs.id = t.idHistoriaSugerida ").
				concat("INNER JOIN pessoa p ").
				concat("ON p.id = t.idPessoa").
				concat("LEFT JOIN historia he ").
				concat("ON he.id = t.idHistoriaEscolhida ").
				concat(" WHERE t.id = ?");
		
		Teste 		teste				= null;
		Pessoa		pessoa				= null;
		Historia	historiaEscolhida	= null;
		Historia	historiaSugerida	= null;

		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			stmt 	.setInt(1,idTeste);

			rs = stmt.executeQuery();
			
			if(rs.next()){
				teste 				= new Teste();
				pessoa 				= new Pessoa();
				historiaEscolhida	= new Historia();
				historiaSugerida	= new Historia();


				pessoa 				.setDataNacimento(rs.getDate("p.dataNascimento"));
				pessoa				.setId(rs.getInt("p.id"));
				
				historiaEscolhida	.setId(rs.getInt("he.id"));
				historiaEscolhida	.setTexto(rs.getString("he.texto"));
				historiaEscolhida	.setTitulo(rs.getString("he.titulo"));
				
				historiaSugerida	.setId(rs.getInt("hs.id"));
				historiaSugerida	.setTexto(rs.getString("hs.texto"));
				historiaSugerida	.setTitulo(rs.getString("hs.titulo"));
				
				teste				.setPessoa(pessoa);
				teste				.setHistoriaEscolhida(historiaEscolhida);
				teste				.setHistoriaSugerida(historiaSugerida);
				teste				.setId(rs.getInt("t.id"));
				teste				.setCompleto(rs.getBoolean("t.incompleto"));
				teste				.setDataAplicacao(rs.getDate("t.dataAplicacao"));
				teste				.setDesvioPadrao(rs.getFloat("t.desvioPadrao"));
				teste				.setRepeticoesErradas(rs.getFloat("t.repeticoesErradas"));
				teste				.setMediaReacao(rs.getFloat("t.mediaReacao"));
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar teste",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
		return teste;
	}
	
	public List<Teste> listar() {
		query = "SELECT * FROM teste t ".
				concat("INNER JOIN historia hs ").
				concat("ON hs.id = t.idHistoriaSugerida ").
				concat("INNER JOIN historia he ").
				concat("ON he.id = t.idHistoriaEscolhida ").
				concat("INNER JOIN pessoa p ").
				concat("ON p.id = t.idPessoa");
		
		List<Teste> testes 				= null;
		Teste		teste				= null;
		Pessoa		pessoa				= null;
		Historia	historiaEscolhida	= null;
		Historia	historiaSugerida	= null;
		
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
			testes = new ArrayList<Teste>();
			
			while(rs.next()){
				teste 				= new Teste();
				pessoa 				= new Pessoa();
				historiaEscolhida	= new Historia();
				historiaSugerida	= new Historia();
				
				pessoa 				.setDataNacimento(rs.getDate("p.dataNascimento"));
				pessoa				.setId(rs.getInt("p.id"));
				
				historiaEscolhida	.setId(rs.getInt("he.id"));
				historiaEscolhida	.setTexto(rs.getString("he.texto"));
				historiaEscolhida	.setTitulo(rs.getString("he.titulo"));
				
				historiaSugerida	.setId(rs.getInt("hs.id"));
				historiaSugerida	.setTexto(rs.getString("hs.texto"));
				historiaSugerida	.setTitulo(rs.getString("hs.titulo"));
				
				teste				.setPessoa(pessoa);
				teste				.setHistoriaEscolhida(historiaEscolhida);
				teste				.setHistoriaSugerida(historiaSugerida);
				teste				.setId(rs.getInt("t.id"));
				teste				.setCompleto(rs.getBoolean("t.completo"));
				teste				.setDataAplicacao(rs.getDate("t.dataAplicacao"));
				teste				.setDesvioPadrao(rs.getFloat("t.desvioPadrao"));
				teste				.setRepeticoesErradas(rs.getFloat("t.repeticoesErradas"));
				teste				.setMediaReacao(rs.getFloat("t.mediaReacao"));
				
				testes				.add(teste);
			
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar testes",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
		return testes;
	}
	
	public void atualizarTeste(int idTeste) {
		
		try (Connection conexao = ConnectionFactory.getConnection()){		
			
			//atualizando os dados estatísticos de todos os testes	

			String query2= "update dadosTeste set DesvioPadraoMedio =(Select AVG(desvioPadrao) from teste), TempoMedioReacao=(Select AVG(mediaReacao) from teste), MediaErrosRepeticao = (Select AVG(repeticoesErradas) from teste)";
			PreparedStatement st = conexao.prepareStatement(query2);
			st.executeQuery(query2);
			
			//atualizando os dados estatísticos de cada teste	
			
			String query1 = "update teste set repeticoesErradas = (select SUM(erroRepeticao) from resposta where idTeste =?), mediaReacao = (select AVG(tempoReacao) from resposta where idTeste =?), desvioPadrao = (select STDDEV(tempoReacao) from resposta where idTeste =?)   WHERE id=?";
			
			PreparedStatement stmt = conexao.prepareStatement(query1);
			stmt.setInt(1,idTeste);
			stmt.setInt(2,idTeste);
			stmt.setInt(3,idTeste);
			stmt.setInt(4,idTeste);
			stmt.executeUpdate();	
			
			//atualizando os dados estatísticos para cada palavra
			String query3= "select idPalavra, AVG(erroRepeticao), AVG(tempoReacao), STDDEV(tempoReacao) from resposta group by idPalavra";
			ResultSet rs = st.executeQuery(query3);
			
			while(rs.next()){
				query3 = "update palavra set MediaReacaoPalavra=? , PorcentagemRepeticoesErradas=? , DesvioPadraoPalavra=? WHERE idPalavra = ? ";
				 stmt = conexao.prepareStatement(query3);
				stmt.setDouble(1,rs.getDouble("AVG(tempoReacao)"));
				stmt.setDouble(2,rs.getDouble("AVG(erroRepeticao)"));
				stmt.setDouble(3,rs.getDouble("STDDEV(tempoReacao)"));
				stmt.setInt(4,rs.getInt("idPalavra"));	
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar dados",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexo com o banco",e1);
		}
		}	
}
