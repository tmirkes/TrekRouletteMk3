package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Season entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
@Entity(name="Season")
@Table(name="season")
public class Season {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "series", nullable = false, length = 50)
    private String series;
    @Basic
    @Column(name = "season", nullable = false)
    private int season;
    @Basic
    @Column(name = "stapi_season_id", nullable = false, length = 100)
    private String stapiSeasonId;
    @OneToMany(mappedBy = "seasonBySeasonId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Episode> episodesById;
    @OneToMany(mappedBy = "seasonBySeasonId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Own> ownsById;

    /**
     * No-argument constructor
     */
    public Season() {
    }

    /**
     * Parameterized constructor
     *
     * @param series series name
     * @param season season number
     * @param stapiSeasonId API id
     */
    public Season(String series, int season, String stapiSeasonId) {
        this.series = series;
        this.season = season;
        this.stapiSeasonId = stapiSeasonId;
    }

    /**
     * id getter
     *
     * @return id value
     */
    public int getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id new id value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * series getter
     *
     * @return series value
     */
    public String getSeries() {
        return series;
    }

    /**
     * series setter
     *
     * @param series new series value
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * season getter
     *
     * @return season value
     */
    public int getSeason() {
        return season;
    }

    /**
     * season setter
     *
     * @param season new season value
     */
    public void setSeason(int season) {
        this.season = season;
    }

    /**
     * stapiSeasonId getter
     * @return stapiSeasonId value
     */
    public String getStapiSeasonId() {
        return stapiSeasonId;
    }

    /**
     * stapiSeasonId setter
     *
     * @param stapiSeasonId new stapiSeasonId
     */
    public void setStapiSeasonId(String stapiSeasonId) {
        this.stapiSeasonId = stapiSeasonId;
    }

    /**
     * equals override
     * @param o object ot evaluate
     * @return result of comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season1 = (Season) o;
        return id == season1.id && season == season1.season && Objects.equals(series, season1.series) && Objects.equals(stapiSeasonId, season1.stapiSeasonId);
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, series, season, stapiSeasonId);
    }

    /**
     * toString override
     *
     * @return output String
     */
    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", series='" + series + '\'' +
                ", season=" + season +
                ", stapiSeasonId='" + stapiSeasonId + '\'' +
                '}';
    }

    /**
     * episodesById getter
     *
     * @return list of Episode entities
     */
    public List<Episode> getEpisodesById() {
        return episodesById;
    }

    /**
     * episodesById setter
     *
     * @param episodesById new list of Episode entities
     */
    public void setEpisodesById(List<Episode> episodesById) {
        this.episodesById = episodesById;
    }

    /**
     * ownsById getter
     *
     * @return list of Own entities
     */
    public List<Own> getOwnsById() {
        return ownsById;
    }

    /**
     * ownsById setter
     *
     * @param ownsById new list of Own entities
     */
    public void setOwnsById(List<Own> ownsById) {
        this.ownsById = ownsById;
    }

    /**
     * Insert Episode entity into episodesById list
     *
     * @param episode new Episode entity
     */
    public void addEpisodesById(Episode episode) { this.episodesById.add(episode); }

    /**
     * Insert Own entity into ownsById list
     *
     * @param own new Own entity
     */
    public void addOwnsById(Own own) { this.ownsById.add(own); }
}
