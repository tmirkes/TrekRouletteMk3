package controller;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiEpisodeLoader {
    private EpisodeFullResponse chosenEpisode = new EpisodeFullResponse();
    private StapiRestClient stapiClient = new StapiRestClient();
    private final Logger logger = LogManager.getLogger(this.getClass());

    public EpisodeFullResponse getEpisodeData(String episodeId) {
        try {
            chosenEpisode = stapiClient.getEpisode().get(episodeId);
        } catch (ApiException apie) {
            logger.error(apie);
        }
        return chosenEpisode;
    }
}