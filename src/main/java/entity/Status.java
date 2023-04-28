package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name="Status")
@Table(name="status")
public class Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "status_title", nullable = false, length = 25)
    private String statusTitle;
    @OneToMany(mappedBy = "statusByStatusId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> viewsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return id == status.id && Objects.equals(statusTitle, status.statusTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusTitle);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", statusTitle='" + statusTitle + '\'' +
                '}';
    }

    public List<View> getViewsById() {
        return viewsById;
    }

    public void setViewsById(List<View> viewsById) {
        this.viewsById = viewsById;
    }
}
