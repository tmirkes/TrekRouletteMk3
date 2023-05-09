package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Own entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
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

    /**
     * No-argument constructor
     */
    public Own() {
    }

    /**
     * Parameterized constructor
     * @param userId user id
     * @param seasonId season id
     */
    public Own(int userId, int seasonId) {
        this.userId = userId;
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
     * userid getter
     *
     * @return userId value
     */
    public int getUserId() {
        return userId;
    }

    /**
     * userId setter
     *
     * @param userId new userId value
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * seasonId getter
     *
     * @return value of seasonId
     */
    public int getSeasonId() {
        return seasonId;
    }

    /**
     * seasonId setter
     *
     * @param seasonId new seasonid value
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
        Own own = (Own) o;
        return id == own.id && userId == own.userId && seasonId == own.seasonId;
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, seasonId);
    }

    /**
     * toString override
     *
     * @return output String
     */
    @Override
    public String toString() {
        return "Own{" +
                "id=" + id +
                ", userId=" + userId +
                ", seasonId=" + seasonId +
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
}
