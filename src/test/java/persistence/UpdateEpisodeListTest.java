package persistence;

import entity.Episode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateEpisodeListTest {

    private UpdateEpisodeList updateEpisodeListUnderTest;

    @BeforeEach
    void setUp() {
        updateEpisodeListUnderTest = new UpdateEpisodeList();
    }

    @Test
    void testProcessEpisodeTableUpdate() {
        // Setup
        // Run the test
        final int result = updateEpisodeListUnderTest.processEpisodeTableUpdate();

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testGetCurrentEpisodeIds() {
        // Setup
        // Run the test
        final ArrayList<String> result = updateEpisodeListUnderTest.getCurrentEpisodeIds();

        // Verify the results
        assertEquals(new ArrayList<>(List.of("value")), result);
    }

    @Test
    void testGetApiEpisodeList() {
        // Setup
        // Run the test
        updateEpisodeListUnderTest.getApiEpisodeList();

        // Verify the results
    }

    @Test
    void testBuildCompleteEpisodeList() {
        // Setup
        // Run the test
        updateEpisodeListUnderTest.buildCompleteEpisodeList();

        // Verify the results
    }

    @Test
    void testGenerateEpisodesFromApiResults() {
        // Setup
        // Run the test
        final int result = updateEpisodeListUnderTest.generateEpisodesFromApiResults(new ArrayList<>(List.of("value")));

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testBuildEpisodeTestBody() {
        // Setup
        final Episode expectedResult = new Episode("title", "stapiEpisodeId", 0);

        // Run the test
        final Episode result = updateEpisodeListUnderTest.buildEpisodeTestBody(0);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
