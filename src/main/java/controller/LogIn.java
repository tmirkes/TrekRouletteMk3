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

/**
 * Manages Amazon Web Service log out endpoint calls
 */
@WebServlet(urlPatterns = {"/logIn"})

/** Begins the authentication process using AWS Cognito
 *
 */
public class LogIn extends HttpServlet implements PropertiesLoader {
    Properties properties;
    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    /**
     * Read in the cognito props file and get the client id and required urls
     * for authenticating a user.
     */
    private void loadProperties() {
        properties = (Properties) getServletContext().getAttribute("cognitoProperties");
        CLIENT_ID = properties.getProperty("client.id");
        LOGIN_URL = properties.getProperty("loginURL");
        REDIRECT_URL = properties.getProperty("redirectURL");
    }

    /**
     * Route to the aws-hosted cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
        resp.sendRedirect(url);
    }
}
