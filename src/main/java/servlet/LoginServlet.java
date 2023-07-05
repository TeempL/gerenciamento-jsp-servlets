package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.LoginBeans;
import dao.LoginDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		
		LoginDao loginDao = new LoginDao();
		
		try {
			if(loginDao.validaLogin(login, senha)) {
				response.sendRedirect("acessoliberado.jsp");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("invalido", "Login ou senha inválido");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
