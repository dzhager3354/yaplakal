package ru.dzhager3354.configurations;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionComponent {
    @Autowired
    private String databaseName;

    public MongoClient getConnection(String username, String password) {
        MongoCredential credential = MongoCredential.createCredential(username, databaseName, password.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .build();
        return MongoClients.create(settings);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public MongoClient connectCreator() {
        return getConnection("usersCreator", "usersCreator");
    }
}
