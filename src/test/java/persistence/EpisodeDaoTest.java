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

class EpisodeDaoTest {
    TrekDao<Episode> episodeDao; // genericDao
    TrekDao<Season> seasonDao; // synopsisTestingDao
    TrekDao<View> viewDao; // seriesSeasonTestingDao

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        episodeDao = new TrekDao(Episode.class);
        seasonDao = new TrekDao(Season.class);
        viewDao = new TrekDao(View.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getEpisodeById() {
        Episode testEpisode = episodeDao.getById(1);
        assertNotNull(testEpisode);
        assertEquals(testEpisode, episodeDao.getById(1));
    }

    @Test
    void getAllEpisodes() {
        List<Episode> episodeList = episodeDao.getAll();
        assertNotNull(episodeList);
        assertEquals(episodeList, episodeDao.getAll());
    }

    @Test
    void addEpisodeWithExistingSeason() {
        Season oldSeason = seasonDao.getById(1);
        assertNotNull(oldSeason);
        assertEquals(1, oldSeason.getId());

        Episode newEpisode = new Episode("Just Testing", "EPMA", 1);
        assertNotNull(newEpisode);
        assertEquals("Just Testing", newEpisode.getTitle());
        assertEquals("EPMA", newEpisode.getStapiEpisodeId());
        assertEquals(1, newEpisode.getSeasonId());

        newEpisode.setSeasonBySeasonId(oldSeason);
        assertEquals(oldSeason, newEpisode.getSeasonBySeasonId());

        oldSeason.addEpisodesById(newEpisode);
        assertTrue(oldSeason.getEpisodesById().contains(newEpisode));

        int id = episodeDao.addEntity(newEpisode);
        Episode insertedEpisode = episodeDao.getById(id);
        assertNotNull(insertedEpisode);
        assertEquals(newEpisode, insertedEpisode);
        assertEquals(oldSeason, insertedEpisode.getSeasonBySeasonId());
    }

    @Test
    void editExistingEpisode() {
        Episode original = episodeDao.getById(1);
        assertNotNull(original);
        assertEquals(original, episodeDao.getById(1));
        assertEquals(13, original.getSeasonId());

        Episode testEpisode = episodeDao.getById(1);
        assertNotNull(testEpisode);
        assertEquals(testEpisode, episodeDao.getById(1));
        assertEquals(13, testEpisode.getSeasonId());

        assertEquals(original, testEpisode);
        testEpisode.setSeasonId(2);
        testEpisode.setSeasonBySeasonId(seasonDao.getById(2));
        assertNotEquals(original,testEpisode);
        episodeDao.editEntity(testEpisode);
        Episode editedEpisode = episodeDao.getById(1);
        assertEquals(2,editedEpisode.getSeasonId());
    }

    @Test
    void deleteEpisode() {
        Episode testEpisode = (Episode) episodeDao.getById(3);
        logger.info("testEpisode: " + testEpisode);
        assertNotNull(testEpisode);
        episodeDao.deleteEntity(testEpisode);
        assertNull(episodeDao.getById(3));
    }

    @Test
    void getByPropertyEqual() {
        List<Episode> episodeList = episodeDao.getByPropertyEqual("title", "Emissary");
        assertNotNull(episodeList);
        assertEquals(1, episodeList.size());
        Episode resultEpisode = episodeList.get(0);
        assertNotNull(resultEpisode);
        assertEquals(episodeList, episodeDao.getByPropertyEqual("title", "Emissary"));
        assertEquals(resultEpisode, episodeList.get(0));

        episodeList = episodeDao.getByPropertyEqual("stapiEpisodeId", "EPMA");
        assertNotNull(episodeList);
        assertEquals(4, episodeList.size());
        resultEpisode = episodeList.get(0);
        assertNotNull(resultEpisode);
        assertEquals(episodeList, episodeDao.getByPropertyEqual("stapiEpisodeId", "EPMA"));
        assertEquals(resultEpisode, episodeList.get(0));
    }

    @Test
    void getByPropertyLike() {
        List<Episode> episodeList = episodeDao.getByPropertyLike("title", "home");
        assertNotNull(episodeList);
        assertEquals(1, episodeList.size());
        Episode resultEpisode = episodeList.get(0);
        assertNotNull(resultEpisode);
        assertEquals(resultEpisode, episodeList.get(0));

        episodeList = episodeDao.getByPropertyLike("stapiEpisodeId", "PM");
        assertNotNull(episodeList);
        assertEquals(4, episodeList.size());
        resultEpisode = episodeList.get(0);
        assertNotNull(resultEpisode);
        assertEquals(resultEpisode, episodeList.get(0));
    }
}