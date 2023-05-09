package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
@Entity(name="User")
@Table(name="user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;
    @Basic
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Basic
    @Column(name = "last_login", nullable = true)
    private Timestamp lastLogin;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Own> ownsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<View> viewsById;

    /**
     * No-argument constructor
     */
    public User() {
    }

    /**
     * Parameterized constructor
     *
     * @param userName user name
     * @param firstName first name
     * @param lastName last name
     * @param lastLogin date of last lagin
     */
    public User(String userName, String firstName, String lastName, Timestamp lastLogin) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastLogin = lastLogin;
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
     * userName getter
     *
     * @return userName value
     */
    public String getUserName() {
        return userName;
    }

    /**
     * userName setter
     * @param userName new userName value
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * firstName getter
     *
     * @return firstName value
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * firstName setter
     *
     * @param firstName new firstName value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * lastName getter
     *
     * @return lastName value
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * lastName setter
     *
     * @param lastName new lastName value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * lastLogin getter
     *
     * @return lastLogin value
     */
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    /**
     * lastLogin setter
     *
     * @param lastLogin new lastLogin setter
     */
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
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
        User user = (User) o;
        return getId() == user.getId() && getUserName().equals(user.getUserName()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName());
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getFirstName(), getLastName());
    }

    /**
     * toString override
     *
     * @return output String
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * ownsById getter
     *
     * @return set of Own entities
     */
    public Set<Own> getOwnsById() {
        return ownsById;
    }

    /**
     * ownsById setter
     *
     * @param ownsById new set of Own entities
     */
    public void setOwnsById(Set<Own> ownsById) {
        this.ownsById = ownsById;
    }

    /**
     * Insert Own entity into ownById list
     *
     * @param ownById Own entity
     */
    public void addOwnsById(Own ownById) { this.ownsById.add(ownById); }

    /**
     * viewsById getter
     * @return set of View entities
     */
    public Set<View> getViewsById() {
        return viewsById;
    }

    /**
     * viewsById setter
     *
     * @param viewsById new set of View entities
     */
    public void setViewsById(Set<View> viewsById) {
        this.viewsById = viewsById;
    }

    /**
     * Insert View entity into viewsById list
     *
     * @param viewById View entity
     */
    public void addViewsById(View viewById) { this.viewsById.add(viewById); }
}
