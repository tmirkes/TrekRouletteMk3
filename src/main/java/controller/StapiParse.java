package controller;

import com.cezarykluczynski.stapi.client.api.dto.EpisodeSearchCriteria;
import com.cezarykluczynski.stapi.client.api.rest.Episode;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class StapiParse {
    private Episode allEpisodes;
    private TreeMap<String, Season> currentSeasons = new TreeMap<String, Season>();
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
            int pageCounter = 0;
            while (pageCounter < baseResponse.getPage().getTotalPages()) {
                // Prepare to get the next page
                searchCriteria.setPageNumber(pageCounter);
                // Retrieve valid page results based on search criteria
                baseResponse = allEpisodes.search(searchCriteria);
                // Add valid results to episode list
                episodeList.addAll(baseResponse.getEpisodes());
                pageCounter++;
            }
            // Extract existing seasons
            buildSeasonList(baseResponse);
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

    public void buildSeasonList(EpisodeBaseResponse baseResponse) {
        Episode episode;
        int cycle = 0;
        while (cycle < baseResponse.getEpisodes().size()) {
            String series = baseResponse.getEpisodes().get(cycle).getSeries().getTitle();
            int season = baseResponse.getEpisodes().get(cycle).getSeasonNumber();
            String uid = baseResponse.getEpisodes().get(cycle).getSeason().getUid();
            episode = new Episode(series, season, uid);
            currentSeasons.put(baseResponse.getEpisodes().get(cycle).getSeason().getTitle(), episode);
            cycle++;
        }
    }
}