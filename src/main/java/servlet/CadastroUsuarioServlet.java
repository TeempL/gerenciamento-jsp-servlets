package servlet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.LoginBeans;
import dao.UsuarioDao;

@WebServlet("/cadastroUsuario")
@MultipartConfig
public class CadastroUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

	public CadastroUsuarioServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(request, response);
		} else {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String username = request.getParameter("username");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String cidade = request.getParameter("cidade");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String ibge = request.getParameter("ibge");
			String uf = request.getParameter("uf");
			String id = request.getParameter("id");
			String sexo = request.getParameter("sexo");
			String complemento = request.getParameter("complemento");
			String perfil = request.getParameter("perfil");

			LoginBeans loginBeans = new LoginBeans();
			loginBeans.setId(id.isEmpty() ? null : Integer.valueOf(id));
			loginBeans.setNome(username);
			loginBeans.setLogin(login);
			loginBeans.setSenha(senha);
			loginBeans.setTelefone(telefone);
			loginBeans.setCep(cep);
			loginBeans.setRua(rua);
			loginBeans.setCidade(cidade);
			loginBeans.setBairro(bairro);
			loginBeans.setUf(uf);
			loginBeans.setIbge(ibge);
			loginBeans.setSexo(sexo);
			loginBeans.setComplemento(complemento);
			loginBeans.setPerfil(perfil);

			if (request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
				loginBeans.setAtivo(true);
				System.out.println(loginBeans.isAtivo());
			} else {
				loginBeans.setAtivo(false);
				System.out.println(loginBeans.isAtivo());
			}
			
			
			
			

			try {

				if (ServletFileUpload.isMultipartContent(request)) {
					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						byte[] converte = converteStremParaByte(imagemFoto.getInputStream());
						String fotobase64 = new Base64().encodeBase64String(converte);

						loginBeans.setFotoBase64(fotobase64);
						loginBeans.setContentType(imagemFoto.getContentType());

						byte[] decoderImage = new Base64().decodeBase64(fotobase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decoderImage));

						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						BufferedImage resize = new BufferedImage(100, 100, type);

						Graphics2D g = resize.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resize, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());
//						System.out.println(miniaturaBase64);
						loginBeans.setFotoBase64Miniatura(miniaturaBase64);

					} else {
						loginBeans.setAtualizaImagem(false);
						loginBeans.setContentType(request.getParameter("contentTemp"));
					}

					Part curriculoPdf = request.getPart("curriculo");
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculobase64 = new Base64()
								.encodeBase64String(converteStremParaByte(curriculoPdf.getInputStream()));

						loginBeans.setCurriculoBase64(curriculobase64);
						loginBeans.setContentTypeCurriculo(curriculoPdf.getContentType());
					} else {
						loginBeans.setAtualizaPdf(false);
						loginBeans.setContentTypeCurriculo(request.getParameter("contentCurriculoTemp"));
					}
				}
				
				// verificações

				String msg = null;
				boolean podeInserir = true;

				if (id == null || id.isEmpty() && !this.usuarioDao.validarLogin(login)) {
					msg = "O login inserido ja esta sendo utilizado por outro usuario";
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !this.usuarioDao.validarSenha(senha)) {
					msg = "A senha inserida ja esta sendo ultilizada por outro usuario";
					podeInserir = false;
				}

				if (msg != null) {
					request.setAttribute("error", msg);
				}

				if (id == null || id.isEmpty() && this.usuarioDao.validarLogin(login) && podeInserir) {
					this.usuarioDao.cadastrar(loginBeans);
					request.setAttribute("msg", "Salvo com sucesso!");
				} else if (id != null && !id.isEmpty() && podeInserir) {
					request.setAttribute("msg", "Usuario atualizado com sucesso!");
					this.usuarioDao.atualizarUsuario(loginBeans);
				}

				if (!podeInserir) {
					request.setAttribute("user", loginBeans);
				}

				RequestDispatcher rd = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
				rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private byte[] converteStremParaByte(InputStream imagem) {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		try {
			int read = imagem.read();
			while (read != -1) {
				arrayOutputStream.write(read);
				read = imagem.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrayOutputStream.toByteArray();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acao = req.getParameter("acao");
		String user = req.getParameter("user");

		if (acao != null && acao.equalsIgnoreCase("delete")) {
			this.usuarioDao.deleteUsuario(user);

			RequestDispatcher rd = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(req, resp);

		} else if (acao != null && acao.equalsIgnoreCase("editar")) {
			LoginBeans loginBeans = this.usuarioDao.buscarUsuario(user);
			RequestDispatcher rd = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("user", loginBeans);
			req.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(req, resp);

		} else if (acao != null && acao.equalsIgnoreCase("listar")) {
			RequestDispatcher rd = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(req, resp);
		} else if (acao != null && acao.equalsIgnoreCase("download")) {

			LoginBeans beans = this.usuarioDao.buscarUsuario(user);
			if (beans != null) {

				String contentType = "";
				byte[] fileBytes = null;

				String tipo = req.getParameter("tipo");
				if (tipo.equalsIgnoreCase("imagem")) {
					contentType = beans.getContentType();
					fileBytes = new Base64().decodeBase64(beans.getFotoBase64());

				} else if (tipo.equalsIgnoreCase("arquivo")) {
					contentType = beans.getContentTypeCurriculo();
					fileBytes = new Base64().decodeBase64(beans.getCurriculoBase64());
				}

				resp.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]);
				InputStream is = new ByteArrayInputStream(fileBytes);

				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream os = resp.getOutputStream();
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}

				os.flush();
				os.close();
			}

		} else {
			RequestDispatcher rd = req.getRequestDispatcher("/cadastroUsuario.jsp");
			req.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(req, resp);
		}

	}
}
