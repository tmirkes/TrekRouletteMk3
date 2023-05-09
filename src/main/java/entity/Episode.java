package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Episode entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
@Entity(name="Episode")
@Table(name="episode")
public class Episode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "stapi_episode_id", nullable = false, length = 50)
    private String stapiEpisodeId;
    @Basic
    @Column(name = "season_id", nullable = false, insertable = false, updatable = false)
    private int seasonId;
    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id", nullable = false)
    private Season seasonBySeasonId;
    @OneToMany(mappedBy = "episodeByEpisodeId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<View> viewsById;

    /**
     * No-argument constructor
     */
    public Episode() {
    }

    /**
     * Parameterized constructor
     *
     * @param title episode title
     * @param stapiEpisodeId API id
     * @param seasonId season id
     */
    public Episode(String title, String stapiEpisodeId, int seasonId) {
        this.title = title;
        this.stapiEpisodeId = stapiEpisodeId;
        this.seasonId = seasonId;
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
     * title getter
     *
     * @return title value
     */
    public String getTitle() {
        return title;
    }

    /**
     * title setter
     *
     * @param title new title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * stapiEpisodeId getter
     *
     * @return stapiEpisodeId value
     */
    public String getStapiEpisodeId() {
        return stapiEpisodeId;
    }

    /**
     * stapiEpisodeId setter
     *
     * @param stapiEpisodeId new stapiEpisodeId value
     */
    public void setStapiEpisodeId(String stapiEpisodeId) {
        this.stapiEpisodeId = stapiEpisodeId;
    }

    /**
     * seasonId getter
     *
     * @return seasonId value
     */
    public int getSeasonId() {
        return seasonId;
    }

    /**
     * seasonId setter
     *
     * @param seasonId new seasonId value
     */
    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
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
        Episode episode = (Episode) o;
        return id == episode.id && seasonId == episode.seasonId && Objects.equals(title, episode.title) && Objects.equals(stapiEpisodeId, episode.stapiEpisodeId);
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, stapiEpisodeId, seasonId);
    }

    /**
     * toString override
     *
     * @return output String
     */
    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stapiEpisodeId='" + stapiEpisodeId + '\'' +
                ", seasonId=" + seasonId +
                '}';
    }

    /**
     * seasonBySeasonId getter
     *
     * @return Season entity
     */
    public Season getSeasonBySeasonId() {
        return seasonBySeasonId;
    }

    /**
     * seasonBySeasonId setter
     *
     * @param seasonBySeasonId new Season entity
     */
    public void setSeasonBySeasonId(Season seasonBySeasonId) {
        this.seasonBySeasonId = seasonBySeasonId;
    }

    /**
     * viewsById getter
     *
     * @return list of View entities
     */
    public List<View> getViewsById() {
        return viewsById;
    }

    /**
     * viewsById setter
     *
     * @param viewsById new list of View entities
     */
    public void setViewsById(List<View> viewsById) {
        this.viewsById = viewsById;
    }

    /**
     * Insert View entity into viewsById list
     *
     * @param viewById View entity
     */
    public void addViewsById(View viewById) { this.viewsById.add(viewById); }
}
