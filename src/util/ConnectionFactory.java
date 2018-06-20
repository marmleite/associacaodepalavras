package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    
	public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/associacaodepalavras?allowMultiQueries=true", "dev", "123");
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("Não foi possível conectar com o banco de dados.");
        }
    }
}
