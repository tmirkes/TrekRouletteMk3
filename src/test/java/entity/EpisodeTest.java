package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EpisodeTest {

    private Episode episodeUnderTest;

    @BeforeEach
    void setUp() {
        episodeUnderTest = new Episode("title", "stapiEpisodeId", 1);
    }

    @Test
    void testEquals() {
        assertFalse(episodeUnderTest.equals("o"));
    }

    @Test
    void testHashCode() {
        assertEquals(251425565, episodeUnderTest.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Episode{id=0, title='title', stapiEpisodeId='stapiEpisodeId', seasonId=1}", episodeUnderTest.toString());
    }

    @Test
    void testAddViewsById() {
        // Setup
        View viewById = new View(Timestamp.from(Instant.now()), 0, 0, 0);
        List<View> testList = new ArrayList<>();

        // Run the test
        episodeUnderTest.setViewsById(testList);
        episodeUnderTest.addViewsById(viewById);

        // Verify the results
        assertEquals(1, episodeUnderTest.getViewsById().size());
    }
}
