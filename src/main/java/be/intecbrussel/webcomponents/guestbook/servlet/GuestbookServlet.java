package be.intecbrussel.webcomponents.guestbook.servlet;

import be.intecbrussel.webcomponents.guestbook.dao.GuestbookDao;
import be.intecbrussel.webcomponents.guestbook.domain.GuestbookBean;

import javax.ejb.Local;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet()
public class GuestbookServlet extends HttpServlet {
GuestbookDao guestbookDao;
    @Override
    public void init() throws ServletException {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            guestbookDao = new GuestbookDao(getInitParameter("url")
            ,getInitParameter("login")
            ,getInitParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>helloworldServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Messages</h1>");
            for (GuestbookBean message: guestbookDao.getAllMessages()){
                out.println("<p>"+message+"</p>");
            }
            out.println("<form method='post' action=''>");
            out.println("<p>please enter name:</p>\n" +
                    "name: <input type=\"text\" name=\"name\">\n" +
                    "message: <input type=\"textarea\" name=\"message\">\n" +
                    "    <input type=\"submit\" value=\"okidoki!\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(IOException ex){
            System.out.println("exception during writing procedure");
            throw ex;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuestbookBean message = new GuestbookBean(LocalDateTime.now()
                ,req.getParameter("name")
                ,req.getParameter("message"));
        guestbookDao.createMessage(message);
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    public void destroy() {
        try {
            guestbookDao.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
