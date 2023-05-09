package persistence;

import entity.User;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * Process AWS authentication token data to determine the existence of user credentials in the database, and persist
 * them if the user is new or retrieve them if user already exists.
 *
 * @author tlmirkes
 * @version 1.0
 */
public class FetchUser {
    private final TrekDao<User> userDao = new TrekDao<>(User.class);

    /**
     * Extract token data and search the database for matches
     *
     * @param tokenData HashMap of AWS token claims
     * @return retrieved or constructed User entity
     */
    public User searchForUserMatch(HashMap<String, String> tokenData) {
        String userName = tokenData.get("username");
        String firstName = tokenData.get("firstname");
        String lastName = tokenData.get("lastname");
        List<User> getUsers = userDao.getByPropertyEqual("userName", userName);
        User currentUser;
        if (getUsers.isEmpty()) {
            currentUser = new User(userName, firstName, lastName, Timestamp.from(Instant.now()));
            int newId = userDao.addEntity(currentUser);
        } else {
            currentUser = (User)getUsers.get(0);
            currentUser.setLastLogin(Timestamp.from(Instant.now()));
            userDao.editEntity(currentUser);
        }
        return currentUser;
    }
}