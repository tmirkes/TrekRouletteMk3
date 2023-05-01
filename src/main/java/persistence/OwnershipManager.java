package persistence;

import entity.Own;
import entity.Season;
import entity.User;
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
import java.sql.*;
import java.util.*;

@WebServlet(urlPatterns = {"/manageOwn"})
public class OwnershipManager extends HttpServlet {
    private Properties properties;
    private final TrekDao<Own> ownDao = new TrekDao(Own.class);
    private final TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private final TrekDao<User> userDao = new TrekDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        properties = (Properties) getServletContext().getAttribute("dbSettings");
        logger.info("props loaded = " + properties);


        User currentUser = (User) session.getAttribute("currentUser");
        String queryOwned = properties.getProperty("owned.query");
        logger.info("query A = " + queryOwned);
        String queryUnowned = properties.getProperty("unowned.query");
        logger.info("query B = " + queryUnowned);

        ArrayList<ArrayList<String>> ownedResults = executeDatabaseSelectStatement(queryOwned, currentUser.getId());
        ArrayList<ArrayList<String>> unownedResults = executeDatabaseSelectStatement(queryUnowned, currentUser.getId());
        session.setAttribute("owned", ownedResults);
        session.setAttribute("unowned", unownedResults);

        String url = "/collection.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] seasonChecks = request.getParameterValues("season");
        logger.info("fields = " + seasonChecks);
        ArrayList<String> selectedSeasons = new ArrayList<>();
        for (String option : seasonChecks) {
            if (option != null) {
                selectedSeasons.add(option);
            }
        }
        logger.info("fields to add = " + selectedSeasons);
        if (request.getParameter("operation").compareTo("add") == 0) {
            addNewSeasonsToCollection(selectedSeasons, request.getParameter("userIdAdd"));
        } else if (request.getParameter("operation").compareTo("remove") == 0) {
            removeOwnedSeasonsFromCollection(selectedSeasons, request.getParameter("userIdRemove"));
        } else {
            logger.error("Invalid submission: function not defined for page collection.jsp.  Returning to origin.");
        }
        doGet(request, response);
    }

    public ArrayList<ArrayList<String>> executeDatabaseSelectStatement(String query, int id) {
        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        ArrayList<String> resultArray = new ArrayList<>();
        String dbUrl = properties.getProperty("url");
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException ex) {
            logger.error("Error: unable to load driver class " + ex.getMessage(), ex);
        }
        try (Connection conn = DriverManager.getConnection(dbUrl,properties.getProperty("username"),properties.getProperty("password"))){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                logger.info("result record: " + results.getString("stapi_season_id"));
                resultArray.add(results.getString("series").substring(11));
                resultArray.add(results.getString("season"));
                resultArray.add(results.getString("id"));
                queryResult.add(resultArray);
            }
        } catch (SQLException sqlException) {
            logger.error("Database access error: " + sqlException.getMessage(), sqlException);
        } catch (Exception exception) {
            logger.error("Something went wrong: " + exception.getMessage(), exception);
        }
        return queryResult;
    }

    public void addNewSeasonsToCollection(ArrayList<String> seasonsToAdd, String userId) {
        int commit;
        int count = 0;
        while (count < seasonsToAdd.size()) {
            int userIdInt = Integer.parseInt(userId);
            int seasonIdInt = Integer.parseInt(seasonsToAdd.get(count));
            Own newOwn = new Own();
            User currentUser = userDao.getById(userIdInt);
            Season newSeason = seasonDao.getById(seasonIdInt);
            newOwn.setSeasonBySeasonId(newSeason);
            newOwn.setUserByUserId(currentUser);
            commit = ownDao.addEntity(newOwn);
            if (commit == 0) {
                logger.error("New Own failed to write to database.");
            }
            count++;
        }
    }

    public void removeOwnedSeasonsFromCollection(ArrayList<String> seasonsToRemove, String userId) {
        int count = 0;
        while (count < seasonsToRemove.size()) {
            ArrayList<Own> ownToDelete = (ArrayList<Own>) ownDao.getByMultiplePropertyEqual("userId", userId, "seasonId", seasonsToRemove.get(count));
            logger.info("Owns to delete: " + ownToDelete);
            Own dropThis = ownToDelete.get(0);
            ownDao.deleteEntity(dropThis);
            count++;
        }
    }
}