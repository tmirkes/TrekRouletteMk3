package persistence;

import entity.Own;
import entity.Season;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * ModifyOwnership provides the direct DAO functionality to perform persistence operations with Own entities and their
 * related entity objects
 *
 * @author tlmirkes
 * @version 1.0
 */
public class ModifyOwnership {
    private final TrekDao<Own> ownDao = new TrekDao(Own.class);
    private final TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private final TrekDao<User> userDao = new TrekDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Construct and persist new Own entities associated with the authenticated user and the Season information
     * passed via the JSP data collection form
     *
     * @param seasonsToAdd ArrayList of Season identifiers
     * @param userId id of authenticated User
     */
    public void addNewSeasonsToCollection(ArrayList<String> seasonsToAdd, String userId) {
        int commit;
        for (int i = 0; i < seasonsToAdd.size(); i++) {
            int userIdInt = Integer.parseInt(userId);
            int seasonIdInt = Integer.parseInt(seasonsToAdd.get(i));
            Own newOwn = new Own();
            User currentUser = userDao.getById(userIdInt);
            Season newSeason = seasonDao.getById(seasonIdInt);
            newOwn.setSeasonBySeasonId(newSeason);
            newOwn.setUserByUserId(currentUser);
            commit = ownDao.addEntity(newOwn);
            if (commit == 0) {
                logger.error("New Own failed to write to database.");
            } else {
                logger.info("New Own created; ID = " + commit + ".");
            }
        }
    }

    /**
     * Identify and remove existing Own entities associated with the authenticated user and the Season information
     * passed via the JSP data collection form
     *
     * @param seasonsToRemove ArrayList of Season identifiers
     * @param userId id of authenticated User
     */
    public void removeOwnedSeasonsFromCollection(ArrayList<String> seasonsToRemove, String userId) {
        for (int i = 0; i < seasonsToRemove.size(); i++) {
            ArrayList<Own> ownToDelete = (ArrayList<Own>) ownDao.getByMultiplePropertyEqual("userId", userId, "seasonId", seasonsToRemove.get(i));
            Own dropThis = ownToDelete.get(0);
            ownDao.deleteEntity(dropThis);
        }
    }
}