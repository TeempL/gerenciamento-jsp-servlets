package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import beans.CategoriaBeans;
import beans.ProdutoBeans;
import connection.SingletonConnetion;

public class ProdutoDao {

	private Connection connection;

	public ProdutoDao() {
		connection = SingletonConnetion.getConnection();
	}

	public void cadastrar(ProdutoBeans produtos) {
		String sql = "INSERT INTO produto(nome, quantidade, valor, categoria_id) VALUES(?,?,?,?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, produtos.getNome());
			ps.setInt(2, produtos.getQuantidade());
			ps.setDouble(3, produtos.getValor());
			ps.setInt(4, produtos.getCategoria_id());
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

	public void atualizar(ProdutoBeans pd) {
		String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ?, categoria_id = ?  WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, pd.getNome());
			ps.setInt(2, pd.getQuantidade());
			ps.setDouble(3, pd.getValor());
			ps.setInt(4, pd.getCategoria_id());
			ps.setInt(5, pd.getId());
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

	public List<ProdutoBeans> listar() {
		List<ProdutoBeans> lista = new ArrayList<ProdutoBeans>();
		String sql = "SELECT * FROM produto";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ProdutoBeans produto = new ProdutoBeans();
					produto.setId(rs.getInt("id"));
					produto.setNome(rs.getString("nome"));
					produto.setQuantidade(rs.getInt("quantidade"));
					produto.setValor(rs.getDouble("valor"));
					produto.setCategoria_id(rs.getInt("categoria_id"));
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
	
	public List<CategoriaBeans> listarCategoria() {
		List<CategoriaBeans> lista = new ArrayList<CategoriaBeans>();
		String sql = "SELECT * FROM categoria";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CategoriaBeans categoriaBeans = new CategoriaBeans();
					categoriaBeans.setId(rs.getInt("id"));
					categoriaBeans.setNome(rs.getString("nome"));
					lista.add(categoriaBeans);
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
	

	public ProdutoBeans buscar(String id) {
		String sql = "SELECT * FROM produto WHERE id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ProdutoBeans pd = new ProdutoBeans();
					pd.setId(rs.getInt("id"));
					pd.setNome(rs.getString("nome"));
					pd.setQuantidade(rs.getInt("quantidade"));
					pd.setValor(rs.getDouble("valor"));
					pd.setCategoria_id(rs.getInt("categoria_id"));
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
		String sql = "DELETE FROM produto WHERE id = ?";

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

	public boolean validarCadastro(String nome) {
		String sql = "SELECT COUNT(1) as qtd FROM produto WHERE nome = ?";

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
	
	public boolean validarCadastroUpdate(String nome, String id) {
		String sql = "SELECT COUNT(1) as qtd FROM produto WHERE nome = ? and id <> ?";

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
}
