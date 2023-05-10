package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.PropertiesLoader;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Startup loads a <code>Properties</code> file at servlet startup
 *
 * @author tlmirkes
 * @version 1.0
 */
@WebServlet(
        name="Startup",
        urlPatterns = { "/startup" },
        loadOnStartup = 1
)
public class Startup extends HttpServlet implements PropertiesLoader {
    private Properties cognitoproperties;
    private Properties dbProperties;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * On startup, load <code>Properties</code> file specified, populate
     * the <code>properties</code> instance variable and add it to the
     * <code>ServletContext</code>.
     */
    public void init() {
        dbProperties = initializeProperties("/database.properties");
        getServletContext().setAttribute("dbSettings", dbProperties);
        cognitoproperties = initializeProperties("/cognito.properties");
        getServletContext().setAttribute("cognitoProperties", cognitoproperties);
    }

    /**
     * Call the <code>loadProperties()</code> method to load and return a
     * <code>Properties</code> file to the calling method.
     *
     * @return Specified <code>Properties</code> file
     */
    public Properties initializeProperties(String propertyFile) {
        Properties properties = null;
        try {
            properties = loadProperties(propertyFile);
        } catch (IOException ioException) {
            logger.error("Error loading " + propertyFile + "; " + ioException.getMessage(), ioException);
        } catch (Exception exception) {
            logger.error("Something went wrong loading " + propertyFile + "; " + exception.getMessage(), exception);
        }
        logger.info("Properties loaded successfully: " + propertyFile);
        return properties;
    }
}