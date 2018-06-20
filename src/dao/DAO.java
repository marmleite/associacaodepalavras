
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import util.ConnectionFactory;

public class DAO {

	/**
	 * estrutura de pesos
	 */
	// camadas de pesos:
	public double[] pesosEntradaTempo = new double[100];
	public double[] pesosEntradaErros = new double[100];
	public double[] pesosEntradaRepeticao = new double[100];
	public double[][] pesos1 = new double[300][200];
	public double[][] pesos2 = new double[200][100];
	public double[][] pesosSaida = new double[100][3];
	// camada de pesos do bias (=1)
	public double[][] bias1 = new double[300][200];
	public double[][] bias2 = new double[200][100];
	public double[][] bias3 = new double[100][3];

	/*
	 * inicializa o banco de dados com os valores iniciais.
	 */

//	public static void main(String[] args) {
//		try {
//			inicializador();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void inicializador() {

		Connection con;
		try {
			con = ConnectionFactory.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("Erro ao abrir conex„o do inicializador do detector");
		}
		Random r = new Random();

		try {
			Statement stmt = con.createStatement();
			String query;
//			String query = "CREATE OR REPLACE TABLE pesosEntradaTempo(ID int PRIMARY KEY, peso double); CREATE OR REPLACE TABLE pesosEntradaErros(ID int PRIMARY KEY, peso double); "
//					+ "CREATE OR REPLACE TABLE pesosEntradaRepeticao(ID int PRIMARY KEY, peso double);"
//					+ "CREATE OR REPLACE TABLE pesos1(ID int PRIMARY KEY, peso double) ;CREATE OR REPLACE TABLE pesos2(ID int PRIMARY KEY, peso double); "
//					+ "CREATE OR REPLACE TABLE pesosSaida(ID int PRIMARY KEY, peso double); CREATE OR REPLACE TABLE bias1(ID int PRIMARY KEY, peso double);"
//					+ "CREATE OR REPLACE TABLE bias2(ID int PRIMARY KEY, peso double); CREATE OR REPLACE TABLE bias3(ID int PRIMARY KEY, peso double); "
//					+ "CREATE OR REPLACE TABLE erro(geracao int PRIMARY KEY AUTO_INCREMENT, erro double); CREATE OR REPLACE TABLE historia(id int PRIMARY KEY, nomeHistoria varchar(20));"
//					+ "CREATE OR REPLACE TABLE teste(geracao int PRIMARY KEY AUTO_INCREMENT, historia int, historiasugerida int);";
//
//			// fazendo por partes
//			stmt.executeUpdate(query);

			/*
			 * inicializando a rede com valores aleat√≥rias
			 */
			query = "insert INTO pesosEntradaTempo(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + (r.nextDouble() / 100) + "), ";
			}
			query += "(99," + (r.nextDouble() / 100) + "); ";

			// fazendo por partes
			stmt.executeUpdate(query);

			query = "insert INTO pesosEntradaErros(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + (r.nextDouble() / 100) + "), ";
			}
			query += "(99," + (r.nextDouble() / 100) + "); ";

			query += "insert INTO pesosEntradaRepeticao(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + (r.nextDouble() / 100) + "), ";
			}
			query += "(99," + (r.nextDouble() / 100) + "); ";
			System.out.println(query);
			stmt.executeUpdate(query);

			query = "insert INTO pesos1(ID, peso) VALUES";
			for (int i = 0; i < 59999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + (r.nextDouble() / 10000) + ");";
					stmt.executeUpdate(query);
					query = "insert INTO pesos1(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + (r.nextDouble() / 10000) + "), ";
				}
			}
			query += "(59999," + (r.nextDouble() / 100) + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO pesos2(ID, peso) VALUES";
			for (int i = 0; i < 19999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + (r.nextDouble() / 10000) + ");";
					stmt.executeUpdate(query);
					query = "insert INTO pesos2(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + (r.nextDouble() / 10000) + "), ";
				}
			}
			query += "(19999," + (r.nextDouble() / 100) + "); ";
			stmt.executeUpdate(query);

			query = "insert INTO pesosSaida(ID, peso) VALUES";
			for (int i = 0; i < 299; i++) {
				query += "(" + i + "," + (r.nextDouble() / 10000) + "), ";
			}
			query += "(299," + (r.nextDouble() / 100) + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO bias1(ID, peso) VALUES";
			for (int i = 0; i < 59999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + (r.nextDouble() / 10000) + ");";
					stmt.executeUpdate(query);
					query = "insert INTO bias1(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + (r.nextDouble() / 10000) + "), ";
				}
			}
			query += "(59999," + (r.nextDouble() / 100) + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO bias2(ID, peso) VALUES";
			for (int i = 0; i < 19999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + (r.nextDouble() / 10000) + ");";
					stmt.executeUpdate(query);
					query = "insert INTO bias2(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + (r.nextDouble() / 10000) + "), ";
				}
			}
			query += "(19999," + (r.nextDouble() / 100) + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO bias3(ID, peso) VALUES";
			for (int i = 0; i < 299; i++) {
				query += "(" + i + "," + (r.nextDouble() / 100) + "), ";
			}
			query += "(299," + (r.nextDouble() / 100) + "); ";
			stmt.executeUpdate(query);

		} catch (Exception e) {
			throw new RuntimeException("Erro ao inserir historico da rede");
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conex„o historico da rede");
		}

	}

	public void buscaPesos() {

		Connection con = ConnectionFactory.getConnection();
		try {
			Statement stmt = con.createStatement();

			String sql = "SELECT * FROM bias1 ORDER BY id";
			ResultSet rs = stmt.executeQuery(sql);
			for (int i = 0; i < 300; i++) {
				for (int y = 0; y < 200; y++) {
					rs.next();
					this.bias1[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM pesos1 ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 300; i++) {
				for (int y = 0; y < 200; y++) {
					rs.next();
					this.pesos1[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM bias2 ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 200; i++) {
				for (int y = 0; y < 100; y++) {
					rs.next();
					this.bias2[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM pesos2 ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 200; i++) {
				for (int y = 0; y < 100; y++) {
					rs.next();
					this.pesos2[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM bias3 ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 100; i++) {
				for (int y = 0; y < 3; y++) {
					rs.next();
					this.bias3[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM pesosSaida ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 100; i++) {
				for (int y = 0; y < 3; y++) {
					rs.next();
					this.pesosSaida[i][y] = rs.getDouble("peso");
				}
			}

			sql = "SELECT * FROM pesosEntradaTempo ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 100; i++) {
				rs.next();
				this.pesosEntradaTempo[i] = rs.getDouble("peso");
			}

			sql = "SELECT * FROM pesosEntradaErros ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 100; i++) {
				rs.next();
				this.pesosEntradaErros[i] = rs.getDouble("peso");
			}

			sql = "SELECT * FROM pesosEntradaRepeticao ORDER BY id";
			rs = stmt.executeQuery(sql);
			for (int i = 0; i < 100; i++) {
				rs.next();
				this.pesosEntradaRepeticao[i] = rs.getDouble("peso");
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir historico da rede");
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conex„o historico da rede");
		}

	}

	public void atualizarPesos() {

		Connection con = ConnectionFactory.getConnection();
		try {
			// String sql = "UPDATE pesosEntradaErros SET peso=? WHERE id= ?";
			// PreparedStatement ps = con.prepareStatement(sql);
			//
			// for (int i = 0; i < 100; i++) {
			// ps.setDouble(1, this.pesosEntradaErros[i]);
			// ps.setInt(2, i);
			// ps.addBatch();
			// }
			// System.out.println('1');
			// sql = "UPDATE pesosEntradaRepeticao SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 100; i++) {
			// ps.setDouble(1, this.pesosEntradaRepeticao[i]);
			// ps.setInt(2, i);
			// ps.addBatch();
			// }
			// System.out.println('2');
			// sql = "UPDATE pesosEntradaErros SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 100; i++) {
			// ps.setDouble(1, this.pesosEntradaErros[i]);
			// ps.setInt(2, i);
			// ps.addBatch();
			// }
			//
			// ps.executeBatch();
			// System.out.println('3');
			// sql = "UPDATE pesosSaida SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 100; i++) {
			// for(int y=0; y < 3; y++) {
			// ps.setDouble(1, this.pesosSaida[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// }
			//
			// System.out.println('4');
			// sql = "UPDATE bias3 SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 100; i++) {
			// for(int y=0; y < 3; y++) {
			// ps.setDouble(1, this.bias3[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// }
			//
			// ps.executeBatch();
			// System.out.println('5');
			// sql = "UPDATE pesos2 SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 200; i++) {
			// for(int y=0; y < 100; y++) {
			// ps.setDouble(1, this.pesos2[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// if(i%20==0) {
			// ps.executeBatch();
			// System.out.println(i);
			// }
			// }
			// ps.executeBatch();
			// System.out.println('6');
			// sql = "UPDATE bias2 SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 200; i++) {
			// for(int y=0; y < 100; y++) {
			// ps.setDouble(1, this.bias2[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// if(i%20==0) {
			// ps.executeBatch();
			// }
			// }
			// ps.executeBatch();
			//
			// System.out.println('7');
			// sql = "UPDATE bias1 SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 300; i++) {
			// for(int y=0; y < 200; y++) {
			// ps.setDouble(1, this.bias1[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// if(i%2==0) {
			// ps.executeBatch();
			// }
			// }
			// ps.executeBatch();
			// System.out.println('8');
			// sql = "UPDATE pesos1 SET peso=? WHERE id= ?";
			// ps = con.prepareStatement(sql);
			// for (int i = 0; i < 300; i++) {
			// for(int y=0; y < 200; y++) {
			// ps.setDouble(1, this.pesos1[i][y]);
			// ps.setInt(2, (i*3)+y);
			// ps.addBatch();
			// }
			// if(i%2==0) {
			// ps.executeBatch();
			// }
			// }
			// ps.executeBatch();
			Statement stmt = con.createStatement();

			String query = "DELETE FROM pesosEntradaTempo; DELETE FROM pesosEntradaErros;"
					+ " DELETE FROM pesosEntradaRepeticao; DELETE FROM pesos1; DELETE FROM pesos2;"
					+ "DELETE FROM pesosSaida; DELETE FROM bias1; DELETE FROM bias2; DELETE FROM bias3;";

			stmt.executeUpdate(query);

			query = "insert INTO pesosEntradaTempo(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + this.pesosEntradaTempo[i] + "), ";
			}
			query += "(99," + this.pesosEntradaTempo[99] + "); ";

			query += "insert INTO pesosEntradaErros(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + this.pesosEntradaErros[i] + "), ";
			}
			query += "(99," + this.pesosEntradaErros[99] + "); ";

			query += "insert INTO pesosEntradaRepeticao(ID, peso) VALUES";
			for (int i = 0; i < 99; i++) {
				query += "(" + i + "," + this.pesosEntradaRepeticao[i] + "), ";
			}
			query += "(99," + this.pesosEntradaErros[99] + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO pesos1(ID, peso) VALUES";
			for (int i = 0; i < 59999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + this.pesos1[(int) (i / 200)][(i % 200)] + ");";
					stmt.executeUpdate(query);
					query = "insert INTO pesos1(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + this.pesos1[(int) (i / 200)][(i % 200)] + "), ";
				}
			}
			query += "(59999," + this.pesos1[299][199] + "); ";
			stmt.executeUpdate(query);

			query = "insert INTO pesos2(ID, peso) VALUES";
			for (int i = 0; i < 19999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + this.pesos2[(int) (i / 100)][(i % 100)] + ");";
					stmt.executeUpdate(query);
					query = "insert INTO pesos2(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + this.pesos2[(int) (i / 100)][(i % 100)] + "), ";
				}
			}
			query += "(19999," + this.pesos2[199][99] + "); ";
			stmt.executeUpdate(query);

			query = "insert INTO pesosSaida(ID, peso) VALUES";
			for (int i = 0; i < 299; i++) {
				query += "(" + i + "," + this.pesosSaida[(int) (i / 3)][(i % 3)] + "), ";
			}
			query += "(299," + this.pesosSaida[99][2] + "); ";
			stmt.executeUpdate(query);

			query = "insert INTO bias1(ID, peso) VALUES";
			for (int i = 0; i < 59999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + this.bias1[(int) (i / 200)][(i % 200)] + ");";
					stmt.executeUpdate(query);
					query = "insert INTO bias1(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + this.bias1[(int) (i / 200)][(i % 200)] + "), ";
				}
			}
			query += "(59999," + this.bias1[299][199] + "); ";
			stmt.executeUpdate(query);

			query = "insert INTO bias2(ID, peso) VALUES";
			for (int i = 0; i < 19999; i++) {
				if (i % 1000 == 0) {
					query += "(" + i + "," + this.bias2[(int) (i / 100)][(i % 100)] + ");";
					stmt.executeUpdate(query);
					query = "insert INTO bias2(ID, peso) VALUES";
				} else {
					query += "(" + i + "," + this.bias2[(int) (i / 100)][(i % 100)] + "), ";
				}
			}
			query += "(19999," + this.bias2[199][99] + "); ";

			stmt.executeUpdate(query);

			query = "insert INTO bias3(ID, peso) VALUES";
			for (int i = 0; i < 299; i++) {
				query += "(" + i + "," + this.bias3[(int) (i / 3)][(i % 3)] + "), ";
			}
			query += "(299," + this.bias3[99][2] + "); ";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir historico da rede");
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conex„o historico da rede");
		}
	}

	/*
	 * resultado sugerido x resultado apresentado + erro do teste
	 */
	public void insereHistoricoRede(double erroTotal, int resultado) {

		Connection con = ConnectionFactory.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			String query = "UPDATE teste SET historiaSugerida = "+resultado+"; ";
			query += "insert INTO erro(erro) VALUE (" + erroTotal + ");";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir historico da rede");
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar conex„o historico da rede");
		}
	}

}
