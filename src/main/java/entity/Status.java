package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Status entity class
 *
 * @author tlmirkes
 * @version 1.0
 */
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

    /**
     * No-argument constructor
     */
    public Status() {
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
     * statusTitle getter
     *
     * @return statusTitle value
     */
    public String getStatusTitle() {
        return statusTitle;
    }

    /**
     * statusTitle setter
     *
     * @param statusTitle new statusTitle value
     */
    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
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
        Status status = (Status) o;
        return id == status.id && Objects.equals(statusTitle, status.statusTitle);
    }

    /**
     * hashCode override
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, statusTitle);
    }

    /**
     * toString override
     *
     * @return output String
     */
    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", statusTitle='" + statusTitle + '\'' +
                '}';
    }

    /**
     * viewsById getter
     * @return list of View entities
     */
    public List<View> getViewsById() {
        return viewsById;
    }

    /**
     * viewsById setter
     * @param viewsById new list of View entities
     */
    public void setViewsById(List<View> viewsById) {
        this.viewsById = viewsById;
    }

    /**
     * Insert View entity into viewsById list
     *
     * @param view
     */
    public void addViewsById(View view) { this.viewsById.add(view); }
}
