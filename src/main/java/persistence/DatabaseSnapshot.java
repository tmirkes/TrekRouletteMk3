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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Season> seasonList = new ArrayList<>();
        // List of episodes available by collection
        ArrayList<Episode> collectionSpecificEpisodeList = new ArrayList<>();
        // Complete set of available episodes
        ArrayList<Episode> completeEpisodeList;
        // List of random numbers for episode selection
        ArrayList<Integer> randomList;
        // List of episodes that have been viewed already
        ArrayList<Episode> viewedList = new ArrayList<>();

        HttpSession session = request.getSession();

        // Get the current user
        User currentUser = (User) session.getAttribute("currentUser");

        // Get all episodes
        completeEpisodeList = (ArrayList<Episode>) episodeDao.getAll();
        logger.info("All episodes: " + completeEpisodeList.size());

        if (currentUser != null) {
            // Get the current user's owns
            Set<Own> ownedSeasons = currentUser.getOwnsById();
            logger.info("Owned seasons: " + ownedSeasons.size());

            // Get the current user's owned seasons
            for (Own owned : ownedSeasons) {
                seasonList.add(seasonDao.getById(owned.getSeasonId()));
            }
            logger.info("Seasons: " + seasonList);

            // Get the current user's owned seasons' episodes
            for (Season season : seasonList) {
                collectionSpecificEpisodeList.addAll(episodeDao.getByPropertyEqual("seasonId", String.valueOf(season.getId())));
            }
            logger.info("Episodes: " + collectionSpecificEpisodeList);

            // Get the episode IDs of viewed episodes
            ArrayList<View> userViews = (ArrayList<View>) viewDao.getByPropertyEqual("userId", Integer.toString(currentUser.getId()));
            for (View view : userViews) {
                if (view.getStatusId() != 3) {
                    Episode episode = (Episode) episodeDao.getById(view.getEpisodeId());
                    viewedList.add(episode);
                }
            }
            logger.info("Finished: " + viewedList);

            // Pare out viewed episodes
            logger.info("collection before: " + collectionSpecificEpisodeList.size());
            collectionSpecificEpisodeList.removeAll(viewedList);
            logger.info("viewed eps: " + viewedList.size());
            logger.info("collection after: " + collectionSpecificEpisodeList.size());

            // Set retrieval list size
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
        // Build list of random numbers
        randomList = buildRandomSequence(listLength);
        logger.info("Random list length: " + randomList.size());

        // Pick episode list based on logged in user
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

    public ArrayList<Integer> buildRandomSequence(int maxNumber) {
        ArrayList<Integer> list = new ArrayList<>();
        int count = 0;
        while (count < maxCount) {
            int number = ThreadLocalRandom.current().nextInt(0, maxNumber);
            logger.info("number " + count + ": " + number);
            if (checkForUniqueness(list, number)) {
                list.add(number);
                count++;
            }
        }
        return list;
    }

    public boolean checkForUniqueness(ArrayList<Integer> theList, int currentValue) {
        boolean addValue = false;
        if (!theList.contains(currentValue)) {
            addValue = true;
        }
        return addValue;
    }
}