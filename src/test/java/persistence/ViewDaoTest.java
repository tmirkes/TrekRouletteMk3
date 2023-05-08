package persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.*;
import util.*;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ViewDaoTest {
    TrekDao<View> viewDao; // userDao
    TrekDao<User> userDao; // ownDao
    TrekDao<Episode> episodeDao; // seasonDao
    TrekDao<Status> statusDao; // seasonDao

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        viewDao = new TrekDao(View.class);
        userDao = new TrekDao(User.class);
        episodeDao = new TrekDao(Episode.class);
        statusDao = new TrekDao(Status.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getViewById() {
        View testView = (View)viewDao.getById(1);
        assertNotNull(testView);
        assertEquals(testView, viewDao.getById(1));
    }
    @Test
    void getAllViews() {
        List<View> viewList = viewDao.getAll();
        assertNotNull(viewList);
        assertEquals(viewList, viewDao.getAll());
    }

    @Test
    void addNewView() {
        View newView = new View(new Timestamp(System.currentTimeMillis()), 4, 1, 1);
        assertNotNull(newView);
        assertEquals(4, newView.getUserId());
        assertEquals(1, newView.getEpisodeId());
        assertEquals(1, newView.getStatusId());
        newView.setUserByUserId(userDao.getById(4));
        newView.setEpisodeByEpisodeId(episodeDao.getById(1));
        newView.setStatusByStatusId(statusDao.getById(1));

        int id = viewDao.addEntity(newView);
        View insertedView = (View)viewDao.getById(id);
        logger.info(newView.toString());
        logger.info(insertedView.toString());
        assertNotNull(insertedView);
        assertEquals(4, insertedView.getUserId());
        assertEquals(1, insertedView.getEpisodeId());
        assertEquals(1, insertedView.getStatusId());
    }

    @Test
    void editExistingViewAndEpisode() {
        View original = (View)viewDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (View)viewDao.getById(1));
        assertEquals(1, original.getUserId());
        assertEquals(1, original.getEpisodeId());

        View testView = (View)viewDao.getById(1);
        assertNotNull(testView);
        assertEquals(testView,(View)viewDao.getById(1));
        assertEquals(1, testView.getUserId());
        assertEquals(1, testView.getEpisodeId());

        assertEquals(original, testView);
        testView.setEpisodeId(2);
        testView.setEpisodeByEpisodeId((Episode)episodeDao.getById(2));
        assertNotEquals(original,testView);
        viewDao.editEntity(testView);
        View editedView = (View)viewDao.getById(1);
        assertEquals(2,editedView.getEpisodeId());
    }

    @Test
    void editExistingViewAndUser() {
        View original = (View)viewDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (View)viewDao.getById(1));
        assertEquals(1, original.getUserId());
        assertEquals(1, original.getEpisodeId());

        View testView = (View)viewDao.getById(1);
        assertNotNull(testView);
        assertEquals(testView,(View)viewDao.getById(1));
        assertEquals(1, testView.getUserId());
        assertEquals(1, testView.getEpisodeId());

        assertEquals(original, testView);
        testView.setUserId(2);
        testView.setUserByUserId((User)userDao.getById(2));
        assertNotEquals(original,testView);
        viewDao.editEntity(testView);
        View editedView = (View)viewDao.getById(1);
        assertEquals(2,editedView.getUserId());
    }

    @Test
    void deleteView() {
        View testView = (View)viewDao.getById(3);
        assertNotNull(testView);
        viewDao.deleteEntity(testView);
        assertNull(viewDao.getById(3));
    }

    @Test
    void getViewByPropertyEqual() {
        List<View> viewList = viewDao.getByPropertyEqual("userId", "2");
        assertNotNull(viewList);
        assertEquals(1, viewList.size());
        View resultView = (View)viewList.get(0);
        assertNotNull(resultView);
        assertEquals(viewList, viewDao.getByPropertyEqual("userId", "2"));
        assertEquals(resultView, viewList.get(0));

        viewList = viewDao.getByPropertyEqual("episodeId", "3");
        assertNotNull(viewList);
        assertEquals(1, viewList.size());
        resultView = (View)viewList.get(0);
        assertNotNull(resultView);
        assertEquals(viewList, viewDao.getByPropertyEqual("episodeId", "3"));
        assertEquals(resultView, viewList.get(0));
    }
}