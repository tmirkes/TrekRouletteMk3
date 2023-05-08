package controller;

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import entity.*;
import persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet(name = "EpisodeSelect", urlPatterns = "/getEpisode")
public class EpisodeSelect extends HttpServlet {
    View currentView = null;
    User currentUser = null;
    Episode stapiEpisode = null;

    private TrekDao<Episode> episodeDao = new TrekDao<>(Episode.class);
    private FetchEpisodeId fetcher = new FetchEpisodeId();
    private String episodeId = "";
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session
        HttpSession session = request.getSession();
        // Get lists of episodes and random numbers
        ArrayList<Episode> episodeList = (ArrayList<Episode>) session.getAttribute("episodeList");
        ArrayList<Integer> randomList = (ArrayList<Integer>) session.getAttribute("randomList");
        // Pass off lists to Fetcher object to select an episode ID
        episodeId = fetcher.selectRandomEpisodeId(episodeList, randomList);
        // Drop the first random number from randomList
        randomList.remove(0);
        // Replace the modified randomList for the old one in the session attributes
        session.setAttribute("randomList", randomList);

        // Construct a new ApiEpisodeLoader
        ApiEpisodeLoader episodeRetriever = new ApiEpisodeLoader();
        // Construct an EpisodeFullResponse to contain search results by episode ID
        EpisodeFullResponse selection = episodeRetriever.getEpisodeData(episodeId);
        // Add the EpisodeFullResponse to the session attributes
        session.setAttribute("selection", selection);

        // Search the episode database for the selected episode UID
        ArrayList<Episode> episodesByStapiId = (ArrayList<Episode>) episodeDao.getByPropertyEqual("stapiEpisodeId", episodeId);
        // Expectation is for single episode; get from first array index
        stapiEpisode = episodesByStapiId.get(0);
        // Add the Episode object to the session attributes
        session.setAttribute("episodeObject", stapiEpisode);

        // If the currentUser is not null, get the User and check for existing Views
        if (session.getAttribute("currentUser") != null) {
            currentUser = (User) session.getAttribute("currentUser");
            extractExistingViewsIfTheyExist();
        }
        // Set the existing watchState based on whether a View for this user and episode exists
        if (currentView == null) {
            // No view exists, watchState 0
            session.setAttribute("watchState", 0);
        } else if (currentView.getStatusId() == 1) {
            // View exists, in progress state, watchState 1
            session.setAttribute("watchState", 1);
        } else {
            // View exists, finished state, watchState 2
            session.setAttribute("watchState", 2);
        }

        // Set destination URL based on conditional statements
        String url = "";
        if (randomList.size() == 1) {
            // If randomList is nearly empty, regenerate it
            url = "/snapshot";
        } else if (session.getAttribute("currentUser") == null || session.getAttribute("currentUser").equals("")) {
            // If the currentUser is null or blank, go to public
            url = "/public.jsp";
        } else {
            // If the currentUser is populated, go to member
            url = "/member.jsp";
        }

        // Complete page forward
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    public View checkForViews(Set<View> viewPool, int episodeId) {
        // Create new View to contain search result
        View foundView = null;
        // For each entry in viewPool, compare DB episode ID and set to foundView if match found
        for (View oldView : viewPool) {
            if (oldView.getEpisodeId() == episodeId) {
                foundView = oldView;
            }
        }
        // Return View, set if found or null if not
        return foundView;
    }

    public void extractExistingViewsIfTheyExist() {
        // If currentUser has any views, check them for current episode
        if (!currentUser.getViewsById().isEmpty()) {
            // Get currentUser's associated views
            Set<View> userViews = currentUser.getViewsById();
            // Pass view set to be checked for matches
            currentView = checkForViews(userViews, stapiEpisode.getId());
        }
    }
}
