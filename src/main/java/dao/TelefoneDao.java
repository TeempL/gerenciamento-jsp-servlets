package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import beans.ProdutoBeans;
import beans.Telefones;
import connection.SingletonConnetion;

public class TelefoneDao {

	private Connection connection;

	public TelefoneDao() {
		connection = SingletonConnetion.getConnection();
	}

	public void cadastrar(Telefones tel) {
		String sql = "INSERT INTO fone(numero, tipo, usuario) VALUES(?,?,?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, tel.getNumero());
			ps.setString(2, tel.getTipo());
			ps.setInt(3, tel.getUsuario());
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

	public void atualizar(Telefones pd) {
		String sql = "UPDATE fone SET numero = ?, tipo = ?, usuario = ? WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, pd.getNumero());
			ps.setString(2, pd.getTipo());
			ps.setInt(3, pd.getUsuario());
			ps.setInt(4, pd.getId());
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

	public List<Telefones> listar(Integer usuario) {
		List<Telefones> lista = new ArrayList<Telefones>();
		String sql = "SELECT * FROM fone WHERE usuario = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, usuario);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Telefones produto = new Telefones();
					produto.setId(rs.getInt("id"));
					produto.setNumero(rs.getString("numero"));
					produto.setTipo(rs.getString("tipo"));
					produto.setUsuario(rs.getInt("usuario"));
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

	public Telefones buscar(String id) {
		String sql = "SELECT * FROM fone WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Telefones pd = new Telefones();
					pd.setId(rs.getInt("id"));
					pd.setNumero(rs.getString("numero"));
					pd.setTipo(rs.getString("tipo"));
					pd.setUsuario(rs.getInt("usuario"));
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

	public void deletar(String id) {
		String sql = "DELETE FROM fone WHERE id = ?";

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
