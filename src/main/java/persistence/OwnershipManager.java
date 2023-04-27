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
        String queryOwned = properties.getProperty("ownedquery");
        logger.info("query A = " + queryOwned);
        String queryUnowned = properties.getProperty("unownedquery");
        logger.info("query B = " + queryUnowned);

        TreeSet<String> ownedResults = executeDatabaseSelectStatement(queryOwned, currentUser.getId());
        TreeSet<String> unownedResults = executeDatabaseSelectStatement(queryUnowned, currentUser.getId());
        session.setAttribute("owned", ownedResults);
        session.setAttribute("unowned", unownedResults);

        String url = "/collection.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        doGet(request, response);
    }

    public TreeSet<String> executeDatabaseSelectStatement(String query, int id) {
        TreeSet<String> queryResult = new TreeSet<>();
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
            //results.beforeFirst();
            while (results.next()) {
                logger.info("result record: " + results.getString("stapi_season_id"));
                queryResult.add(results.getString("stapi_season_id"));
            }
        } catch (SQLException sqlException) {
            logger.error("Database access error: " + sqlException.getMessage(), sqlException);
        } catch (Exception exception) {
            logger.error("Something went wrong: " + exception.getMessage(), exception);
        }
        return queryResult;
    }
}