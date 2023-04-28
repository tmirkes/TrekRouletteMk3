package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity(name="View")
@Table(name="view")
public class View {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "view_date", nullable = false)
    private Timestamp viewDate;
    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;
    @Basic
    @Column(name = "episode_id", nullable = false, insertable = false, updatable = false)
    private int episodeId;
    @Basic
    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
    private int statusId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "episode_id", referencedColumnName = "id", nullable = false)
    private Episode episodeByEpisodeId;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private Status statusByStatusId;

    public View() {
    }

    public View(Timestamp viewDate, int userId, int episodeId, int statusId) {
        this.viewDate = viewDate;
        this.userId = userId;
        this.episodeId = episodeId;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getViewDate() {
        return viewDate;
    }

    public void setViewDate(Timestamp viewDate) {
        this.viewDate = viewDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return id == view.id && userId == view.userId && episodeId == view.episodeId && statusId == view.statusId && Objects.equals(viewDate, view.viewDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, viewDate, userId, episodeId, statusId);
    }

    @Override
    public String toString() {
        return "View{" +
                "id=" + id +
                ", viewDate=" + viewDate +
                ", userId=" + userId +
                ", episodeId=" + episodeId +
                ", statusId=" + statusId +
                '}';
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Episode getEpisodeByEpisodeId() {
        return episodeByEpisodeId;
    }

    public void setEpisodeByEpisodeId(Episode episodeByEpisodeId) {
        this.episodeByEpisodeId = episodeByEpisodeId;
    }

    public Status getStatusByStatusId() {
        return statusByStatusId;
    }

    public void setStatusByStatusId(Status statusByStatusId) {
        this.statusByStatusId = statusByStatusId;
    }
}
