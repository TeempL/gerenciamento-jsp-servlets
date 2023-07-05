package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import beans.LoginBeans;
import connection.SingletonConnetion;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingletonConnetion.getConnection();
	}

	public void cadastrar(LoginBeans loginBeans) {
		String sql = "INSERT INTO usuario(nome,login, senha, telefone, cep, cidade, rua,bairro, uf, ibge, fotobase64, contentType, contentTypeCurriculo, "
				+ "curriculobase64, fotoBase64Miniatura, ativo, sexo, perfil, complemento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, loginBeans.getNome());
			ps.setString(2, loginBeans.getLogin());
			ps.setString(3, loginBeans.getSenha());
			ps.setString(4, loginBeans.getTelefone());
			ps.setString(5, loginBeans.getCep());
			ps.setString(6, loginBeans.getCidade());
			ps.setString(7, loginBeans.getRua());
			ps.setString(8, loginBeans.getBairro());
			ps.setString(9, loginBeans.getUf());
			ps.setString(10, loginBeans.getIbge());
			ps.setString(11, loginBeans.getFotoBase64());
			ps.setString(12, loginBeans.getContentType());
			ps.setString(13, loginBeans.getContentTypeCurriculo());
			ps.setString(14, loginBeans.getCurriculoBase64());
			ps.setString(15, loginBeans.getFotoBase64Miniatura());
			ps.setBoolean(16, loginBeans.isAtivo());
			ps.setString(17, loginBeans.getSexo());
			ps.setString(18, loginBeans.getPerfil());
			ps.setString(19, loginBeans.getComplemento());
			ps.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<LoginBeans> listaConsulta(String consulta, String selecao){
		String sql = "SELECT * FROM usuario WHERE login <> 'admin' and " + selecao +" like'%"+ consulta + "%'";
		return consultarUsuarios(sql);
	}
	

	public List<LoginBeans> listarUsuarios() {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin'";
		return consultarUsuarios(sql);
	}

	private List<LoginBeans> consultarUsuarios(String sql) {
		List<LoginBeans> lista = new ArrayList<LoginBeans>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LoginBeans loginBeans = new LoginBeans();
					loginBeans.setId(rs.getInt("id"));
					loginBeans.setNome(rs.getString("nome"));
					loginBeans.setLogin(rs.getString("login"));
					loginBeans.setSenha(rs.getString("senha"));
					loginBeans.setTelefone(rs.getString("telefone"));
					loginBeans.setCep(rs.getString("cep"));
					loginBeans.setCidade(rs.getString("cidade"));
					loginBeans.setBairro(rs.getString("bairro"));
					loginBeans.setIbge(rs.getString("ibge"));
					loginBeans.setUf(rs.getString("uf"));
					loginBeans.setRua(rs.getString("rua"));
					loginBeans.setFotoBase64(rs.getString("fotobase64"));
					loginBeans.setContentType(rs.getString("contentType"));
					loginBeans.setCurriculoBase64(rs.getString("curriculobase64"));
					loginBeans.setContentTypeCurriculo(rs.getString("contentTypeCurriculo"));
					loginBeans.setFotoBase64Miniatura(rs.getString("fotoBase64Miniatura"));
					loginBeans.setSexo(rs.getString("sexo"));
					loginBeans.setPerfil(rs.getString("perfil"));
					loginBeans.setComplemento(rs.getString("complemento"));
					lista.add(loginBeans);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void deleteUsuario(String id) {
		String sql = "DELETE FROM usuario WHERE id = ? and login <> 'admin'";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			ps.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginBeans buscarUsuario(String id) {
		String sql = "SELECT * FROM usuario where id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					LoginBeans lb = new LoginBeans();
					lb.setId(rs.getInt("id"));
					lb.setNome(rs.getString("nome"));
					lb.setLogin(rs.getString("login"));
					lb.setSenha(rs.getString("senha"));
					lb.setTelefone(rs.getString("telefone"));
					lb.setCep(rs.getString("cep"));
					lb.setCidade(rs.getString("cidade"));
					lb.setRua(rs.getString("rua"));
					lb.setBairro(rs.getString("bairro"));
					lb.setIbge(rs.getString("ibge"));
					lb.setUf(rs.getString("uf"));
					lb.setFotoBase64(rs.getString("fotobase64"));
					lb.setContentType(rs.getString("contentType"));
					lb.setCurriculoBase64(rs.getString("curriculobase64"));
					lb.setContentTypeCurriculo(rs.getString("contentTypeCurriculo"));
					lb.setFotoBase64Miniatura(rs.getString("fotoBase64Miniatura"));
					lb.setAtivo(rs.getBoolean("ativo"));
					lb.setSexo(rs.getString("sexo"));
					lb.setPerfil(rs.getString("perfil"));
					lb.setComplemento(rs.getString("complemento"));
					return lb;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean validarLogin(String login) {
		String sql = "SELECT count(1) as qtd FROM usuario WHERE login = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, login);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validarLoginUpdate(String login, String id) {
		String sql = "SELECT count(1) as qtd FROM usuario WHERE login = ? and id <> ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, login);
			ps.setString(2, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validarSenhaUpdate(String senha, String id) {
		String sql = "SELECT count(1) as qtd FROM usuario WHERE senha = ? and id <> ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, senha);
			ps.setString(2, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validarSenha(String senha) {
		String sql = "SELECT count(1) as qtd FROM usuario WHERE senha = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, senha);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("qtd") <= 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void atualizarUsuario(LoginBeans l) {

		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE usuario SET nome = ?, login = ?, senha = ?, telefone = ?, cep = ?, cidade = ?, ");
		sql.append("rua = ? ,bairro = ?, ibge = ?, uf = ?, ativo = ?, sexo = ?, perfil = ?, complemento = ?");

		if (l.isAtualizaImagem()) {
			sql.append(", fotobase64 =?, contentType = ?");
		}
		if (l.isAtualizaPdf()) {
			sql.append(", curriculobase64 =?, contentTypeCurriculo =?");
		}

		if (l.isAtualizaImagem()) {
			sql.append(", fotoBase64Miniatura = ?");
		}

		sql.append("WHERE id = " + l.getId());

		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setString(1, l.getNome());
			ps.setString(2, l.getLogin());
			ps.setString(3, l.getSenha());
			ps.setString(4, l.getTelefone());
			ps.setString(5, l.getCep());
			ps.setString(6, l.getCidade());
			ps.setString(7, l.getRua());
			ps.setString(8, l.getBairro());
			ps.setString(9, l.getIbge());
			ps.setString(10, l.getUf());
			ps.setBoolean(11, l.isAtivo());
			ps.setString(12, l.getSexo());
			ps.setString(13, l.getPerfil());
			ps.setString(14, l.getComplemento());

			if (l.isAtualizaImagem()) {
				ps.setString(15, l.getFotoBase64());
				ps.setString(16, l.getContentType());
			}
			if (l.isAtualizaPdf()) {
				if (l.isAtualizaPdf() && !l.isAtualizaImagem()) {
					ps.setString(15, l.getCurriculoBase64());
					ps.setString(16, l.getContentTypeCurriculo());
				} else {
					ps.setString(17, l.getCurriculoBase64());
					ps.setString(18, l.getContentTypeCurriculo());
				}
			} else {
				if (l.isAtualizaImagem()) {
					ps.setString(17, l.getFotoBase64Miniatura());
				}
			}
			if (l.isAtualizaImagem() && l.isAtualizaPdf()) {
				ps.setString(19, l.getFotoBase64Miniatura());
			}

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
