package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="Own")
@Table(name="own")
public class Own {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;
    @Basic
    @Column(name = "season_id", nullable = false, insertable = false, updatable = false)
    private int seasonId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id", nullable = false)
    private Season seasonBySeasonId;

    public Own() {
    }

    public Own(int userId, int seasonId) {
        this.userId = userId;
        this.seasonId = seasonId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        Own own = (Own) o;
        return id == own.id && userId == own.userId && seasonId == own.seasonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, seasonId);
    }

    @Override
    public String toString() {
        return "Own{" +
                "id=" + id +
                ", userId=" + userId +
                ", seasonId=" + seasonId +
                ", userByUserId=" + userByUserId +
                ", seasonBySeasonId=" + seasonBySeasonId +
                '}';
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Season getSeasonBySeasonId() {
        return seasonBySeasonId;
    }

    public void setSeasonBySeasonId(Season seasonBySeasonId) {
        this.seasonBySeasonId = seasonBySeasonId;
    }
}
