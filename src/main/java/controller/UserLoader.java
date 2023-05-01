package controller;

import entity.*;
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
import java.util.HashMap;

@WebServlet(
        urlPatterns = {"/loadUser"}
)
public class UserLoader extends HttpServlet {
    private FetchUser seeker = new FetchUser();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        HashMap<String, String> findMe = (HashMap<String, String>) req.getAttribute("userInfo");
        User userData = seeker.searchForUserMatch(findMe);
        session.setAttribute("currentUser", userData);
        session.setAttribute("activeUser", "active");
        String url = "/snapshot";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(req, resp);
    }
}
