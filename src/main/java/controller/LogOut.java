package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.PropertiesLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet(
        urlPatterns = {"/logOut"}
)

/** Begins closes a session and clears authentication using AWS Cognito
 *
 */
public class LogOut extends HttpServlet implements PropertiesLoader {
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private String logOutRedirect;
    public static String CLIENT_ID;
    public static String LOGOUT_ENDPOINT;

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    /**
     * Read in the cognito props file and get the client id and required urls
     * for authenticating a user.
     */
    // TODO This code appears in a couple classes, consider using a startup servlet similar to adv java project
    // 4 to do this work a single time and put the properties in the application scope
    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            LOGOUT_ENDPOINT = properties.getProperty("logoutURL");
            logOutRedirect = properties.getProperty("logoutRedirectURL");
        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }

    /**
     * Route to the aws-hosted cognito logout page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO if properties weren't loaded properly, route to an error page
        String url = LOGOUT_ENDPOINT + "?client_id=" + CLIENT_ID + "&logout_uri=" + logOutRedirect;
        resp.sendRedirect(url);
    }
}