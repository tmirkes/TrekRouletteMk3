package persistence;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public class FetchUser {
    private final TrekDao<User> userDao = new TrekDao<>(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    public User searchForUserMatch(HashMap<String, String> tokenData) {
        String userName = tokenData.get("username");
        String firstName = tokenData.get("firstname");
        String lastName = tokenData.get("lastname");
        List<User> getUsers = userDao.getByPropertyEqual("userName", userName);
        logger.info("Oosers: " + getUsers.toString());
        User currentUser;
        if (getUsers.isEmpty()) {
            logger.info("No results found, creating new user.");
            currentUser = new User(userName, firstName, lastName, Timestamp.from(Instant.now()));
            int newId = userDao.addEntity(currentUser);
        } else {
            logger.info("User found: " + getUsers.get(0).getUserName());
            currentUser = (User)getUsers.get(0);
            currentUser.setLastLogin(Timestamp.from(Instant.now()));
            userDao.editEntity(currentUser);
        }
        return currentUser;
    }
}