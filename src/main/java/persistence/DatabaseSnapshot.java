package persistence;

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
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        properties = (Properties) getServletContext().getAttribute("dbSettings");
        ArrayList<String> episodeList = new ArrayList<>();
        ArrayList<Integer> randomList;

        String query = properties.getProperty("countquery");
        String dbUrl = properties.getProperty("url");
        logger.info("query = " + query);
        logger.info("dbUrl = " + dbUrl);
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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
        logger.info("episodes: " + episodeList);
        session.setAttribute("episodeList", episodeList);

        randomList = buildRandomSequence(episodeList.size());
        session.setAttribute("randomList", randomList);
        logger.info("numbers: " + randomList);

        String url = "/getEpisode";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private ArrayList<Integer> buildRandomSequence(int maxNumber) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < QUEUE_SIZE; i++) {
            int number = ThreadLocalRandom.current().nextInt(0, maxNumber);
            logger.info("number " + i + ": " + number);
            list.add(number);
        }
        return list;
    }
}