package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CategoriaBeans;
import dao.CategoriaDao;
import dao.ProdutoDao;

/**
 * Servlet implementation class CadastroCategoriaServlet
 */
@WebServlet("/cadastroCategoria")
public class CadastroCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoriaDao dao = new CategoriaDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		RequestDispatcher rd = request.getRequestDispatcher("/cadastroCategoria.jsp");

		if (acao != null && acao.equalsIgnoreCase("cadastrar")) {
			CategoriaDao categoriaBeans = new CategoriaDao();
			List<CategoriaBeans> cat = categoriaBeans.listar();

			request.setAttribute("categoria", cat);

		} else if (acao != null && acao.equalsIgnoreCase("editar")) {

			request.setAttribute("categoria", dao.listar());
			request.setAttribute("cat", dao.buscar(user));

		} else if (acao != null && acao.equalsIgnoreCase("delete")) {
			dao.deletar(user);

			request.setAttribute("categoria", dao.listar());
		}

		rd.forward(request, response);
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroCategoria.jsp");
			request.setAttribute("categoria", this.dao.listar());
			rd.forward(request, response);
		} else {

			String nome = request.getParameter("nome");
			String id = request.getParameter("id");

			CategoriaBeans categoriaBeans = new CategoriaBeans();
			categoriaBeans.setId(id.isEmpty() ? null : Integer.valueOf(id));
			categoriaBeans.setNome(nome);

			boolean podeInserir = true;
			
			
			if(id == null && id.isEmpty() && !this.dao.validarCadastro(nome)) {
				request.setAttribute("error", "Este produto ja foi cadastrado!");
				podeInserir = false;
			}
			
			if(id == null && id.isEmpty() && !this.dao.validarCadastroUpdate(nome, id)) {
				this.dao.cadastrar(categoriaBeans);
				request.setAttribute("msg", "Salvo com sucesso!");
				
			}else if(id == null && id.isEmpty() && !this.dao.validarCadastroUpdate(nome, id)) {
				request.setAttribute("error", "Este produto ja foi cadastrado!");
			}
			
			if(id == null && id.isEmpty() && podeInserir) {
			}else if(id != null && !id.isEmpty() && podeInserir) {
				this.dao.atualizar(categoriaBeans);
				request.setAttribute("msg", "Atualizado com sucesso!");
			}
			
			if (id == null || id.isEmpty() && this.dao.validarCadastro(nome) && podeInserir) {
				this.dao.cadastrar(categoriaBeans);
			} else if (id != null || !id.isEmpty() && podeInserir) {

				this.dao.atualizar(categoriaBeans);
			}

			if (!podeInserir) {
				request.setAttribute("cat", categoriaBeans);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroCategoria.jsp");
			request.setAttribute("categoria", this.dao.listar());
			rd.forward(request, response);

		}

	}

}
