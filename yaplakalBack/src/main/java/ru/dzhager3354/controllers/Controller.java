package ru.dzhager3354.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.websocket.server.PathParam;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzhager3354.classes.AuthService;
import ru.dzhager3354.dto.InfoUpdate;
import ru.dzhager3354.dto.User;
import ru.dzhager3354.dto.UserEditDto;
import ru.dzhager3354.objects.UserInfo;

@RestController
public class Controller {
    @Autowired
    public AuthService connection;

    @PostMapping("/registerUser")
    @CrossOrigin
    public ResponseEntity<String> connect(@RequestBody User user) {
        connection.registerUser(user);
        return ResponseEntity.ok("");
    }

    @PostMapping("/createModer")
    public ResponseEntity<String> createModer(@RequestBody User user) {
        connection.createModer(user);
        return ResponseEntity.ok("new moder");
    }

    @PostMapping("/auth")
    @CrossOrigin
    public ResponseEntity<String> checkUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null ||
            user.getUsername().isEmpty() || user.getPassword().isEmpty())
            return ResponseEntity.badRequest().body("not null");
        boolean res = connection.checkUser(user);
        if (res)
            return ResponseEntity.ok("");
        else
            return ResponseEntity.badRequest().body("incorrect login");
    }

    @PostMapping("/profile")
    @CrossOrigin
    public ResponseEntity<UserInfo> getUser(@RequestBody User user) {
        return ResponseEntity.ok(connection.getUser(user));
    }

    @PostMapping("/updateProfile")
    @CrossOrigin
    public ResponseEntity<String> setUserInfo(@RequestBody InfoUpdate infoUpdate) {
        System.out.println("update profile");
        connection.updateUser(infoUpdate.getUser(), infoUpdate.getDto());
        return ResponseEntity.ok("");
    }
}
