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

class UserDaoTest {
    TrekDao<User> userDao; // episodeDao
    TrekDao<Own> ownDao; // seasonDao
    TrekDao<View> viewDao; // viewDao

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        userDao = new TrekDao(User.class);
        ownDao = new TrekDao(Own.class);
        viewDao = new TrekDao(View.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getUserById() {
        User testUser = (User)userDao.getById(1);
        assertNotNull(testUser);
        assertEquals(testUser, userDao.getById(1));
    }
    @Test
    void getAllUsers() {
        List<User> userList = userDao.getAll();
        assertNotNull(userList);
        assertEquals(userList, userDao.getAll());
    }

    @Test
    void addNewUser() {
        User newUser = new User("eadmin", "Erin", "Admin", new Timestamp(System.currentTimeMillis()));
        assertNotNull(newUser);
        assertEquals("eadmin", newUser.getUserName());
        assertEquals("Erin", newUser.getFirstName());
        assertEquals("Admin", newUser.getLastName());

        int id = userDao.addEntity(newUser);
        User insertedUser = (User)userDao.getById(id);
        logger.info(newUser.toString());
        logger.info(insertedUser.toString());
        assertNotNull(insertedUser);
        assertEquals("eadmin", insertedUser.getUserName());
        assertEquals("Erin", insertedUser.getFirstName());
        assertEquals("Admin", insertedUser.getLastName());
    }

    @Test
    void editExistingUser() {
        User original = (User)userDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (User)userDao.getById(1));
        assertEquals("admin", original.getUserName());
        assertEquals("Alice", original.getFirstName());
        assertEquals("Admin", original.getLastName());

        User testUser = (User)userDao.getById(1);
        assertNotNull(testUser);
        assertEquals(testUser,(User)userDao.getById(1));
        assertEquals("admin", testUser.getUserName());
        assertEquals("Alice", testUser.getFirstName());
        assertEquals("Admin", testUser.getLastName());

        assertEquals(original, testUser);
        testUser.setUserName("WackyWaldo");
        assertNotEquals(original,testUser);
        userDao.editEntity(testUser);
        User editedUser = (User)userDao.getById(1);
        assertEquals("WackyWaldo",editedUser.getUserName());
    }

    @Test
    void deleteUser() {
        User testUser = (User)userDao.getById(3);
        assertNotNull(testUser);
        userDao.deleteEntity(testUser);
        assertNull(userDao.getById(3));
    }

    @Test
    void getByPropertyEqual() {
        List<User> userList = userDao.getByPropertyEqual("userName", "admin");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        User resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(userList, userDao.getByPropertyEqual("userName", "admin"));
        assertEquals(resultUser, userList.get(0));

        userList = userDao.getByPropertyEqual("firstName", "Bob");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(userList, userDao.getByPropertyEqual("firstName", "Bob"));
        assertEquals(resultUser, userList.get(0));

        userList = userDao.getByPropertyEqual("lastName", "Admin");
        assertNotNull(userList);
        assertEquals(4, userList.size());
        resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(userList, userDao.getByPropertyEqual("lastName", "Admin"));
        assertEquals(resultUser, userList.get(0));
    }

    @Test
    void getByPropertyLike() {
        List<User> userList = userDao.getByPropertyLike("userName", "cad");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        User resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(resultUser, userList.get(0));

        userList = userDao.getByPropertyLike("firstName", "ic");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(resultUser, userList.get(0));

        userList = userDao.getByPropertyLike("lastName", "mi");
        assertNotNull(userList);
        assertEquals(4, userList.size());
        resultUser = (User)userList.get(0);
        assertNotNull(resultUser);
        assertEquals(resultUser, userList.get(0));
    }
}