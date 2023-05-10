package controller;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ApiEpisodeLoader is responsible for calling the API and returning the full API response for a single episode
 * based on that episode's API id
 *
 * @author tlmirkes
 * @version 1.0
 */
public class ApiEpisodeLoader {
    private EpisodeFullResponse chosenEpisode = new EpisodeFullResponse();
    StapiRestClient stapiClient = new StapiRestClient();
    Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Call the Star Trek API and retrieve a full response for the episode ID provided
     *
     * @param episodeId API id
     * @return API full Episode response entity
     */
    public EpisodeFullResponse getEpisodeData(String episodeId) {
        try {
            chosenEpisode = stapiClient.getEpisode().get(episodeId);
        } catch (ApiException apie) {
            logger.error(apie);
        }
        return chosenEpisode;
    }
}