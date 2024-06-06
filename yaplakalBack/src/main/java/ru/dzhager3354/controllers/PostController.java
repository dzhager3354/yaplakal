package ru.dzhager3354.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzhager3354.classes.PostService;
import ru.dzhager3354.dto.NewAnswer;
import ru.dzhager3354.dto.NewPost;
import ru.dzhager3354.dto.User;
import ru.dzhager3354.objects.Post;
import ru.dzhager3354.objects.PostAnswer;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService service;

    @PostMapping("/posts")
    @CrossOrigin
    public ResponseEntity<List<Post>> getPosts(@RequestBody User user) {
        return ResponseEntity.ok(service.getPosts(user));
    }

    @PostMapping("/createPost")
    @CrossOrigin
    public ResponseEntity<String> createPost(@RequestBody NewPost newPost) {
        service.sendPost(newPost.getUser(), newPost.getPost());
        return ResponseEntity.ok("post created");
    }

    @PostMapping("/answer")
    @CrossOrigin
    public ResponseEntity<String> answer(@RequestBody NewAnswer answer) {
        System.out.println(answer);
        service.addAnswer(answer.getUser(), answer.getDto());
        return ResponseEntity.ok("create answer");
    }

    @PostMapping("/answers/{id}")
    @CrossOrigin
    public ResponseEntity<List<PostAnswer>> answersFromPost(@RequestBody User user, @PathVariable("id") String postId) {
        return ResponseEntity.ok(service.answers(user, postId));
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public ResponseEntity<String> delete(@RequestBody User user, @PathVariable("id") String id) {
        service.deletePost(user, id);
        return ResponseEntity.ok("123");
    }

    @PostMapping("/post/{id}")
    @CrossOrigin
    public ResponseEntity<Post> post(@RequestBody User user, @PathVariable("id") String id) {
        if (user == null || user.getUsername() == null || user.getPassword() == null || id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Post post = service.getPost(user, id);
        System.out.println(post);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/sort/date")
    @CrossOrigin
    public ResponseEntity<List<Post>> sortByDate(@RequestBody User user) {
        return ResponseEntity.ok(service.getPostsByDate(user));
    }

    @PostMapping("/sort/nick/{nick}")
    @CrossOrigin
    public ResponseEntity<List<Post>> sortByNick(@RequestBody User user, @PathVariable("nick") String nick) {
        return ResponseEntity.ok(service.getPostsByNick(user, nick));
    }

    @PostMapping("/count/{id}")
    @CrossOrigin
    public ResponseEntity<Integer> count(@RequestBody User user, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.getAnswersCount(user, id));
    }
}