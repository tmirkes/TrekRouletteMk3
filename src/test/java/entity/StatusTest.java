package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StatusTest {

    private Status statusUnderTest;

    @BeforeEach
    void setUp() {
        statusUnderTest = new Status();
    }

    @Test
    void testEquals() {
        assertFalse(statusUnderTest.equals("o"));
    }

    @Test
    void testHashCode() {
        assertEquals(961, statusUnderTest.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Status{id=0, statusTitle='null'}", statusUnderTest.toString());
    }

    @Test
    void testAddViewsById() {
        // Setup
        View view = new View(Timestamp.from(Instant.now()), 1, 1, 1);
        List<View> testList = new ArrayList<>();

        // Run the test
        statusUnderTest.setViewsById(testList);
        statusUnderTest.addViewsById(view);

        // Verify the results
        assertEquals(1, statusUnderTest.getViewsById().size());
    }
}
