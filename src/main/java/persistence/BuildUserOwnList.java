package persistence;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BuildUserOwnList {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final TrekDao<Episode> episodeDao = new TrekDao<>(Episode.class);
    private final TrekDao<Season> seasonDao = new TrekDao<>(Season.class);
    private final TrekDao<View> viewDao = new TrekDao<>(View.class);

    public ArrayList<Episode> getCompleteEpisodeList() {
        return (ArrayList<Episode>) episodeDao.getAll();
    }

    public ArrayList<Season> getOwnedSeasons(User currentUser) {
        ArrayList<Season> seasonList = new ArrayList<>();
        Set<Own> ownedSeasons = currentUser.getOwnsById();
        logger.info("Owned seasons: " + ownedSeasons.size());
        for (Own owned : ownedSeasons) {
            seasonList.add(seasonDao.getById(owned.getSeasonId()));
        }
        logger.info("Seasons: " + seasonList);
        return seasonList;
    }

    public ArrayList<Episode> getCollectionBasedEpisodes(ArrayList<Season> seasonList) {
        ArrayList<Episode> collectionSpecificEpisodeList = new ArrayList<>();
        for (Season season : seasonList) {
            collectionSpecificEpisodeList.addAll(episodeDao.getByPropertyEqual("seasonId", String.valueOf(season.getId())));
        }
        logger.info("Episodes: " + collectionSpecificEpisodeList);
        return collectionSpecificEpisodeList;
    }

    public ArrayList<Episode> getEpisodeIdsOfViewedEpisodes(User currentUser) {
        ArrayList<Episode> viewedList = new ArrayList<>();
        ArrayList<View> userViews = (ArrayList<View>) viewDao.getByPropertyEqual("userId", Integer.toString(currentUser.getId()));
        for (View view : userViews) {
            if (view.getStatusId() != 3) {
                Episode episode = episodeDao.getById(view.getEpisodeId());
                viewedList.add(episode);
            }
        }
        logger.info("Finished: " + viewedList);
        return viewedList;
    }

    public ArrayList<Episode> pareOutWatchedEpisodes(ArrayList<Episode> collection, ArrayList<Episode> viewed) {
        logger.info("collection before: " + collection.size());
        collection.removeAll(viewed);
        logger.info("viewed eps: " + viewed.size());
        logger.info("collection after: " + collection.size());
        return collection;
    }

    public ArrayList<Integer> buildRandomSequence(int maxNumber, int maxCount) {
        ArrayList<Integer> list = new ArrayList<>();
        int count = 0;
        while (count < maxCount) {
            int number = ThreadLocalRandom.current().nextInt(0, maxNumber);
            logger.info("number " + count + ": " + number);
            if (checkForUniqueness(list, number)) {
                list.add(number);
                count++;
            }
        }
        return list;
    }

    public boolean checkForUniqueness(ArrayList<Integer> theList, int currentValue) {
        boolean addValue = false;
        if (!theList.contains(currentValue)) {
            addValue = true;
        }
        return addValue;
    }
}