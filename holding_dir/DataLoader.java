package controller;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.DatabaseSnapshot;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DataLoader", urlPatterns = "/loadData")
public class DataLoader extends HttpServlet {
    private ArrayList<Season> currentSeasons;
    private ArrayList<Episode> currentEpisodes;
    private ArrayList<User> currentUsers;
    private ArrayList<Own> currentOwns;
    private ArrayList<View> currentViews;
    private final DatabaseSnapshot databaseSnapshot = new DatabaseSnapshot();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //currentSeasons = databaseSnapshot.getExistingSeasons();
        logger.info("seasons = " + currentSeasons.size());
        session.setAttribute("seasons", currentSeasons);
        //currentEpisodes = databaseSnapshot.getExistingEpisodes();
        logger.info("episodes = " + currentEpisodes.size());
        session.setAttribute("episodes", currentEpisodes);
        //currentUsers = databaseSnapshot.getExistingUsers();
        logger.info("users = " + currentUsers.size());
        session.setAttribute("users", currentUsers);
       // currentOwns = databaseSnapshot.getExistingOwns();
        logger.info("owns = " + currentOwns.size());
        session.setAttribute("owns", currentOwns);
       // currentViews = databaseSnapshot.getExistingViews();
        logger.info("views = " + currentViews.size());
        session.setAttribute("views", currentViews);
        String url = "/public.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}