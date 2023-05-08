package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DatabaseSnapshotTest {
    private DatabaseSnapshot databaseSnapshotUnderTest;

    @BeforeEach
    void setUp() {
        databaseSnapshotUnderTest = new DatabaseSnapshot();
    }

    @Test
    void testBuildRandomSequence() {
        // Run the test
        databaseSnapshotUnderTest.maxCount = 25;
        ArrayList<Integer> result = databaseSnapshotUnderTest.buildRandomSequence(100);

        // Verify the results
        assertEquals(25, result.size());
        assertTrue(Collections.max(result) < 100);
        assertTrue(Collections.min(result) > 0);
    }

    @Test
    void testCheckForUniqueness() {
        assertFalse(databaseSnapshotUnderTest.checkForUniqueness(new ArrayList<>(List.of(0)), 0));
        assertTrue(databaseSnapshotUnderTest.checkForUniqueness(new ArrayList<>(List.of(1)), 2));
    }
}
