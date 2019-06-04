package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dao.blueprint.IUserDAO;
import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {
    @Inject
    private IUserDAO userDAO;

    public UserService() {
    }

    public User addUser(long userId, String username) {
        return userDAO.addUser(new User(userId, username));
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public User getById(long userId) {
        return userDAO.getById(userId);
    }

    public User getByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    public User checkIfUserAuthenticated(String token) {
        HttpResponse<JsonNode> body = null;
        try {
            body = Unirest.get("http://localhost:8080/AuthenticationApp-1.0/rest/jsonwebtoken/validate").queryString("tokenString", token).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (body.getBody() != null) {
            String resultobject = body.getBody().toString();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(resultobject);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            long id = jsonObject.get("id").getAsLong();
            String username = jsonObject.get("username").getAsString();
            User user = this.getById(id);
            if (user != null) {
                if (!user.getUsername().equals(username)) {
                    user.setUsername(username);
                    this.updateUser(user);
                }
            } else {
                user = this.addUser(id, username);
            }
            return user;
        }
        return null;
    }
}
