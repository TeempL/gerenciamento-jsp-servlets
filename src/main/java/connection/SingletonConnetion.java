package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnetion {
	
	private static Connection connection=null ;
	private static String url = "jdbc:mysql://localhost/cursojsp";
	private static String user  = "root";
	private static String pass = "root";
	
	static {
		obterConexao();
	}
	
	public SingletonConnetion() {
		obterConexao();
	}
	
	private static void obterConexao() {
		try {
			if(connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, pass);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao conectar com banco de dados");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
