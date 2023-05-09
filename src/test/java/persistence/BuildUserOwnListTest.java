package persistence;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BuildUserOwnListTest {
    private TrekDao<User> userDao;
    private TrekDao<Season> seasonDao;
    private TrekDao<Episode> episodeDao;
    private TrekDao<View> viewDao;
    private BuildUserOwnList buildUserOwnListUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        userDao = new TrekDao(User.class);
        seasonDao = new TrekDao(Season.class);
        episodeDao = new TrekDao(Episode.class);
        viewDao = new TrekDao(View.class);
        buildUserOwnListUnderTest = new BuildUserOwnList();
    }

    @Test
    void testGetCompleteEpisodeList() {
        // Setup
        ArrayList<Episode> expectedResult = (ArrayList<Episode>) episodeDao.getAll();

        // Run the test
        ArrayList<Episode> result = buildUserOwnListUnderTest.getCompleteEpisodeList();

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetOwnedSeasons() {
        // Setup
        User currentUser = userDao.getById(1);
        ArrayList<Season> expectedResult = new ArrayList<>();
        Set<Own> ownedSeasons = currentUser.getOwnsById();
        for(Own own : ownedSeasons) {
            expectedResult.add(seasonDao.getById(own.getSeasonId()));
        }

        // Run the test
        ArrayList<Season> result = buildUserOwnListUnderTest.getOwnedSeasons(currentUser);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
        int cycle = 0;
        for(Season season : expectedResult) {
            assertEquals(expectedResult.get(cycle), result.get(cycle));
            cycle++;
        }
    }

    @Test
    void testGetCollectionBasedEpisodes() {
        // Setup
        ArrayList<Season> seasonList = new ArrayList<>();
        seasonList.add(seasonDao.getById(13));

        ArrayList<Episode> expectedResult = (ArrayList<Episode>) episodeDao.getByPropertyEqual("seasonId", "13");

        // Run the test
        ArrayList<Episode> result = buildUserOwnListUnderTest.getCollectionBasedEpisodes(seasonList);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetEpisodeIdsOfViewedEpisodes() {
        // Setup
        User currentUser = userDao.getById(1);
        ArrayList<Episode> expectedResult = new ArrayList<>();
        ArrayList<View> userViews = (ArrayList<View>) viewDao.getByPropertyEqual("userId", Integer.toString(currentUser.getId()));
        for (View view : userViews) {
            if (view.getStatusId() != 3) {
                Episode episode = episodeDao.getById(view.getEpisodeId());
                expectedResult.add(episode);
            }
        }
        // Run the test
        ArrayList<Episode> result = buildUserOwnListUnderTest.getEpisodeIdsOfViewedEpisodes(currentUser);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testPareOutWatchedEpisodes() {
        // Setup
        ArrayList<Episode> collection = (ArrayList<Episode>) episodeDao.getAll();
        ArrayList<Episode> viewed = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            viewed.add(episodeDao.getById(i));
        }
        int listSize = collection.size();
        ArrayList<Episode> expectedResult = new ArrayList<>();
        for(int i = 5; i < listSize; i++) {
            expectedResult.add(episodeDao.getById(i));
        }

        // Run the test
        ArrayList<Episode> result = buildUserOwnListUnderTest.pareOutWatchedEpisodes(collection, viewed);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    void testBuildRandomSequence() {
        // Setup
        ArrayList<Integer> expectedResult = new ArrayList<>();
        for(int i = 0; i < 25; i++) {
            expectedResult.add(i);
        }
        // Run the test
        ArrayList<Integer> result = buildUserOwnListUnderTest.buildRandomSequence(100, 25);

        // Verify the results
        assertEquals(expectedResult.size(), result.size());
    }

    @Test
    void testCheckForUniqueness() {
        assertFalse(buildUserOwnListUnderTest.checkForUniqueness(new ArrayList<>(List.of(0)), 0));
    }
}
