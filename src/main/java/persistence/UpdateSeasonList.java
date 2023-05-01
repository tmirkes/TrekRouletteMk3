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

public class UpdateSeasonList {
    private TrekDao<Season> seasonDao = new TrekDao(Season.class);
    private StapiRestClient stapiClient = new StapiRestClient();
    private com.cezarykluczynski.stapi.client.api.rest.Season rawSeasonData;
    private ArrayList<SeasonBase> seasonList = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    public int processSeasonTableUpdate() {
        ArrayList<String> seasonIds = getCurrentSeasonIds();
        getApiSeasonList();
        buildCompleteSeasonList();
        return generateSeasonsFromApiResults(seasonIds);
    }

    public ArrayList<String> getCurrentSeasonIds() {
        ArrayList<Season> currentSeasons = (ArrayList<Season>) seasonDao.getAll();
        ArrayList<String> currentSeasonIds = new ArrayList<>();
        for (int i = 0; i < currentSeasons.size(); i++) {
            currentSeasonIds.add(currentSeasons.get(i).getStapiSeasonId());
        }
        return currentSeasonIds;
    }

    public void getApiSeasonList() {
        // Get complete API episode raw response
        rawSeasonData = stapiClient.getSeason();
    }
    
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
                logger.info("pageCounter = " + pageCounter);
                logger.info("total pages = " + baseResponse.getPage().getTotalPages());
                logger.info("total elements = " + baseResponse.getPage().getTotalElements());
                searchCriteria.setPageNumber(pageCounter);
                baseResponse = rawSeasonData.search(searchCriteria);
                seasonList.addAll(baseResponse.getSeasons());
                pageCounter++;
            }
            logger.info("after all pages: " + seasonList.size());
            logger.info("all seasons: " + seasonList);
        } catch (ApiException apie) {
            logger.error(apie);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public int generateSeasonsFromApiResults(ArrayList<String> seasonIds) {
        int tally = 0;
        for (int i = 0; i < seasonList.size(); i++) {
            Season thisSeason = buildSeasonTestBody(i);
            if (!seasonIds.contains(thisSeason.getStapiSeasonId())) {
                logger.info("NO SEASON FOUND.  ADD IT NOW.");
                int successValue = seasonDao.addEntity(thisSeason);
                logger.info("Season added. New ID = " + successValue);
                tally++;
            }
        }
        return tally;
    }
    
    public Season buildSeasonTestBody(int i) {
        SeasonBase seasonBase = seasonList.get(i);
        Season thisSeason = new Season(seasonBase.getSeries().getTitle(), seasonBase.getSeasonNumber(), seasonBase.getUid());
        //logger.info("season " + i + ": " + seasonBase);
        logger.info("season object = " + thisSeason);
        logger.info("season object = " + thisSeason);
        return thisSeason;
    }
}