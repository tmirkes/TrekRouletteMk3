package persistence;

import entity.Own;
import entity.Season;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ModifyOwnership {
    private final TrekDao<Own> ownDao = new TrekDao(Own.class);
    private final TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private final TrekDao<User> userDao = new TrekDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());

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

    public void removeOwnedSeasonsFromCollection(ArrayList<String> seasonsToRemove, String userId) {
        for (int i = 0; i < seasonsToRemove.size(); i++) {
            ArrayList<Own> ownToDelete = (ArrayList<Own>) ownDao.getByMultiplePropertyEqual("userId", userId, "seasonId", seasonsToRemove.get(i));
            logger.info("Owns to delete: " + ownToDelete);
            Own dropThis = ownToDelete.get(0);
            ownDao.deleteEntity(dropThis);
            logger.info("Own with ID " + dropThis.getId() + "deleted.");
        }
    }
}