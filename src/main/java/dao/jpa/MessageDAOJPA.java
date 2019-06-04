package dao.jpa;

import dao.blueprint.IMessageDAO;
import dao.jpa.config.JPA;
import models.Message;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JPA
@Stateless
@Default
public class MessageDAOJPA implements IMessageDAO {
    @PersistenceContext(unitName = "localhost")
    private EntityManager em;

    @Override
    public Message addMessage(Message message) {
        em.persist(message);
        return message;
    }

    @Override
    public Message getById(long messageId) {
        return em.find(Message.class, messageId);
    }
}
