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

@WebServlet(urlPatterns = {"/snapshot"})
public class DatabaseSnapshot extends HttpServlet {
    private final int QUEUE_SIZE = 25;
    protected int maxCount = 0;
    private int listLength = 0;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final TrekDao<Episode> episodeDao = new TrekDao<>(Episode.class);
    private final TrekDao<Season> seasonDao = new TrekDao<>(Season.class);
    private final TrekDao<View> viewDao = new TrekDao<>(View.class);
    private BuildUserOwnList builder = new BuildUserOwnList();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Season> seasonList = new ArrayList<>();
        ArrayList<Episode> collectionSpecificEpisodeList = new ArrayList<>();
        ArrayList<Episode> completeEpisodeList;
        ArrayList<Integer> randomList;
        ArrayList<Episode> viewedList;

        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("currentUser");

        completeEpisodeList = builder.getCompleteEpisodeList();
        logger.info("All episodes: " + completeEpisodeList.size());

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
            logger.info("Expected list length: " + listLength);
        }

        randomList = builder.buildRandomSequence(listLength, maxCount);

        if (currentUser != null) {
            session.setAttribute("episodeList", collectionSpecificEpisodeList);
            logger.info("SPECIFIC LIST USED.");
        } else {
            session.setAttribute("episodeList", completeEpisodeList);
            logger.info("COMPLETE LIST USED.");
        }
        session.setAttribute("randomList", randomList);

        String url = "/getEpisode";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
}