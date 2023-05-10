package controller;

import entity.Episode;
import java.util.ArrayList;

/**
 * Retrieves the API episode id from an ArrayList of Episode entities using the randomly generated number
 * at index 0 of the passed Integer ArrayList
 *
 * @author tlmirkes
 * @version 1.0
 */
public class FetchEpisodeId {

    /**
     * Extract an Episode entity from the ArrayList based on the number at index 0 in the passed Integer ArrayList
     *
     * @param episodes ArrayList of Episode entities
     * @param randomValues ArrayList of integers
     * @return API id of Episode
     */
    public String selectRandomEpisodeId(ArrayList<Episode> episodes, ArrayList<Integer> randomValues) {
        Episode selectedEpisode = episodes.get(randomValues.get(0));
        return selectedEpisode.getStapiEpisodeId();
    }
}