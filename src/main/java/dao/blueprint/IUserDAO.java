package dao.blueprint;

import models.User;

public interface IUserDAO {
    User addUser(User user);

    User updateUser(User user);

    User getById(long userId);

    User getByUsername(String username);
}
