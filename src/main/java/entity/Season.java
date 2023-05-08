package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    public Season() {
    }

    public Season(String series, int season, String stapiSeasonId) {
        this.series = series;
        this.season = season;
        this.stapiSeasonId = stapiSeasonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getStapiSeasonId() {
        return stapiSeasonId;
    }

    public void setStapiSeasonId(String stapiSeasonId) {
        this.stapiSeasonId = stapiSeasonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season1 = (Season) o;
        return id == season1.id && season == season1.season && Objects.equals(series, season1.series) && Objects.equals(stapiSeasonId, season1.stapiSeasonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, season, stapiSeasonId);
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", series='" + series + '\'' +
                ", season=" + season +
                ", stapiSeasonId='" + stapiSeasonId + '\'' +
                '}';
    }

    public List<Episode> getEpisodesById() {
        return episodesById;
    }

    public void setEpisodesById(List<Episode> episodesById) {
        this.episodesById = episodesById;
    }

    public List<Own> getOwnsById() {
        return ownsById;
    }

    public void setOwnsById(List<Own> ownsById) {
        this.ownsById = ownsById;
    }

    public void addEpisodesById(Episode episode) { this.episodesById.add(episode); }

    public void addOwnsById(Own own) { this.ownsById.add(own); }
}
