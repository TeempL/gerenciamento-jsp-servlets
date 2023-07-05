package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProdutoBeans;
import dao.ProdutoDao;

@WebServlet("/cadastroProduto")
public class CadastroProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDao produtoDao = new ProdutoDao();

	ProdutoBeans produtoBeans = new ProdutoBeans();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		RequestDispatcher rd = request.getRequestDispatcher("/cadastroProduto.jsp");
		
		if (acao != null && acao.equalsIgnoreCase("editar")) {
			ProdutoBeans produtoBeans = this.produtoDao.buscar(user);
			request.setAttribute("usuarios", this.produtoDao.listar());
			request.setAttribute("user", produtoBeans);
			

		} else if (acao != null && acao.equalsIgnoreCase("delete")) {
			this.produtoDao.deletar(user);
			request.setAttribute("usuarios", this.produtoDao.listar());
			
		} else if (acao != null && acao.equalsIgnoreCase("listar")) {
			request.setAttribute("usuarios", this.produtoDao.listar());
			
		} 
//		else {
//			request.setAttribute("usuarios", this.produtoDao.listar());
//		}
		
		request.setAttribute("categorias", this.produtoDao.listarCategoria());
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("usuarios", this.produtoDao.listar());
			request.setAttribute("categorias", this.produtoDao.listarCategoria());
			rd.forward(request, response);
		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria_id");
			
			System.out.println(categoria);

			ProdutoBeans produtoBeans = new ProdutoBeans();
			produtoBeans.setId(id.isEmpty() ? null : Integer.valueOf(id));
			produtoBeans.setNome(nome);
			produtoBeans.setQuantidade(quantidade.isEmpty() ? null : Integer.parseInt(quantidade));
			produtoBeans.setCategoria_id(categoria.isEmpty() ? null: Integer.parseInt(categoria));
			if (valor != null && !valor.isEmpty()) {
				String valorConverte = valor.replaceAll("\\.", "");
				valorConverte = valorConverte.replaceAll("\\,", ".");
				produtoBeans.setValor(valor.isEmpty() ? null : Double.parseDouble(valorConverte));

			}

			boolean podeInserir = true;

			if (id == null || id.isBlank() && !this.produtoDao.validarCadastro(nome)) {
				RequestDispatcher rd = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("msg", "Esse produto ja foi cadastrado!");
				request.setAttribute("usuarios", this.produtoDao.listar());
				rd.forward(request, response);
				podeInserir = false;
			}

			if (id == null || id.isBlank() && !this.produtoDao.validarCadastroUpdate(nome, id)) {
				this.produtoDao.cadastrar(produtoBeans);
			} else if (id == null || id.isBlank() && !this.produtoDao.validarCadastroUpdate(nome, id)) {
				RequestDispatcher rd = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("msg", "Esse produto ja foi cadastrado!");
				rd.forward(request, response);
			}

			if (id == null || id.isEmpty() && this.produtoDao.validarCadastro(nome) && podeInserir) {
				this.produtoDao.cadastrar(produtoBeans);
			} else if (id != null || !id.isEmpty() && podeInserir) {

				this.produtoDao.atualizar(produtoBeans);
			}

			if (!podeInserir) {
				request.setAttribute("user", produtoBeans);
			}

			RequestDispatcher rd = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("usuarios", this.produtoDao.listar());
			request.setAttribute("categorias", this.produtoDao.listarCategoria());
			rd.forward(request, response);
		}

	}

}
