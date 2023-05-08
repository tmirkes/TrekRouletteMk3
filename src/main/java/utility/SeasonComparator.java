package utility;

import entity.Season;

import java.util.Comparator;

public class SeasonComparator implements Comparator<Season> {
    // https://stackoverflow.com/questions/4310014/sort-an-arraylist-base-on-multiple-attributes
    public int compare(Season a, Season b) {
        int seriesNameCompare = a.getSeries().compareTo(b.getSeries());
        return seriesNameCompare == 0 ? Integer.compare(a.getSeason(), (b.getSeason())) : seriesNameCompare;
    }
}
