package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller02
 */
public class Controller02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller02() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        int filas = Integer.parseInt(request.getParameter("filas"));
        int columnas = Integer.parseInt(request.getParameter("columnas"));
        if (filas > 7 || columnas > 7) {
        	response.sendRedirect("./error01.html");;
        }else {
        	PrintWriter out=response.getWriter();
        	response.setContentType("text/html;charset=UTF-8");
        	out.println("<TABLE border=1>");
        	for (int i = 1; i<=filas;i++) {
                out.println("\t\t<tr>");
                for (int j=1;j<=columnas;j++)
                    out.println("\t\t\t<td>"+((i-1)*columnas+j)+"</td>");
                out.println("\t\t</tr>");
            }
            out.println("</TABLE>");
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
