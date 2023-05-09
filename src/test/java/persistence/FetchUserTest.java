package persistence;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class FetchUserTest {

    private FetchUser fetchUserUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        fetchUserUnderTest = new FetchUser();
    }

    @Test
    void testSearchForUserMatch() {
        // Setup
        HashMap<String, String> tokenDataMatch = new HashMap<>();
        tokenDataMatch.put("username", "admin");
        tokenDataMatch.put("firstname", "Alice");
        tokenDataMatch.put("lastname", "Admin");
        assertEquals(tokenDataMatch.get("username"), "admin");
        assertEquals(tokenDataMatch.get("firstname"), "Alice");
        assertEquals(tokenDataMatch.get("lastname"), "Admin");

        User expectedResult = new User("admin", "Alice", "Admin", Timestamp.from(Instant.now()));
        assertEquals(expectedResult.getUserName(), "admin");
        assertEquals(expectedResult.getFirstName(), "Alice");
        assertEquals(expectedResult.getLastName(), "Admin");

        // Run the test
        User matchResult = fetchUserUnderTest.searchForUserMatch(tokenDataMatch);
        assertEquals(matchResult.getUserName(), "admin");
        assertEquals(matchResult.getFirstName(), "Alice");
        assertEquals(matchResult.getLastName(), "Admin");

        // Verify the results
        assertEquals(expectedResult.getUserName(), matchResult.getUserName());
        assertEquals(expectedResult.getFirstName(), matchResult.getFirstName());
        assertEquals(expectedResult.getLastName(), matchResult.getLastName());

        // Setup
        HashMap<String, String> tokenDataVoid = new HashMap<>();
        tokenDataVoid.put("username", "stan");
        tokenDataVoid.put("firstname", "stan");
        tokenDataVoid.put("lastname", "man");
        assertEquals(tokenDataVoid.get("username"), "stan");
        assertEquals(tokenDataVoid.get("firstname"), "stan");
        assertEquals(tokenDataVoid.get("lastname"), "man");

        User newUser = new User("admin", "Alice", "Admin", Timestamp.from(Instant.now()));
        assertEquals(newUser.getUserName(), "admin");
        assertEquals(newUser.getFirstName(), "Alice");
        assertEquals(newUser.getLastName(), "Admin");

        // Run the test
        User voidResult = fetchUserUnderTest.searchForUserMatch(tokenDataVoid);
        assertEquals(voidResult.getUserName(), "stan");
        assertEquals(voidResult.getFirstName(), "stan");
        assertEquals(voidResult.getLastName(), "man");

        // Verify the results
        assertNotEquals(0, newUser.getUserName().compareTo(voidResult.getUserName()));
        assertNotEquals(0, newUser.getFirstName().compareTo(voidResult.getFirstName()));
        assertNotEquals(0, newUser.getLastName().compareTo(voidResult.getLastName()));
    }
}
