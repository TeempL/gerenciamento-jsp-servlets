package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.LoginBeans;
import beans.Telefones;
import dao.TelefoneDao;
import dao.UsuarioDao;

@WebServlet("/cadastroTelefones")
public class CadastroTelefonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();
	private TelefoneDao telefoneDao = new TelefoneDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.endsWith("addFone")) {

			String id = request.getParameter("user");

			LoginBeans beans = usuarioDao.buscarUsuario(id);
			request.getSession().setAttribute("usuario", this.usuarioDao.buscarUsuario(id));
			request.setAttribute("usuario", this.usuarioDao.buscarUsuario(id));

			RequestDispatcher rd = request.getRequestDispatcher("/cadastroTelefones.jsp");
			request.setAttribute("telefones", this.telefoneDao.listar(beans.getId()));
			rd.forward(request, response);

		} else if (acao != null && acao.endsWith("deleteFone")) {
			String fone = request.getParameter("fone");

			this.telefoneDao.deletar(fone);
			LoginBeans loginBeans = (LoginBeans) request.getSession().getAttribute("usuario");

			RequestDispatcher rd = request.getRequestDispatcher("/cadastroTelefones.jsp");
			request.setAttribute("telefones", this.telefoneDao.listar(loginBeans.getId()));
			rd.forward(request, response);
		} else {
			LoginBeans loginBeans = (LoginBeans) request.getSession().getAttribute("usuario");
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroTelefones.jsp");
			request.setAttribute("telefones", this.telefoneDao.listar(loginBeans.getId()));
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginBeans loginBeans = (LoginBeans) request.getSession().getAttribute("usuario");

		String numero = request.getParameter("telefone");
		String tipo = request.getParameter("tipo");
		String acao = request.getParameter("acao");

		if (acao == null || (acao != null && !acao.equalsIgnoreCase("cancelar"))) {
			Telefones tel = new Telefones();
			tel.setNumero(numero);
			tel.setTipo(tipo);
			tel.setUsuario(loginBeans.getId());

			this.telefoneDao.cadastrar(tel);

			request.getSession().setAttribute("usuario", loginBeans);
			request.setAttribute("usuario", loginBeans);

			RequestDispatcher rd = request.getRequestDispatcher("/cadastroTelefones.jsp");
			request.setAttribute("msg", "Salvo com sucesso!");
			request.setAttribute("telefones", this.telefoneDao.listar(loginBeans.getId()));
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", this.usuarioDao.listarUsuarios());
			rd.forward(request, response);

		}

	}

}
