package controller;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.FetchUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = {"/loadCollection"}
)
public class CollectionLoader extends HttpServlet {
    private FetchUser seeker = new FetchUser();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser =(User) session.getAttribute("currentUser");
        logger.info(currentUser);


//        String findMe = (String)req.getAttribute("userName");
//        User userData = seeker.searchForUserMatch(findMe);
//        session.setAttribute("currentUser", userData);
        String url = "/collection.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
