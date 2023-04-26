package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name="Episode")
@Table(name="episode", schema="trekroulette")
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
    @OneToMany(mappedBy = "episodeByEpisodeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> viewsById;

    public Episode() {
    }

    public Episode(String title, String stapiEpisodeId, int seasonId) {
        this.title = title;
        this.stapiEpisodeId = stapiEpisodeId;
        this.seasonId = seasonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStapiEpisodeId() {
        return stapiEpisodeId;
    }

    public void setStapiEpisodeId(String stapiEpisodeId) {
        this.stapiEpisodeId = stapiEpisodeId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return id == episode.id && seasonId == episode.seasonId && Objects.equals(title, episode.title) && Objects.equals(stapiEpisodeId, episode.stapiEpisodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, stapiEpisodeId, seasonId);
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stapiEpisodeId='" + stapiEpisodeId + '\'' +
                ", seasonId=" + seasonId +
                ", seasonBySeasonId=" + seasonBySeasonId +
                ", viewsById=" + viewsById +
                '}';
    }

    public Season getSeasonBySeasonId() {
        return seasonBySeasonId;
    }

    public void setSeasonBySeasonId(Season seasonBySeasonId) {
        this.seasonBySeasonId = seasonBySeasonId;
    }

    public List<View> getViewsById() {
        return viewsById;
    }

    public void setViewsById(List<View> viewsById) {
        this.viewsById = viewsById;
    }
}
