package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * View entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
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

    /**
     * No-argument constructor
     */
    public View() {
    }

    /**
     * Parameterized constructor
     *
     * @param viewDate date of view creation
     * @param userId authenticated user id
     * @param episodeId episode id
     * @param statusId status id
     */
    public View(Timestamp viewDate, int userId, int episodeId, int statusId) {
        this.viewDate = viewDate;
        this.userId = userId;
        this.episodeId = episodeId;
        this.statusId = statusId;
    }

    /**
     * id getter
     *
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * id setter
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * viewDate getter
     *
     * @return value of viewDate
     */
    public Timestamp getViewDate() {
        return viewDate;
    }

    /**
     * viewDate setter
     * @param viewDate new value of viewDate
     */
    public void setViewDate(Timestamp viewDate) {
        this.viewDate = viewDate;
    }

    /**
     * userId getter
     *
     * @return value of userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * userId setter
     *
     * @param userId new value of userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * episodeId getter
     * @return value of episodeId
     */
    public int getEpisodeId() {
        return episodeId;
    }

    /**
     * episodeId setter
     * @param episodeId new value of episodeId
     */
    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    /**
     * statusId getter
     *
     * @return value of statusId
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * statusId setter
     *
     * @param statusId new value of statusId
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
        View view = (View) o;
        return id == view.id && userId == view.userId && episodeId == view.episodeId && statusId == view.statusId && Objects.equals(viewDate, view.viewDate);
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, viewDate, userId, episodeId, statusId);
    }

    /**
     * toString override
     *
     * @return output String
     */
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

    /**
     * userByUserId getter
     *
     * @return User entity
     */
    public User getUserByUserId() {
        return userByUserId;
    }

    /**
     * userByUserId setter
     *
     * @param userByUserId new User entity
     */
    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    /**
     * episodeByEpisodeId getter
     *
     * @return Episode entity
     */
    public Episode getEpisodeByEpisodeId() {
        return episodeByEpisodeId;
    }

    /**
     * episodeByEpisodeId setter
     *
     * @param episodeByEpisodeId new Episode entity
     */
    public void setEpisodeByEpisodeId(Episode episodeByEpisodeId) {
        this.episodeByEpisodeId = episodeByEpisodeId;
    }

    /**
     * statusByStatusId getter
     *
     * @return Status entity
     */
    public Status getStatusByStatusId() {
        return statusByStatusId;
    }

    /**
     * statusByStatusId setter
     *
     * @param statusByStatusId new Status entity
     */
    public void setStatusByStatusId(Status statusByStatusId) {
        this.statusByStatusId = statusByStatusId;
    }
}
