package persistence;

import com.cezarykluczynski.stapi.client.api.StapiRestClient;
import com.cezarykluczynski.stapi.client.api.dto.SeasonSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.*;
import entity.Season;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UpdateSeasonList {
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private StapiRestClient stapiClient = new StapiRestClient();
    private com.cezarykluczynski.stapi.client.api.rest.Season rawSeasonData;
    private List<Season> currentSeasons = new ArrayList<>();
    private ArrayList<Season> formattedSeasons = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    public int processSeasonTableUpdate() {
        getCurrentSeasons();
        int newSeasons = getApiSeasonList();
        return newSeasons;
    }

    public void getCurrentSeasons() {
        currentSeasons = (ArrayList<Season>) seasonDao.getAll();
    }

    public int getApiSeasonList() {
        int tally = 0;
        // Get complete API episode raw response
        rawSeasonData = stapiClient.getSeason();
        // Create search criteria
        SeasonSearchCriteria searchCriteria;
        // Create basic episode response format
        SeasonBaseResponse baseResponse;
        // Create list to contain individual episodes
        ArrayList<SeasonBase> seasonList = new ArrayList<>();
        // Create variable to hold detailed episode data
        SeasonFullResponse completeSeason = new SeasonFullResponse();
        // Create counter to process all pages of search results
        int index = 0;
        // Use try/catch to process API call
        try {
            // Step 1: create a criteria object and configure it
            searchCriteria = new SeasonSearchCriteria();
            searchCriteria.setSeasonNumberFrom(1);
            searchCriteria.setSeasonNumberTo(10);
            // Step 2: perform the search using the criteria object
            baseResponse = rawSeasonData.search(searchCriteria);
            seasonList.addAll(baseResponse.getSeasons());
            logger.info("after first page:" + seasonList.size());
            // Step 3: retrieve the episode list from the search results
            int pageCounter = 1;
            while (pageCounter < baseResponse.getPage().getTotalPages()) {
                logger.info("pageCounter = " + pageCounter);
                logger.info("total pages = " + baseResponse.getPage().getTotalPages());
                logger.info("total elements = " + baseResponse.getPage().getTotalElements());
                // Prepare to get the next page
                searchCriteria.setPageNumber(pageCounter);
                // Retrieve valid page results based on search criteria
                baseResponse = rawSeasonData.search(searchCriteria);
                logger.info("page " + baseResponse.getPage().getPageNumber() + ": " + baseResponse.getSeasons());
                // Add valid results to episode list
                seasonList.addAll(baseResponse.getSeasons());
                pageCounter++;
            }
            logger.info("after all pages: " + seasonList.size());
            logger.info("all seasons: " + seasonList);
            tally = generateSeasonsFromApiResults(seasonList);
        } catch (ApiException apie) {
            logger.error(apie);
        } catch (Exception e) {
            logger.error(e);
        }
        return tally;
    }

    public int generateSeasonsFromApiResults(ArrayList<SeasonBase> inputSeasonData) {
        int tally = 0;
        for (int i = 0; i < inputSeasonData.size(); i++) {
            SeasonBase seasonBase = inputSeasonData.get(i);
            Season thisSeason = new Season(seasonBase.getSeries().getTitle(), seasonBase.getSeasonNumber(), seasonBase.getUid());
            logger.info("season " + i + ": " + seasonBase);
            logger.info("season object = " + thisSeason);
            ArrayList<Season> oldSeasons = (ArrayList<Season>) seasonDao.getByPropertyEqual("stapiSeasonId", thisSeason.getStapiSeasonId());
            logger.info("results of the search: " + oldSeasons);
            Season testSeason = oldSeasons.get(0);
            logger.info("searched result season: " + testSeason);
            if (seasonDao.getByPropertyEqual("stapiSeasonId", thisSeason.getStapiSeasonId()) == null) {
                logger.info("NO SEASON FOUND.  ADD IT NOW.");
                tally++;
                Season season = new Season();
                season.setSeries(inputSeasonData.get(i).getSeries().getTitle());
                season.setSeason(inputSeasonData.get(i).getSeasonNumber());
                season.setStapiSeasonId(inputSeasonData.get(i).getUid());
                int successValue = seasonDao.addEntity(season);
            }
        }
        return tally;
    }
}