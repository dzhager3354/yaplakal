package ru.dzhager3354.classes;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dzhager3354.configurations.ConnectionComponent;
import ru.dzhager3354.dto.Post;
import ru.dzhager3354.dto.User;
import ru.dzhager3354.dto.UserInfoDto;
import ru.dzhager3354.objects.UserInfo;

@Component
public class AuthService {
    @Autowired
    private ConnectionComponent connection;

    public boolean checkUser(User user) {
        try(MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            client.getDatabase(connection.getDatabaseName())
                    .getCollection("posts")
                    .find()
                    .explain();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void registerUser(User user) {
        try(MongoClient client = connection.connectCreator()) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            String[] roles = {"user"};
            Bson bson = new BasicDBObject("createUser", user.getUsername())
                    .append("pwd", user.getPassword())
                    .append("roles", roles);
            database.runCommand(bson);
        }
    }

    public void createModer(User user) {
        try (MongoClient client = connection.connectCreator()){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            String[] roles = {"moder"};
            Bson bson = new BasicDBObject("createUser", user.getUsername())
                    .append("pwd", user.getPassword())
                    .append("roles", roles);
            database.runCommand(bson);
        }
    }

    public UserInfo getUser(User user, String nick) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("profiles");
            Document document = collection.find(Filters.eq("nickname", nick)).first();
            System.out.println(document);
            if (document == null || document.isEmpty())
                return null;
            UserInfo info = UserInfo.builder()
                    .nickname(document.getString("nickname"))
                    .name(document.getString("name"))
                    .age(document.getInteger("age"))
                    .build();
            return info;
        }
    }

    public UserInfo getUser(User user) {
        return getUser(user, user.getUsername());
    }

    public void updateUser(User user, UserInfoDto dto) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("profiles");
            Document document = collection.find(Filters.eq("nickname", user.getUsername())).first();
            if (document == null || document.isEmpty()) {
                collection.insertOne(new Document().
                        append("nickname", user.getUsername())
                        .append("age", dto.getAge())
                        .append("name", dto.getNick())
                );
            }
            else {
                collection.updateOne(Filters.eq("nickname", user.getUsername()),
                        Updates.combine(Updates.set("age", dto.getAge()),
                                Updates.set("name", dto.getNick()))
                        );
            }
        }
    }
}