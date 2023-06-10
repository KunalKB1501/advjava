package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;
import pojos.User;

/**
 * Servlet implementation class Registration
 */

@WebServlet(value = "/reg", loadOnStartup = 2)
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userdao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		try {
			userdao = new UserDaoImpl();
		} catch (SQLException e) {
			throw new ServletException("in init method " + getClass(), e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			userdao.cleanUp();
		} catch (Exception e) {
			System.out.println("in destroy method " + getClass() + " " + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try(PrintWriter pw=response.getWriter())
		{
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String email = request.getParameter("email");
			String pwd = request.getParameter("pass");
			
			
			LocalDate dob1=LocalDate.parse(request.getParameter("dob"));
			LocalDate dob2=userdao.validatedate(dob1);
			Date dob=Date.valueOf(dob2);
			String role = request.getParameter("role");
			
			try {
				if(dob!=null)
				{
				int result=userdao.newregisterUser(fname, lname, email, pwd, dob, false, role);
				if(result==0)
				{
					pw.print("<h4>Data not Inserted</h4>");
				}
				else
				{
				response.sendRedirect("login.html");
				}
				}
				else
					response.sendRedirect("age");
			} catch (Exception e) {
			throw new ServletException("data not inserted ", e);
			}
			
			
			
		}
	}

}
