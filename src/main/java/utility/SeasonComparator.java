package utility;

import entity.Season;

import java.util.Comparator;

/**
 * The SeasonComparator is a custom comparator class for use in sorting <code>ArrayList</code> objects via lambda
 * methods.
 *
 * @author tlmirkes
 * @version 1.0
 */
public class SeasonComparator implements Comparator<Season> {
    // https://stackoverflow.com/questions/4310014/sort-an-arraylist-base-on-multiple-attributes

    /**
     * Perform comparison between two Season objects.
     *
     * @param a First Season object
     * @param b Second Season object
     * @return result of compareTo process
     */
    public int compare(Season a, Season b) {
        int seriesNameCompare = a.getSeries().compareTo(b.getSeries());
        return seriesNameCompare == 0 ? Integer.compare(a.getSeason(), (b.getSeason())) : seriesNameCompare;
    }
}
