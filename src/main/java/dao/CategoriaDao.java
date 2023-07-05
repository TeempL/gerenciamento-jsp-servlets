package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CategoriaBeans;
import beans.Telefones;
import connection.SingletonConnetion;

public class CategoriaDao {

	
	private Connection connection;
	
	public CategoriaDao() {
		this.connection = SingletonConnetion.getConnection();
	}
	
	public void cadastrar(CategoriaBeans cat) {
		String sql = "INSERT INTO categoria(nome) VALUES(?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, cat.getNome());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void atualizar(CategoriaBeans pd) {
		String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, pd.getNome());
			ps.setInt(2, pd.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<CategoriaBeans> listar() {
		List<CategoriaBeans> lista = new ArrayList<CategoriaBeans>();
		String sql = "SELECT * FROM categoria";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CategoriaBeans produto = new CategoriaBeans();
					produto.setId(rs.getInt("id"));
					produto.setNome(rs.getString("nome"));
					lista.add(produto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return lista;
	}
	
	public boolean validarCadastro(String nome) {
		String sql = "SELECT COUNT(1) as qtd FROM categoria WHERE nome = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nome);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	

	public CategoriaBeans buscar(String id) {
		String sql = "SELECT * FROM categoria WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					CategoriaBeans pd = new CategoriaBeans();
					pd.setId(rs.getInt("id"));
					pd.setNome(rs.getString("nome"));
					return pd;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	
	public boolean validarCadastroUpdate(String nome, String id) {
		String sql = "SELECT COUNT(1) as qtd FROM categoria WHERE nome = ? and id <> ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nome);
			ps.setString(2, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	public void deletar(String id) {
		String sql = "DELETE FROM categoria WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
