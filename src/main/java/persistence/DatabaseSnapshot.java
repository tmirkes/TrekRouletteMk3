package persistence;

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
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(urlPatterns = {"/snapshot"})
public class DatabaseSnapshot extends HttpServlet {
    private final int QUEUE_SIZE = 25;
    private Properties properties;
    private ArrayList<String> episodeList = new ArrayList<>();
    private ArrayList<Integer> randomList;
    private int maxCount = 0;
    private String query = "";
    private boolean activeUser = false;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        properties = (Properties) getServletContext().getAttribute("dbSettings");
        User currentUser = (User) session.getAttribute("currentUser");
        logger.info("currentUser: " + currentUser);
        String dbUrl = properties.getProperty("url");

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        activeUser = checkForActiveUser(session);
        if (activeUser) {
            query = properties.getProperty("count.query.filtered");
            logger.info("FILTERED QUERY: " + query);
            buildFilteredEpisodeList(query, dbUrl, currentUser.getId());
        } else {
            query = properties.getProperty("count.query");
            logger.info("UNFILTERED QUERY: " + query);
            buildEpisodeList(query, dbUrl);
        }
        logger.info("episodes: " + episodeList);
        session.setAttribute("episodeList", episodeList);
        if (episodeList.size() < QUEUE_SIZE) {
            maxCount = episodeList.size();
        } else {
            maxCount = QUEUE_SIZE;
        }
        randomList = buildRandomSequence(episodeList.size());
        session.setAttribute("randomList", randomList);
        logger.info("numbers: " + randomList);

        String url = "/getEpisode";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    public boolean checkForActiveUser(HttpSession session) {
        boolean activeUser;
        String userValue = (String) session.getAttribute("activeUser");
        if (userValue.compareTo("inactive") == 0) {
            activeUser = false;
        } else {
            activeUser = true;
        }
        return activeUser;
    }

    public void buildFilteredEpisodeList(String query, String dbUrl, int id) {
        episodeList = new ArrayList<>();
        logger.info("FILTERED EPISODES");
        try (Connection conn = DriverManager.getConnection(dbUrl,properties.getProperty("username"),properties.getProperty("password"))){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                episodeList.add(results.getString("stapi_episode_id"));
            }
        } catch (SQLException sqlException) {
            logger.error("Database access error: " + sqlException.getMessage(), sqlException);
        } catch (Exception exception) {
            logger.error("Something went wrong: " + exception.getMessage(), exception);
        }

    }
    public void buildEpisodeList(String query, String dbUrl) {
        episodeList = new ArrayList<>();
        logger.info("UNFILTERED EPISODES");
        try (Connection conn = DriverManager.getConnection(dbUrl,properties.getProperty("username"),properties.getProperty("password"))){
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);
            while (results.next()) {
                episodeList.add(results.getString("stapi_episode_id"));
            }
        } catch (SQLException sqlException) {
            logger.error("Database access error: " + sqlException.getMessage(), sqlException);
        } catch (Exception exception) {
            logger.error("Something went wrong: " + exception.getMessage(), exception);
        }
    }

    public ArrayList<Integer> buildRandomSequence(int maxNumber) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < maxCount; i++) {
            int number = ThreadLocalRandom.current().nextInt(0, maxNumber);
            logger.info("number " + i + ": " + number);
            list.add(number);
        }
        return list;
    }
}