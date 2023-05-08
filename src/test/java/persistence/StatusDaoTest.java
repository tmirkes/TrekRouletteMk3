package persistence;

import entity.*;
import util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatusDaoTest {
    TrekDao<Status> statusDao; // genericDao
    TrekDao<View> viewDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        statusDao = new TrekDao(Status.class);
        viewDao = new TrekDao(View.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getStatusById() {
        Status testStatus = statusDao.getById(1);
        assertNotNull(testStatus);
        assertEquals(testStatus, statusDao.getById(1));
    }

    @Test
    void getAllStatuss() {
        List<Status> statusList = statusDao.getAll();
        assertNotNull(statusList);
        assertEquals(statusList, statusDao.getAll());
    }

    @Test
    void editExistingStatus() {
        Status original = statusDao.getById(1);
        assertNotNull(original);
        assertEquals(original, statusDao.getById(1));

        Status testStatus = statusDao.getById(1);
        assertNotNull(testStatus);
        assertEquals(testStatus, statusDao.getById(1));

        assertEquals(original, testStatus);
        testStatus.setStatusTitle("NOOOOOO!");
        assertNotEquals(original,testStatus);
        statusDao.editEntity(testStatus);
        Status editedStatus = statusDao.getById(1);
        assertEquals("NOOOOOO!",editedStatus.getStatusTitle());
    }

    @Test
    void deleteStatus() {
        Status testStatus = (Status) statusDao.getById(3);
        logger.info("testStatus: " + testStatus);
        assertNotNull(testStatus);
        View testView = viewDao.getById(4);
        viewDao.deleteEntity(testView);
        statusDao.deleteEntity(testStatus);
        assertNull(statusDao.getById(3));
    }

    @Test
    void getByPropertyEqual() {
        List<Status> statusList = statusDao.getByPropertyEqual("statusTitle", "Complete");
        assertNotNull(statusList);
        assertEquals(1, statusList.size());
        Status resultStatus = statusList.get(0);
        assertNotNull(resultStatus);
        assertEquals(statusList, statusDao.getByPropertyEqual("statusTitle", "Complete"));
        assertEquals(resultStatus, statusList.get(0));
    }

    @Test
    void getByPropertyLike() {
        List<Status> statusList = statusDao.getByPropertyLike("statusTitle", "In");
        assertNotNull(statusList);
        assertEquals(1, statusList.size());
        Status resultStatus = statusList.get(0);
        assertNotNull(resultStatus);
        assertEquals(resultStatus, statusList.get(0));
    }
}