package persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * TrekDao provides a universal set of methods for accessing and modifying persistence objects via Hibernate.  Basic
 * methods are outlined to satisfy create, retrieve, update, and delete functions.
 *
 * @param <T> class of object to be processed
 */
public class TrekDao<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Parameterized constructor
     * @param type class of object
     */
    public TrekDao(Class<T> type) {
        this.type = type;
    }
    /**
     * Session getter
     * @return new Session object
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
    /**
     * Transaction getter
     * @param session Session object to associate Transaction with
     * @return new Transaction object
     */
    private Transaction getTransaction(Session session) {
        return session.beginTransaction();
    }
    /**
     * Retrieve database objects by their id attribute
     *
     * @param id id value to retrieve
     * @return object of type T retrieved
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }
    /**
     * Retrieve database objects by specified attributes and values by exact match
     *
     * @param property object property to search
     * @param value value to search for match
     * @return list of objects of type T matching search value
     */
    public List<T> getByPropertyEqual(String property, String value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(property), value));
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }
    /**
     * Retrieve database objects by specified attributes and values by partial/containing match
     *
     * @param property object property to search
     * @param value value to search for partial/containing match
     * @return list of objects of type T matching search value
     */
    public List<T> getByPropertyLike(String property, Object value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        String searchTerm = "%" + value + "%";
        query.select(root).where(builder.like(root.get(property), searchTerm));
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }
    /**
     * Retrieve all database objects
     *
     * @return list of objects of type T in target table
     */
    public List<T> getAll() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }
    /**
     * Create a new entity in the target table
     * @param entity object of type T to be created
     * @return integer id value of created object
     */
    public int addEntity(T entity) {
        Session session = getSession();
        Transaction transaction = getTransaction(session);
        int id = (int)session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }
    /**
     * Update the attributes of an existing database entity
     * @param entity object of type T to modify
     */
    public void editEntity(T entity) {
        Session session = getSession();
        Transaction transaction = getTransaction(session);
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();
    }
    /**
     * Delete an existing database entity
     * @param entity object of type T to delete
     */
    public void deleteEntity(T entity) {
        Session session = getSession();
        Transaction transaction = getTransaction(session);
        session.delete(entity);
        transaction.commit();
        session.close();
    }
}