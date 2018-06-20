package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import bean.Escolaridade;
import bean.PerfilSocioeconomico;
import bean.Pessoa;
import bean.Sexo;
import util.ConnectionFactory;

public class PessoaDAO {
	private ResultSet			rs;
	private PreparedStatement 	stmt;
	private	String				query;
	
	public Boolean salvar(Pessoa pessoa) {
	
		query = "INSERT INTO pessoa ".
					concat(" (dataNascimento, ").
					concat("idEscolaridade, ").
					concat("idSocioeconomico, ").
					concat("idSexo) ").
					concat("VALUES ( ? , ? , ? , ? )");
		
		Boolean salvo;
		
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt 	= conexao.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			stmt	.setDate(1,new java.sql.Date(pessoa.getDataNacimento().getTime()));
			stmt	.setInt(2, pessoa.getEscolaridade().getId());
			stmt	.setInt(3, pessoa.getPerfilSocieconomico().getId());
			stmt	.setInt(4, pessoa.getSexo().getId());
			
			salvo	= stmt.execute();
			rs 		= stmt.getGeneratedKeys();
			
			if(rs.next())
				pessoa	.setId(rs.getInt(1));
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar dados da pessoa",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
		return salvo;				
	}
	
	public List<Pessoa> listar(){
		query = "SELECT * FROM pessoa p".
				concat(" INNER JOIN escolaridade e ").
				concat(" ON e.id = p.idEscolaridade ").
				concat(" INNER JOIN socioeconomico s ").
				concat(" ON s.id = p.idSocioeconomico ").
				concat(" INNER JOIN sexo x ").
				concat(" ON x.id = p.idSexo ");
	
		List<Pessoa> 			pessoas;
		Pessoa					pessoa;
		Escolaridade			escolaridade;
		PerfilSocioeconomico	socioeconomico;
		Sexo					sexo;
	
		try (Connection conexao = ConnectionFactory.getConnection()){
			
			stmt			= conexao.prepareStatement(query);
			rs				= stmt.executeQuery();
			pessoas			= new ArrayList<Pessoa>();
			
			while(rs.next()){
				pessoa 			= new Pessoa();
				escolaridade	= new Escolaridade();
				socioeconomico 	= new PerfilSocioeconomico();
				sexo 			= new Sexo();
				
				sexo			.setDescricao(rs.getString("x.descricao"));
				sexo			.setId(rs.getInt("x.id"));
				socioeconomico	.setDescricao(rs.getString("s.descricao"));
				socioeconomico	.setId(rs.getInt("s.id"));
				escolaridade	.setDescricao(rs.getString("e.descricao"));
				escolaridade	.setId(rs.getInt("e.id"));
				pessoa			.setDataNacimento(rs.getDate("dataNascimento"));
				pessoa			.setEscolaridade(escolaridade);
				pessoa			.setPerfilSocieconomico(socioeconomico);
				pessoa			.setSexo(sexo);
				
				pessoas.add(pessoa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao listar pessoas",e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conexão com o banco",e1);
		}
			return pessoas;
	}
}
