package persistence;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.api.dto.EpisodeSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import entity.Episode;
import entity.Season;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class UpdateEpisodeList {
    private TrekDao<Episode> episodeDao = new TrekDao(Episode.class);
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private StapiRestClient stapiClient = new StapiRestClient();
    private com.cezarykluczynski.stapi.client.api.rest.Episode rawEpisodeData;
    private ArrayList<EpisodeBase> episodeList = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    public int processEpisodeTableUpdate() {
        ArrayList<String> episodeIds = getCurrentEpisodeIds();
        getApiEpisodeList();
        buildCompleteEpisodeList();
        return generateEpisodesFromApiResults(episodeIds);
    }

    public ArrayList<String> getCurrentEpisodeIds() {
        ArrayList<Episode> currentEpisodes = (ArrayList<Episode>) episodeDao.getAll();
        ArrayList<String> currentEpisodeIds = new ArrayList<>();
        for (int i = 0; i < currentEpisodes.size(); i++) {
            currentEpisodeIds.add(currentEpisodes.get(i).getStapiEpisodeId());
        }
        return currentEpisodeIds;
    }

    public void getApiEpisodeList() {
        // Get complete API episode raw response
        rawEpisodeData = stapiClient.getEpisode();
    }

    public void buildCompleteEpisodeList() {
        EpisodeBaseResponse baseResponse;
        EpisodeSearchCriteria searchCriteria = new EpisodeSearchCriteria();
        searchCriteria.setSeasonNumberFrom(1);
        searchCriteria.setSeasonNumberTo(10);
        try {
            baseResponse = rawEpisodeData.search(searchCriteria);
            episodeList.addAll(baseResponse.getEpisodes());
            int pageCounter = 1;
            while (pageCounter < baseResponse.getPage().getTotalPages()) {
                logger.info("pageCounter = " + pageCounter);
                logger.info("total pages = " + baseResponse.getPage().getTotalPages());
                logger.info("total elements = " + baseResponse.getPage().getTotalElements());
                searchCriteria.setPageNumber(pageCounter);
                baseResponse = rawEpisodeData.search(searchCriteria);
                episodeList.addAll(baseResponse.getEpisodes());
                pageCounter++;
            }

        } catch (ApiException apie) {
            logger.error(apie);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public int generateEpisodesFromApiResults(ArrayList<String> episodeIds) {
        int tally = 0;
        for (int i = 0; i < episodeList.size(); i++) {
            Episode thisEpisode = buildEpisodeTestBody(i);
            if (!episodeIds.contains(thisEpisode.getStapiEpisodeId())) {
                logger.info("NO EPISODE FOUND.  ADD IT NOW.");
                int successValue = episodeDao.addEntity(thisEpisode);
                logger.info("Episode added.  New ID = " + successValue);
                tally++;
            }
        }
        return tally;
    }

    public Episode buildEpisodeTestBody(int i) {
        EpisodeBase episodeBase = episodeList.get(i);
        List<Season> seasonMatches = seasonDao.getByPropertyEqual("stapiSeasonId", episodeBase.getSeason().getUid());
        Season thisSeason = seasonMatches.get(0);
        Episode thisEpisode = new Episode(episodeBase.getTitle(), episodeBase.getUid(), thisSeason.getId());
        thisEpisode.setSeasonBySeasonId(thisSeason);
        //logger.info("episode " + i + ": " + episodeBase);
        logger.info("episode object = " + thisEpisode);
        logger.info("season object = " + thisSeason);
        return thisEpisode;
    }
}
