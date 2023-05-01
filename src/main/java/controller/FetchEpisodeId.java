package controller;

import entity.Episode;
import java.util.ArrayList;

public class FetchEpisodeId {
    public String selectRandomEpisodeId(ArrayList<Episode> episodes, ArrayList<Integer> randomValues) {
        Episode selectedEpisode = episodes.get(randomValues.get(0));
        return selectedEpisode.getStapiEpisodeId();
    }
}