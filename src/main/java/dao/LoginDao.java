package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingletonConnetion;

public class LoginDao {

	private Connection connection;
	
	public LoginDao() {
		connection = SingletonConnetion.getConnection();
	}
	
	public boolean validaLogin(String login, String senha) throws Exception{
		String sql = "SELECT * FROM usuario WHERE login = ? and senha = ?";
		try(PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, login);
			ps.setString(2, senha);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
}
