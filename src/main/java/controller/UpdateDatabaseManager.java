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

/**
 * UpdateDatabaseManager controls the functionality of the UpdateSeasonList and UpdateEpisodeList classes
 *
 * @author tlmirkes
 * @version 1.0
 */
@WebServlet(name = "UpdateDatabaseManager", urlPatterns = "/dbUpdate")
public class UpdateDatabaseManager extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles HTTP GET requests.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @exception ServletException if there is a Servlet failure
     * @exception IOException if there is an IO failure
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UpdateSeasonList seasons = new UpdateSeasonList();
        int newSeasons = seasons.processSeasonTableUpdate();
        UpdateEpisodeList episodes = new UpdateEpisodeList();
        int newEpisodes = episodes.processEpisodeTableUpdate();
        String updateMessage = newEpisodes + " new episodes added, " + newSeasons + " new seasons added.";
        session.setAttribute("updateStatus", updateMessage);
        String url = "/update.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
