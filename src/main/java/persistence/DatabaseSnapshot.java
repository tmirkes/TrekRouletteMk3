package persistence;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * DatabaseSnapshot is responsible for generating ArrayLists representing current values of the database at the time
 * the class is called.
 *
 * @author tlmirkes
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/snapshot"})
public class DatabaseSnapshot extends HttpServlet {
    private final int QUEUE_SIZE = 25;
    protected int maxCount = 0;
    private int listLength = 0;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private BuildUserOwnList builder = new BuildUserOwnList();

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
        ArrayList<Season> seasonList;
        ArrayList<Episode> collectionSpecificEpisodeList = new ArrayList<>();
        ArrayList<Episode> completeEpisodeList;
        ArrayList<Integer> randomList;
        ArrayList<Episode> viewedList;

        // Get Session object
        HttpSession session = request.getSession();
        // Load current User object from session attributes
        User currentUser = (User) session.getAttribute("currentUser");

        // Get the complete owned Season list
        completeEpisodeList = builder.getCompleteEpisodeList();

        // Filter the episode list and determine the proper queue list length
        if (currentUser != null) {
            seasonList = builder.getOwnedSeasons(currentUser);
            collectionSpecificEpisodeList = builder.getCollectionBasedEpisodes(seasonList);
            viewedList = builder.getEpisodeIdsOfViewedEpisodes(currentUser);
            collectionSpecificEpisodeList = builder.pareOutWatchedEpisodes(collectionSpecificEpisodeList, viewedList);

            if (collectionSpecificEpisodeList.size() < QUEUE_SIZE) {
                maxCount = collectionSpecificEpisodeList.size();
                listLength = maxCount;
            } else {
                maxCount = QUEUE_SIZE;
                listLength = collectionSpecificEpisodeList.size();
            }
        } else {
            listLength = completeEpisodeList.size();
            maxCount = QUEUE_SIZE;
        }

        // Generate a list of random numbers
        randomList = builder.buildRandomSequence(listLength, maxCount);

        // Determine if complete episode list or user-specific list is to be used
        if (currentUser != null) {
            session.setAttribute("episodeList", collectionSpecificEpisodeList);
        } else {
            session.setAttribute("episodeList", completeEpisodeList);
        }
        session.setAttribute("randomList", randomList);

        String url = "/getEpisode";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}