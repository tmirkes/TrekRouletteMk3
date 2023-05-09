package controller;

import entity.Episode;
import persistence.TrekDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testng.AssertJUnit.assertTrue;

class FetchEpisodeIdTest {
    private TrekDao<Episode> episodeDao;
    private FetchEpisodeId fetchEpisodeIdUnderTest;

    @BeforeEach
    void setUp() {
        episodeDao = new TrekDao(Episode.class);
        fetchEpisodeIdUnderTest = new FetchEpisodeId();
    }

    @Test
    void testSelectRandomEpisodeId() {
        // Setup
        ArrayList<Episode> episodes = new ArrayList<>();
        for(int i = 1; i < 5; i++) {
            episodes.add(episodeDao.getById(i));
        }
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i = 1; i < 5; i++) {
            integers.add(i);
        }

        // Run the test
        String result = fetchEpisodeIdUnderTest.selectRandomEpisodeId(episodes, integers);

        // Verify the results
        assertEquals(4, episodes.size());
        assertEquals(4, integers.size());
        assertNotEquals(0, result.length());
        assertNotEquals("", result);
        assertTrue(testResultString(result, episodes));
    }

    public boolean testResultString(String result, ArrayList<Episode> episodes) {
        boolean check = false;
        for(int i = 0; i < episodes.size(); i++) {
            if (episodes.get(i).getStapiEpisodeId().compareTo(result) == 0) {
                check = true;
            }
        }
        return check;
    }
}
