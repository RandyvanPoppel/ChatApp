package dao.blueprint;

import models.Message;

public interface IMessageDAO {
    Message addMessage(Message message);

    Message getById(long messageId);
}
