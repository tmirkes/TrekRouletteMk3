package persistence;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FetchUser {
    private final TrekDao<User> userDao = new TrekDao<>(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    public User searchForUserMatch(String userName) {
        List<User> getUsers = userDao.getByPropertyEqual("userName", userName);
        User currentUser;
        if (getUsers.isEmpty()) {
            logger.info("No results found, creating new user.");
            currentUser = new User();
            currentUser.setUserName(userName);
        } else {
            currentUser = (User)getUsers.get(0);
        }
        return currentUser;
    }
}