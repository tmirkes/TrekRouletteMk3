package persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.*;
import util.*;

import java.sql.Timestamp;
import java.util.List;

import static javax.json.JsonValue.ValueType.NULL;
import static org.junit.jupiter.api.Assertions.*;

class OwnDaoTest {
    TrekDao<User> userDao; // userDao
    TrekDao<Own> ownDao; // seasonDao
    TrekDao<Season> seasonDao; // viewDao

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        ownDao = new TrekDao(Own.class);
        userDao = new TrekDao(User.class);
        seasonDao = new TrekDao(Season.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getOwnById() {
        Own testOwn = (Own)ownDao.getById(1);
        assertNotNull(testOwn);
        assertEquals(testOwn, ownDao.getById(1));
    }
    @Test
    void getAllOwns() {
        List<Own> ownList = ownDao.getAll();
        assertNotNull(ownList);
        assertEquals(ownList, ownDao.getAll());
    }

    @Test
    void addNewOwn() {
        Own newOwn = new Own(1, 4);
        assertNotNull(newOwn);
        assertEquals(1, newOwn.getUserId());
        assertEquals(4, newOwn.getSeasonId());
        newOwn.setUserByUserId(userDao.getById(1));
        newOwn.setSeasonBySeasonId(seasonDao.getById(4));

        int id = ownDao.addEntity(newOwn);
        Own insertedOwn = (Own)ownDao.getById(id);
        logger.info(newOwn.toString());
        logger.info(insertedOwn.toString());
        assertNotNull(insertedOwn);
        assertEquals(1, insertedOwn.getUserId());
        assertEquals(4, insertedOwn.getSeasonId());
    }

    @Test
    void editExistingOwnAndSeason() {
        Own original = (Own)ownDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (Own)ownDao.getById(1));
        assertEquals(1, original.getUserId());
        assertEquals(1, original.getSeasonId());

        Own testOwn = (Own)ownDao.getById(1);
        assertNotNull(testOwn);
        assertEquals(testOwn,(Own)ownDao.getById(1));
        assertEquals(1, testOwn.getUserId());
        assertEquals(1, testOwn.getSeasonId());

        assertEquals(original, testOwn);
        testOwn.setSeasonId(2);
        testOwn.setSeasonBySeasonId((Season)seasonDao.getById(2));
        assertNotEquals(original,testOwn);
        ownDao.editEntity(testOwn);
        Own editedOwn = (Own)ownDao.getById(1);
        assertEquals(2,editedOwn.getSeasonId());
    }

    @Test
    void editExistingOwnAndUser() {
        Own original = (Own)ownDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (Own)ownDao.getById(1));
        assertEquals(1, original.getUserId());
        assertEquals(1, original.getSeasonId());

        Own testOwn = (Own)ownDao.getById(1);
        assertNotNull(testOwn);
        assertEquals(testOwn,(Own)ownDao.getById(1));
        assertEquals(1, testOwn.getUserId());
        assertEquals(1, testOwn.getSeasonId());

        assertEquals(original, testOwn);
        testOwn.setUserId(2);
        testOwn.setUserByUserId((User)userDao.getById(2));
        assertNotEquals(original,testOwn);
        ownDao.editEntity(testOwn);
        Own editedOwn = (Own)ownDao.getById(1);
        assertEquals(2,editedOwn.getUserId());
    }

    @Test
    void deleteOwn() {
        Own testOwn = (Own)ownDao.getById(3);
        assertNotNull(testOwn);
        ownDao.deleteEntity(testOwn);
        assertNull(ownDao.getById(3));
    }

    @Test
    void getOwnByPropertyEqual() {
        List<Own> ownList = ownDao.getByPropertyEqual("userId", "2");
        assertNotNull(ownList);
        assertEquals(2, ownList.size());
        Own resultOwn = (Own)ownList.get(0);
        assertNotNull(resultOwn);
        assertEquals(ownList, ownDao.getByPropertyEqual("userId", "2"));
        assertEquals(resultOwn, ownList.get(0));

        ownList = ownDao.getByPropertyEqual("seasonId", "3");
        assertNotNull(ownList);
        assertEquals(1, ownList.size());
        resultOwn = (Own)ownList.get(0);
        assertNotNull(resultOwn);
        assertEquals(ownList, ownDao.getByPropertyEqual("seasonId", "3"));
        assertEquals(resultOwn, ownList.get(0));
    }
}