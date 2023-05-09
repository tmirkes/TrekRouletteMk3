package controller;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.api.rest.Episode;
import com.cezarykluczynski.stapi.client.v1.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiClient;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.JSON;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFull;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import com.squareup.okhttp.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiEpisodeLoaderTest {

    private ApiEpisodeLoader apiEpisodeLoaderUnderTest;

    @BeforeEach
    void setUp() {
        apiEpisodeLoaderUnderTest = new ApiEpisodeLoader();
        apiEpisodeLoaderUnderTest.stapiClient = new StapiRestClient();
        apiEpisodeLoaderUnderTest.logger = LogManager.getLogger(this.getClass());
    }

    @Test
    void testGetEpisodeData() {
        // Setup
        Logger localLogger = LogManager.getLogger(this.getClass());
        String episodeId = "EPMA0000001002";
        StapiRestClient client = new StapiRestClient();
        EpisodeFullResponse expectedResult = new EpisodeFullResponse();
        try {
            expectedResult = client.getEpisode().get(episodeId);
        } catch (ApiException apie) {
            localLogger.error(apie);
        }

        // Run the test
        EpisodeFullResponse result = apiEpisodeLoaderUnderTest.getEpisodeData(episodeId);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
