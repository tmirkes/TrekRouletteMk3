package controller;

import com.cezarykluczynski.stapi.client.api.dto.EpisodeSearchCriteria;
import com.cezarykluczynski.stapi.client.api.rest.Episode;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StapiParse {
    private Episode allEpisodes;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public StapiParse() {}

    public StapiParse(Episode allEpisodes) {
        this.allEpisodes = allEpisodes;
    }

    public EpisodeFullResponse recommendEpisode() {
        EpisodeSearchCriteria searchCriteria;
        EpisodeBaseResponse baseResponse;
        List<EpisodeBase> episodeList = new ArrayList<>();
        EpisodeFullResponse chosenEpisode = new EpisodeFullResponse();
        int index = 0;
        try {
            // Step 1: create a criteria object and configure it
            searchCriteria = new EpisodeSearchCriteria();
            searchCriteria.setSeasonNumberFrom(1);
            searchCriteria.setSeasonNumberTo(7);
            // Step 2: perform the search using the criteria object
            baseResponse = allEpisodes.search(searchCriteria);
            episodeList.addAll(baseResponse.getEpisodes());
            int episodeCount = baseResponse.getPage().getTotalElements();
            logger.info("total episodes: " + episodeCount);
            logger.info("after first page:" + episodeList.size());
            // Step 3: retrieve the episode list from the search results
            int pageCounter = 1;
            while (pageCounter < baseResponse.getPage().getTotalPages()) {
                searchCriteria.setPageNumber(pageCounter);
                baseResponse = allEpisodes.search(searchCriteria);
                episodeList.addAll(baseResponse.getEpisodes());
                pageCounter++;
            }
            logger.info("after all pages: " + episodeList.size());
            // Step 4: extract the index randomly selected
            index = selectResultIndex(episodeCount);
            logger.info("random selection: " + index);
            String queryUid = episodeList.get(index).getUid();
            logger.info("UID to search: " + queryUid);
            chosenEpisode = allEpisodes.get(queryUid);
            logger.info("episode selected: " + chosenEpisode.toString());
            // Step 5: return the episode to the servlet
        } catch (ApiException apie) {
            logger.error(apie);
        } catch (Exception e) {
            logger.error(e);
        }
        return chosenEpisode;
    }

    public int selectResultIndex(int episodeCount) {
        return (int) ThreadLocalRandom.current().nextInt(0, episodeCount + 1);
    }
}