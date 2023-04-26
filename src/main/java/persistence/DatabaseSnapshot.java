package persistence;

import entity.*;

import java.util.ArrayList;

public class DatabaseSnapshot {
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private TrekDao<Episode> episodeDao = new TrekDao(Episode.class);
    private TrekDao<User> userDao = new TrekDao(User.class);
    private TrekDao<Own> ownDao = new TrekDao(Own.class);
    private TrekDao<View> viewDao = new TrekDao(View.class);

    public DatabaseStateCheck() {}

    public ArrayList<Season> getExistingSeasons() {
        return ArrayList<Season>(seasonDao.getAll());
    }
    public ArrayList<Episode> getExistingEpisodes() {
        return ArrayList<Episode>(episodeDao.getAll());
    }
    public ArrayList<User> getExistingUsers() {
        return ArrayList<User>(userDao.getAll());
    }
    public ArrayList<Own> getExistingOwns() {
        return ArrayList<Own>(ownDao.getAll());
    }
    public ArrayList<View> getExistingViews() {
        return ArrayList<View>(viewDao.getAll());
    }
}
