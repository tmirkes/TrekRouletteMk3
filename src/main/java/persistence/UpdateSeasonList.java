package persistence;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.api.dto.SeasonSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.*;
import entity.Episode;
import entity.Season;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * UpdateSeasonList is responsible for handling API calls to the STAPI source, retrieving all Season entities there,
 * and comparing the retrieved list against the existing database to identify new content for addition to the database.
 *
 * @author tlmirkes
 * @version 1.0
 */
public class UpdateSeasonList {
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private StapiRestClient stapiClient = new StapiRestClient();
    private com.cezarykluczynski.stapi.client.api.rest.Season rawSeasonData;
    protected ArrayList<SeasonBase> seasonList = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Main controller method for the Season entity update process
     *
     * @return count of Season entities added
     */
    public int processSeasonTableUpdate() {
        ArrayList<String> seasonIds = getCurrentSeasonIds();
        getApiSeasonList();
        buildCompleteSeasonList();
        return generateSeasonsFromApiResults(seasonIds);
    }

    /**
     * Construct a list of existing Season entities by their Star Trek API identifier
     *
     * @return ArrayList of Strings
     */
    public ArrayList<String> getCurrentSeasonIds() {
        ArrayList<Season> currentSeasons = (ArrayList<Season>) seasonDao.getAll();
        ArrayList<String> currentSeasonIds = new ArrayList<>();
        for (int i = 0; i < currentSeasons.size(); i++) {
            currentSeasonIds.add(currentSeasons.get(i).getStapiSeasonId());
        }
        return currentSeasonIds;
    }

    /**
     * Call the Star Trek API and retrieve a complete list of all Season entities
     */
    public void getApiSeasonList() {
        // Get complete API episode raw response
        rawSeasonData = stapiClient.getSeason();
    }

    /**
     * Parse the multi-page API response and construct an ArrayList of Season entities
     */
    public void buildCompleteSeasonList() {
        SeasonBaseResponse baseResponse;
        SeasonSearchCriteria searchCriteria = new SeasonSearchCriteria();
        searchCriteria.setSeasonNumberFrom(1);
        searchCriteria.setSeasonNumberTo(10);
        try {
            baseResponse = rawSeasonData.search(searchCriteria);
            seasonList.addAll(baseResponse.getSeasons());
            searchCriteria = new SeasonSearchCriteria();
            int pageCounter = 1;
            while (pageCounter < baseResponse.getPage().getTotalPages()) {
                searchCriteria.setPageNumber(pageCounter);
                baseResponse = rawSeasonData.search(searchCriteria);
                seasonList.addAll(baseResponse.getSeasons());
                pageCounter++;
            }
        } catch (ApiException apie) {
            logger.error(apie);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Compare the API Season list to the existing Season list and persist Season entities for seasons not already
     * present in the database
     *
     * @param seasonIds list of Star Trek API Season ids
     * @return count of new Season entities persisted
     */
    public int generateSeasonsFromApiResults(ArrayList<String> seasonIds) {
        int tally = 0;
        for (int i = 0; i < seasonList.size(); i++) {
            Season thisSeason = buildSeasonTestBody(i);
            if (!seasonIds.contains(thisSeason.getStapiSeasonId())) {
                int successValue = seasonDao.addEntity(thisSeason);
                tally++;
            }
        }
        return tally;
    }

    /**
     * Extract Season entities from the API return data for comparison and processing
     *
     * @param i list index of current Season to process
     * @return Season entity
     */
    public Season buildSeasonTestBody(int i) {
        SeasonBase seasonBase = seasonList.get(i);
        Season thisSeason = new Season(seasonBase.getSeries().getTitle(), seasonBase.getSeasonNumber(), seasonBase.getUid());
        return thisSeason;
    }
}