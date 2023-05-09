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

/**
 * UpdateEpisodeList is responsible for handling API calls to the STAPI source, retrieving all Episode entities there,
 * and comparing the retrieved list against the existing database to identify new content for addition to the database.
 *
 * @author tlmirkes
 * @version 1.0
 */
public class UpdateEpisodeList {
    private TrekDao<Episode> episodeDao = new TrekDao(Episode.class);
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private StapiRestClient stapiClient = new StapiRestClient();
    private com.cezarykluczynski.stapi.client.api.rest.Episode rawEpisodeData;
    private ArrayList<EpisodeBase> episodeList = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Main controller method for the Episode entity update process
     *
     * @return count of Episode entities added
     */
    public int processEpisodeTableUpdate() {
        ArrayList<String> episodeIds = getCurrentEpisodeIds();
        getApiEpisodeList();
        buildCompleteEpisodeList();
        return generateEpisodesFromApiResults(episodeIds);
    }

    /**
     * Construct a list of existing Episode entities by their Star Trek API identifier
     *
     * @return ArrayList of Strings
     */
    public ArrayList<String> getCurrentEpisodeIds() {
        ArrayList<Episode> currentEpisodes = (ArrayList<Episode>) episodeDao.getAll();
        ArrayList<String> currentEpisodeIds = new ArrayList<>();
        for (int i = 0; i < currentEpisodes.size(); i++) {
            currentEpisodeIds.add(currentEpisodes.get(i).getStapiEpisodeId());
        }
        return currentEpisodeIds;
    }

    /**
     * Call the Star Trek API and retrieve a complete list of all Episode entities
     */
    public void getApiEpisodeList() {
        // Get complete API episode raw response
        rawEpisodeData = stapiClient.getEpisode();
    }

    /**
     * Parse the multi-page API response and construct an ArrayList of Episode entities
     */
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

    /**
     * Compare the API Episode list to the existing Episode list and persist Episode entities for episodes not already
     * present in the database
     *
     * @param episodeIds list of Star Trek API Episode ids
     * @return count of new Episode entities persisted
     */
    public int generateEpisodesFromApiResults(ArrayList<String> episodeIds) {
        int tally = 0;
        for (int i = 0; i < episodeList.size(); i++) {
            Episode thisEpisode = buildEpisodeTestBody(i);
            if (!episodeIds.contains(thisEpisode.getStapiEpisodeId())) {
                int successValue = episodeDao.addEntity(thisEpisode);
                tally++;
            }
        }
        return tally;
    }

    /**
     * Extract Episode entities from the API return data for comparison and processing
     *
     * @param i list index of current Episode to process
     * @return Episode entity
     */
    public Episode buildEpisodeTestBody(int i) {
        EpisodeBase episodeBase = episodeList.get(i);
        List<Season> seasonMatches = seasonDao.getByPropertyEqual("stapiSeasonId", episodeBase.getSeason().getUid());
        Season thisSeason = seasonMatches.get(0);
        Episode thisEpisode = new Episode(episodeBase.getTitle(), episodeBase.getUid(), thisSeason.getId());
        thisEpisode.setSeasonBySeasonId(thisSeason);
        return thisEpisode;
    }
}
