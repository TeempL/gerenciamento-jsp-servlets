package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.LoginBeans;
import dao.UsuarioDao;


@WebServlet("/pesquisarUsuario")
public class PesquisarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	UsuarioDao dao = new UsuarioDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pesquisa = request.getParameter("pesquisa");
		String selecao = request.getParameter("selecao");
		
		
		
		if(pesquisa != null && !pesquisa.trim().isEmpty()) {
			List<LoginBeans> lista = dao.listaConsulta(pesquisa, selecao);
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", lista);
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", dao.listarUsuarios());
			rd.forward(request, response);
		}

	}

}
