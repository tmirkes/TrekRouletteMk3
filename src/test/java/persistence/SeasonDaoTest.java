package persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.*;
import util.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeasonDaoTest {
    TrekDao<Season> seasonDao; // seasonDao
    TrekDao<Episode> episodeDao; // episodeDao
    TrekDao<Own> ownDao; // ownDao

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        seasonDao = new TrekDao(Season.class);
        episodeDao = new TrekDao(Episode.class);
        ownDao = new TrekDao(Own.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        database.runSQL("TRTestData.sql");
    }

    @Test
    void getSeasonById() {
        Season testSeason = (Season)seasonDao.getById(1);
        assertNotNull(testSeason);
        assertEquals(testSeason, seasonDao.getById(1));
    }
    @Test
    void getAllSeasons() {
        List<Season> seasonList = seasonDao.getAll();
        assertNotNull(seasonList);
        assertEquals(seasonList, seasonDao.getAll());
    }
    @Test
    void addSeason() {
        Season newSeason = new Season("Just Testing", 1, "TEST");
        assertNotNull(newSeason);
        assertEquals("Just Testing", newSeason.getSeries());
        assertEquals("TEST", newSeason.getStapiSeasonId());
        assertEquals(1, newSeason.getSeason());

        int id = seasonDao.addEntity(newSeason);
        Season insertedSeason = (Season)seasonDao.getById(id);
        assertNotNull(insertedSeason);
        assertEquals(newSeason.getSeries(), insertedSeason.getSeries());
        assertEquals(newSeason.getStapiSeasonId(), insertedSeason.getStapiSeasonId());
        assertEquals(newSeason.getSeason(), insertedSeason.getSeason());
    }
    @Test
    void editExistingSeason() {
        Season original = (Season)seasonDao.getById(1);
        assertNotNull(original);
        assertEquals(original, (Season)seasonDao.getById(1));
        assertEquals("Star Trek: The Original Series", original.getSeries());
        assertEquals(1, original.getSeason());
        assertEquals("SEMA", original.getStapiSeasonId());

        Season testSeason = (Season)seasonDao.getById(1);
        assertNotNull(testSeason);
        assertEquals(testSeason, (Season)seasonDao.getById(1));
        assertEquals("Star Trek: The Original Series", testSeason.getSeries());
        assertEquals(1, original.getSeason());
        assertEquals("SEMA", testSeason.getStapiSeasonId());

        assertEquals(original, testSeason);

        testSeason.setSeason(4);
        assertNotEquals(original,testSeason);
        seasonDao.editEntity(testSeason);

        Season editedSeason = (Season)seasonDao.getById(1);
        assertEquals(4,editedSeason.getSeason());
    }

    @Test
    void deleteSeason() {
        Season testSeason = (Season)seasonDao.getById(3);
        assertNotNull(testSeason);
        Own testOwn = (Own)ownDao.getById(3);
        ownDao.deleteEntity(testOwn);
        seasonDao.deleteEntity(testSeason);
        assertNull(seasonDao.getById(3));
    }

    @Test
    void getByPropertyEqual() {
        List<Season> seasonList = seasonDao.getByPropertyEqual("series", "Star Trek: Discovery");
        assertNotNull(seasonList);
        assertEquals(5, seasonList.size());
        Season resultSeason = (Season)seasonList.get(0);
        assertNotNull(resultSeason);
        assertEquals(seasonList, seasonDao.getByPropertyEqual("series", "Star Trek: Discovery"));
        assertEquals(resultSeason, seasonList.get(0));

        seasonList = seasonDao.getByPropertyEqual("season", "1");
        assertNotNull(seasonList);
        assertEquals(11, seasonList.size());
        resultSeason = (Season)seasonList.get(0);
        assertNotNull(resultSeason);
        assertEquals(seasonList, seasonDao.getByPropertyEqual("season", "1"));
        assertEquals(resultSeason, seasonList.get(0));

        seasonList = seasonDao.getByPropertyEqual("stapiSeasonId", "SEMA");
        assertNotNull(seasonList);
        assertEquals(46, seasonList.size());
        resultSeason = (Season)seasonList.get(0);
        assertNotNull(resultSeason);
        assertEquals(seasonList, seasonDao.getByPropertyEqual("stapiSeasonId", "SEMA"));
        assertEquals(resultSeason, seasonList.get(0));
    }

    @Test
    void getByPropertyLike() {
        List<Season> seasonList = seasonDao.getByPropertyLike("series", "The");
        assertNotNull(seasonList);
        assertEquals(12, seasonList.size());
        Season resultSeason = (Season)seasonList.get(0);
        assertNotNull(resultSeason);
        assertEquals(resultSeason, seasonList.get(0));

        seasonList = seasonDao.getByPropertyLike("stapiSeasonId", "EM");
        assertNotNull(seasonList);
        assertEquals(46, seasonList.size());
        resultSeason = (Season)seasonList.get(0);
        assertNotNull(resultSeason);
        assertEquals(resultSeason, seasonList.get(0));
    }
}