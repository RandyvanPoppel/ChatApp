package dao.jpa;

import dao.blueprint.IUserDAO;
import dao.jpa.config.JPA;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@JPA
@Stateless
@Default
public class UserDAOJPA implements IUserDAO {
    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public User addUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        em.merge(user);
        return user;
    }

    @Override
    public User getById(long userId) {
        return em.find(User.class, userId);
    }

    @Override
    public User getByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery("user.getByUsername", User.class);
        query.setParameter("username", username);
        List<User> results = query.getResultList();
        if (results.size() > 0)
        {
            return results.get(0);
        }
        return null;
    }
}
