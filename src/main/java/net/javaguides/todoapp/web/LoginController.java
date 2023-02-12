package net.javaguides.todoapp.web;

import java.io.IOException;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.javaguides.todoapp.dao.UserDao;
import net.javaguides.todoapp.model.User;

import net.javaguides.todoapp.dao.LoginDao;
import net.javaguides.todoapp.model.LoginBean;


@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        authenticate(request, response);
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);

        try {
            if (loginDao.validate(loginBean)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("todo-list.jsp");
                dispatcher.forward(request, response);
            } else {
            	
            	// request.getRequestDispatcher("login.jsp");
            	// HttpSession session = request.getSession();
            	// session.setAttribute("user", username);
            	// request.setAttribute("errorMessage", "Invalid user or password");
            	// request.getRequestDispatcher("/login.jsp");

            	request.setAttribute("errorMessage", "Invalid user or password");
            	request.getRequestDispatcher("/login.jsp").forward(request, response);


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
