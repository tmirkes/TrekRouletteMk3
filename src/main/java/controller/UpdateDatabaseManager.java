package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.UpdateEpisodeList;
import persistence.UpdateSeasonList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateDatabaseManager", urlPatterns = "/dbUpdate")
public class UpdateDatabaseManager extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UpdateSeasonList seasons = new UpdateSeasonList();
        logger.info("Season update created");
        int newSeasons = seasons.processSeasonTableUpdate();
        logger.info("Season update called and returned");
        UpdateEpisodeList episodes = new UpdateEpisodeList();
        logger.info("Episode update created");
        int newEpisodes = episodes.processEpisodeTableUpdate();
        logger.info("Episode update called and returned");
        String updateMessage = newEpisodes + " new episodes added, " + newSeasons + " new seasons added.";
        logger.info("Message created");
        session.setAttribute("updateStatus", updateMessage);
        logger.info("Message added to session");
        String url = "/update.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
