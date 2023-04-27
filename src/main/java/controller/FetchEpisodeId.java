package controller;

import java.util.ArrayList;

public class FetchEpisodeId {
    public String selectRandomEpisodeId(ArrayList<String> episodes, ArrayList<Integer> randomValues) {
        return (String) episodes.get((int)randomValues.get(0));
    }
}
