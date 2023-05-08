package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User("userName", "firstName", "lastName", Timestamp.from(Instant.now()));
    }

    @Test
    void testEquals() {
        // Setup
        // Run the test
        final boolean result = userUnderTest.equals("o");

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testHashCode() {
        assertEquals(-204458179, userUnderTest.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("User{id=0, userName='userName', firstName='firstName', lastName='lastName'}", userUnderTest.toString());
    }

    @Test
    void testAddOwnsById() {
        // Setup
        Own ownById = new Own(1, 1);
        Set<Own> testSet = new HashSet<>();

        // Run the test
        userUnderTest.setOwnsById(testSet);
        userUnderTest.addOwnsById(ownById);

        // Verify the results
        assertEquals(1, userUnderTest.getOwnsById().size());
    }

    @Test
    void testAddViewsById() {
        // Setup
        View viewById = new View(Timestamp.from(Instant.now()), 1, 1, 1);
        Set<View> testSet = new HashSet<>();

        // Run the test
        userUnderTest.setViewsById(testSet);
        userUnderTest.addViewsById(viewById);

        // Verify the results
        assertEquals(1, userUnderTest.getViewsById().size());
    }
}
