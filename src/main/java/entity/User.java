package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity(name="User")
@Table(name="user", schema="trekroulette")
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
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Own> ownsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> viewsById;

    public User() {
    }

    public User(String userName, String firstName, String lastName, Timestamp lastLogin) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(lastLogin, user.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, lastLogin);
    }

    public List<Own> getOwnsById() {
        return ownsById;
    }

    public void setOwnsById(List<Own> ownsById) {
        this.ownsById = ownsById;
    }

    public List<View> getViewsById() {
        return viewsById;
    }

    public void setViewsById(List<View> viewsById) {
        this.viewsById = viewsById;
    }
}
