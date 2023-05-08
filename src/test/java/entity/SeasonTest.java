package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SeasonTest {

    private Season seasonUnderTest;

    @BeforeEach
    void setUp() {
        seasonUnderTest = new Season("series", 1, "stapiSeasonId");
    }

    @Test
    void testEquals() {
        assertFalse(seasonUnderTest.equals("o"));
    }

    @Test
    void testHashCode() {
        assertEquals(1185258222, seasonUnderTest.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Season{id=0, series='series', season=1, stapiSeasonId='stapiSeasonId'}", seasonUnderTest.toString());
    }

    @Test
    void testAddEpisodesById() {
        // Setup
        Episode episode = new Episode("title", "stapiEpisodeId", 1);
        List<Episode> testList = new ArrayList<>();

        // Run the test
        seasonUnderTest.setEpisodesById(testList);
        seasonUnderTest.addEpisodesById(episode);

        // Verify the results
        assertEquals(1, seasonUnderTest.getEpisodesById().size());
    }

    @Test
    void testAddOwnsById() {
        // Setup
        Own own = new Own(0, 0);
        List<Own> testList = new ArrayList<>();

        // Run the test
        seasonUnderTest.setOwnsById(testList);
        seasonUnderTest.addOwnsById(own);

        // Verify the results
        assertEquals(1, seasonUnderTest.getOwnsById().size());
    }
}
