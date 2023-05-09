package persistence;

import entity.Own;
import entity.Season;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.SeasonComparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/manageOwn"})
public class OwnershipManager extends HttpServlet {
    private final TrekDao<Own> ownDao = new TrekDao(Own.class);
    private final TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private final TrekDao<User> userDao = new TrekDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ModifyOwnership editOwns = new ModifyOwnership();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User currentUser = (User) session.getAttribute("currentUser");
        Set<Own> ownedSet = currentUser.getOwnsById();

        List<Season> owned = new ArrayList<>();
        for (Own own : ownedSet) {
            owned.add(seasonDao.getById(own.getSeasonId()));
        }
        Collections.sort(owned, new SeasonComparator());

        ArrayList<Season> unowned = (ArrayList<Season>) seasonDao.getAll();
        unowned.removeIf(season -> owned.contains(season));
        session.setAttribute("owned", owned);
        session.setAttribute("unowned", unowned);

        String url = "/collection.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String[] seasonChecks = request.getParameterValues("season");
        logger.info("fields = " + seasonChecks);
        ArrayList<String> selectedSeasons = new ArrayList<>(Arrays.asList(seasonChecks));
        logger.info("fields to add = " + selectedSeasons);
        if (request.getParameter("operation").compareTo("add") == 0) {
            editOwns.addNewSeasonsToCollection(selectedSeasons, request.getParameter("userIdAdd"));
        } else if (request.getParameter("operation").compareTo("remove") == 0) {
            editOwns.removeOwnedSeasonsFromCollection(selectedSeasons, request.getParameter("userIdRemove"));
        } else {
            logger.error("Invalid submission: function not defined for page collection.jsp.  Returning to origin.");
        }
        User updatedUser = userDao.getById(currentUser.getId());
        session.setAttribute("currentUser", updatedUser);
        doGet(request, response);
    }
}