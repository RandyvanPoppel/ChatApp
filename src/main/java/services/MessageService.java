package services;

import dao.blueprint.IMessageDAO;
import models.Message;
import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MessageService {
    @Inject
    private IMessageDAO messageDAO;

    public MessageService() {}

    public Message addMessage(String message, User user)
    {
        return messageDAO.addMessage(new Message(message, user, System.currentTimeMillis() / 1000L));
    }

    public Message getById(long messageId)
    {
        return messageDAO.getById(messageId);
    }
}
