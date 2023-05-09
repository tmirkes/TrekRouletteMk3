package persistence;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * BuildUserOwnList provides functionality for the construction of a registered user's list of owned seasons and
 * their associated episodes
 *
 * @author tlmirkes
 * @version 1.0
 */
public class BuildUserOwnList {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final TrekDao<Episode> episodeDao = new TrekDao<>(Episode.class);
    private final TrekDao<Season> seasonDao = new TrekDao<>(Season.class);
    private final TrekDao<View> viewDao = new TrekDao<>(View.class);

    /**
     * Query the database and retrieve all episodes as Episode entities
     *
     * @return ArrayList of Episode entities
     */
    public ArrayList<Episode> getCompleteEpisodeList() {
        return (ArrayList<Episode>) episodeDao.getAll();
    }

    /**
     * Extract the Seasons owned by the current User as Season entities
     *
     * @param currentUser User entity representing authenticated user
     * @return ArrayList of Season entities
     */
    public ArrayList<Season> getOwnedSeasons(User currentUser) {
        ArrayList<Season> seasonList = new ArrayList<>();
        Set<Own> ownedSeasons = currentUser.getOwnsById();
        for (Own owned : ownedSeasons) {
            seasonList.add(seasonDao.getById(owned.getSeasonId()));
        }
        return seasonList;
    }

    /**
     * Query the database and retrieve all episodes as Episode entities that are associated with the extracted owned
     * Season entities
     *
     * @param seasonList Season entities representing owned seasons
     * @return ArrayList of Episode entities
     */
    public ArrayList<Episode> getCollectionBasedEpisodes(ArrayList<Season> seasonList) {
        ArrayList<Episode> collectionSpecificEpisodeList = new ArrayList<>();
        for (Season season : seasonList) {
            collectionSpecificEpisodeList.addAll(episodeDao.getByPropertyEqual("seasonId", String.valueOf(season.getId())));
        }
        return collectionSpecificEpisodeList;
    }

    /**
     * Query the database and retrieve all episodes as Episode entities that are associated with the extracted owned
     * Season entities and have been marked as viewed by the User
     *
     * @param currentUser User entity representing authenticated user
     * @return ArrayList of Episode entities
     */
    public ArrayList<Episode> getEpisodeIdsOfViewedEpisodes(User currentUser) {
        ArrayList<Episode> viewedList = new ArrayList<>();
        ArrayList<View> userViews = (ArrayList<View>) viewDao.getByPropertyEqual("userId", Integer.toString(currentUser.getId()));
        for (View view : userViews) {
            if (view.getStatusId() != 3) {
                Episode episode = episodeDao.getById(view.getEpisodeId());
                viewedList.add(episode);
            }
        }
        return viewedList;
    }

    /**
     * Remove watched Episode entities from the recommendation list
     *
     * @param collection ArrayList of Episode entities
     * @param viewed ArrayList of Episode entities marked as viewed
     * @return ArrayList of Episode entities
     */
    public ArrayList<Episode> pareOutWatchedEpisodes(ArrayList<Episode> collection, ArrayList<Episode> viewed) {
        collection.removeAll(viewed);
        return collection;
    }

    /**
     * Generate a list of randomly determined integers between 0 and <code>maxNumber</code>, containing <code>maxCount</code>
     * entries
     *
     * @param maxNumber upper bound of generated integers
     * @param maxCount desired count of generated integers
     * @return ArrayList of integers
     */
    public ArrayList<Integer> buildRandomSequence(int maxNumber, int maxCount) {
        ArrayList<Integer> list = new ArrayList<>();
        int count = 0;
        while (count < maxCount) {
            int number = ThreadLocalRandom.current().nextInt(0, maxNumber);
            if (checkForUniqueness(list, number)) {
                list.add(number);
                count++;
            }
        }
        return list;
    }

    /**
     * Validate generated numbers are unique within the integer list
     *
     * @param theList generated integer ArrayList
     * @param currentValue value to be searched for within <code>theList</code>
     * @return true/false result of uniqueness
     */
    public boolean checkForUniqueness(ArrayList<Integer> theList, int currentValue) {
        boolean addValue = false;
        if (!theList.contains(currentValue)) {
            addValue = true;
        }
        return addValue;
    }
}