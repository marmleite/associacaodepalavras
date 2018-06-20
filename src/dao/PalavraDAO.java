package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Palavra;
import util.ConnectionFactory;

public class PalavraDAO {

	private PreparedStatement stmt;
	private String query;
	private ResultSet rs;

	public Palavra buscar(Integer idPalavra) {
		query = "SELECT * FROM palavra WHERE id = ?";

		Palavra palavra = null;

		try (Connection conexao = ConnectionFactory.getConnection()) {

			stmt = conexao.prepareStatement(query);
			stmt.setInt(1, idPalavra);
			rs = stmt.executeQuery();

			if (rs.next()) {
				palavra = new Palavra();

				palavra.setId(rs.getInt("id"));
				palavra.setCaminhoAudio(rs.getString("caminhoAudio"));
				palavra.setMediaReacao(rs.getFloat("mediaReacao"));
				palavra.setDesvioPadrao(rs.getFloat("desvioPadrao"));
				palavra.setPercentualErros(rs.getFloat("percentualErros"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar dados da palavra", e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conex„o com o banco", e1);
		}
		return palavra;
	}

	public List<Palavra> listar() {

		query = "SELECT * FROM palavra";

		List<Palavra> palavras = null;
		Palavra palavra = null;

		try (Connection conexao = ConnectionFactory.getConnection()) {

			stmt = conexao.prepareStatement(query);
			rs = stmt.executeQuery();
			palavras = new ArrayList<Palavra>();

			while (rs.next()) {
				palavra = new Palavra();

				palavra.setId(rs.getInt("id"));
				palavra.setTexto(rs.getString("texto"));
				palavra.setCaminhoAudio(rs.getString("caminhoAudio"));
				palavra.setMediaReacao(rs.getFloat("MediaReacaoPalavra"));
				palavra.setDesvioPadrao(rs.getFloat("DesvioPadraoPalavra"));
				palavra.setPercentualErros(rs.getFloat("PorcentagemRepeticoesErradas"));

				palavras.add(palavra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar dados da palavra", e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conex„o com o banco", e1);
		}
		return palavras;
	}

	public void atualizar(Palavra palavra) {

		query = "UPDATE palavra SET".concat(" 	MediaReacaoPalavra = ? ").concat(", 	DesvioPadraoPalavra = ? ")
				.concat(",	PorcentagemRepeticoesErradas = ? ").concat(" WHERE id = ? ");

		try (Connection conexao = ConnectionFactory.getConnection()) {

			stmt = conexao.prepareStatement(query);
			stmt.setFloat(1, palavra.getMediaReacao());
			stmt.setFloat(2, palavra.getDesvioPadrao());
			stmt.setFloat(3, palavra.getPercentualErros());
			stmt.setInt(4, palavra.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar dados da palavra", e);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro na conex√£o com o banco", e1);
		}
	}

}
