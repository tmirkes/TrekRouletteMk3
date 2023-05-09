//package persistence;
//
//import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBase;
//import entity.Season;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class UpdateSeasonListTest {
//    private UpdateSeasonList updateSeasonListUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        updateSeasonListUnderTest = new UpdateSeasonList();
//    }
//
//    @Test
//    void testProcessSeasonTableUpdate() {
//        // Setup
//        // Run the test
//        int result = updateSeasonListUnderTest.processSeasonTableUpdate();
//
//        // Verify the results
//        assertEquals(0, result);
//    }
//
//    @Test
//    void testGetCurrentSeasonIds() {
//        // Setup
//        // Run the test
//        ArrayList<String> result = updateSeasonListUnderTest.getCurrentSeasonIds();
//
//        // Verify the results
//        assertEquals(94, result.size());
//    }
//
//    @Test
//    void testGetApiSeasonList() {
//        // Setup
//        // Run the test
//        updateSeasonListUnderTest.getApiSeasonList();
//
//        // Verify the results
//    }
//
//    @Test
//    void testBuildCompleteSeasonList() {
//        // Setup
//        // Run the test
//        updateSeasonListUnderTest.buildCompleteSeasonList();
//
//        // Verify the results
//    }
//
//    @Test
//    void testGenerateSeasonsFromApiResults() {
//        // Setup
//        // Run the test
//        int result = updateSeasonListUnderTest.generateSeasonsFromApiResults(new ArrayList<>(List.of("value")));
//
//        // Verify the results
//        assertEquals(0, result);
//    }
//
//    @Test
//    void testBuildSeasonTestBody() {
//        // Setup
//        SeasonBase sampleSeasonBase = new SeasonBase();
//        sampleSeasonBase.getSeries().setTitle("Star Trek");
//        sampleSeasonBase.setSeasonNumber(1);
//        sampleSeasonBase.setUid("W00T");
//
//        ArrayList<SeasonBase> seasonBaseList = new ArrayList<>();
//        seasonBaseList.add(sampleSeasonBase);
//        seasonBaseList.add(sampleSeasonBase);
//        seasonBaseList.add(sampleSeasonBase);
//        updateSeasonListUnderTest.seasonList = new ArrayList<>(seasonBaseList);
//        Season expectedResult = new Season("Star Trek", 1, "W00T");
//
//        // Run the test
//        Season result = updateSeasonListUnderTest.buildSeasonTestBody(0);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//}
