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
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;


@WebServlet(urlPatterns = {"/manageViews"})
public class ViewManager extends HttpServlet {
    private User user;
    private Episode episode;
    private View view;
    private Status status;
    private final TrekDao<View> viewDao = new TrekDao(View.class);
    private final TrekDao<Episode> episodeDao = new TrekDao(Episode.class);
    private final TrekDao<Status> statusDao = new TrekDao(Status.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session
        HttpSession session = request.getSession();
        // Searchable episode ID extraction
        logger.info("form ep id: " + request.getParameter("episodeId"));
        logger.info("form user id: " + request.getParameter("userId"));
        logger.info("form view type: " + request.getParameter("viewType"));
        String episodeId = request.getParameter("episodeId");
        int episodeIdInt = Integer.parseInt(episodeId);
        // Load user from session attributes
        user = (User) session.getAttribute("currentUser");
        logger.info(user);
        // Search episode table for episodes matching episodId form field
        ArrayList<Episode> episodeSearch = (ArrayList<Episode>) episodeDao.getByPropertyEqual("id", episodeId);
        logger.info("ep id: " + episodeIdInt);
        logger.info(episodeSearch);
        // Load episode from proper source
        if (episodeSearch.isEmpty()) {
            // If episodeSearch is empty, get the actual episode
            episode = episodeDao.getById(episodeIdInt);
            logger.info("db ep: " + episode);
        } else {
            // If episodeSearch has results, get the first
            episode = episodeSearch.get(0);
            logger.info("list ep: " + episode);
        }
        // If form field viewType is set to start, add a new one
        if (((String) request.getParameter("viewType")).compareTo("start") == 0) {
            // Get status of ID 2
            status = statusDao.getById(2);
            // Start new ID for episode and user
            view = new View(Timestamp.from(Instant.now()), user.getId(), episode.getId(), status.getId());
            // Prep view for insertion to DB
            constructViewForPersistence();
            // Add view to DB
            viewDao.addEntity(view);
            // Set watchState session attribute to 1
            session.setAttribute("watchState", 1);
        } else {
            // Get status of ID 3
            status = statusDao.getById(3);
            // Get list of views for episode
            ArrayList<View> existingViews = (ArrayList<View>) viewDao.getByPropertyEqual("episodeId", episodeId);
            // Find view for current user
            for (View oldView : existingViews) {
                // If view matches user ID and status ID
                if (oldView.getUserId() == user.getId() && oldView.getStatusId() == (status.getId() - 1)) {
                    // Set view to local variable
                    view = oldView;
                }
            }
            // Prep view for update to DB
            constructViewForPersistence();
            // Update view in DB
            viewDao.editEntity(view);
            // Set watchState session attribute to 2
            session.setAttribute("watchState", 2);
        }
        // Add updated currentUser to session attribute
        session.setAttribute("currentUser", user);

        // Set URl for page forward
        String url = "/member.jsp";
        // Complete page forward
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    public void constructViewForPersistence() {
        // Add status object to view
        view.setStatusByStatusId(status);
        // Add episode object to view
        view.setEpisodeByEpisodeId(episode);
        // Add user object to view
        view.setUserByUserId(user);
        // Add view to user object
        user.addViewsById(view);
        // Add view to episode object
        episode.addViewsById(view);
    }
}