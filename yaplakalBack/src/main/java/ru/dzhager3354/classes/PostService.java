package ru.dzhager3354.classes;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Component;
import ru.dzhager3354.configurations.ConnectionComponent;
import ru.dzhager3354.dto.PostAnswerDTO;
import ru.dzhager3354.dto.User;
import ru.dzhager3354.objects.Post;
import ru.dzhager3354.objects.PostAnswer;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostService {
    @Autowired
    private ConnectionComponent connection;

    public List<Post> getPosts(User user) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("posts");
            MongoCursor<Document> cursor = collection.find().iterator();
            List<Post> posts = new ArrayList<>();
            while (cursor.hasNext()) {
                JsonObject object = new JsonObject(cursor.next().toJson());
                BsonDocument document = object.toBsonDocument();
                Post post = Post.builder().
                        id(document.getObjectId("_id").getValue().toHexString())
                        .nickname(document.get("user").asString().getValue())
                        .content(document.get("content").asString().getValue())
                        .build();
                posts.add(post);
            }
            System.out.println(posts);
            return posts;
        }
    }

    public List<Post> getPostsByDate(User user) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("posts");
            MongoCursor<Document> cursor = collection.find().sort(Sorts.ascending("user")).iterator();
            List<Post> posts = new ArrayList<>();
            while (cursor.hasNext()) {
                JsonObject object = new JsonObject(cursor.next().toJson());
                BsonDocument document = object.toBsonDocument();
                Post post = Post.builder().
                        id(document.getObjectId("_id").getValue().toHexString())
                        .nickname(document.get("user").asString().getValue())
                        .content(document.get("content").asString().getValue())
                        .build();
                posts.add(post);
            }
            System.out.println(posts);
            return posts;
        }
    }

    public List<Post> getPostsByNick(User user, String nick) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("posts");
            MongoCursor<Document> cursor = collection.find(Filters.regex("user", nick)).iterator();
            List<Post> posts = new ArrayList<>();
            while (cursor.hasNext()) {
                JsonObject object = new JsonObject(cursor.next().toJson());
                BsonDocument document = object.toBsonDocument();
                Post post = Post.builder().
                        id(document.getObjectId("_id").getValue().toHexString())
                        .nickname(document.get("user").asString().getValue())
                        .content(document.get("content").asString().getValue())
                        .build();
                posts.add(post);
            }
            return posts;
        }
    }

    public void addAnswer(User user, PostAnswerDTO dto) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("answers");
            collection.insertOne(new Document()
                    .append("postID", new ObjectId(dto.getPostId()))
                    .append("answerID", dto.getAnswerId() == null ? null : new ObjectId(dto.getAnswerId()))
                    .append("content", dto.getContent())
                    .append("author", user.getUsername())
            );
        }
    }

    public Post getPost(User user, String id) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("posts");
            Document document = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (document == null) {
                return null;
            }
            return Post.builder().
                    id(document.getObjectId("_id").toHexString())
                    .nickname(document.getString("user"))
                    .content(document.getString("content"))
                    .build();
        }
    }

    public void sendPost(User user, ru.dzhager3354.dto.Post post) {
        try (MongoClient client = connection.getConnection(user.getUsername(),
                user.getPassword())){
            MongoCollection collection = client.getDatabase(connection.getDatabaseName())
                    .getCollection("posts");
            Document document = new Document("user", user.getUsername())
                    .append("content", post.getContent());
            collection.insertOne(document);
        }
    }

    public void deletePost(User user, String postId) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection collection = database.getCollection("posts");
            collection.deleteOne(new BasicDBObject("_id", new ObjectId(postId)));
            MongoCollection collection1 = database.getCollection("answers");
            collection1.deleteMany(new BasicDBObject("postID", new ObjectId(postId)));
        }
    }

    public List<PostAnswer> answers(User user, String postId) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())){
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            MongoCollection<Document> collection = database.getCollection("answers");
            MongoCursor<Document> cursor = collection.find(Filters.eq("postID", new ObjectId(postId))).iterator();
            List<PostAnswer> posts = new ArrayList<>();
            while (cursor.hasNext()) {
                JsonObject object = new JsonObject(cursor.next().toJson());
                BsonDocument document = object.toBsonDocument();
                PostAnswer answer = PostAnswer.builder()
                                .id(document.getObjectId("_id").getValue().toHexString())
                        .postId(document.getObjectId("postID").getValue().toHexString())
                        .answerId(document.get("answerID").isNull() ? null :
                                document.getObjectId("answerID").getValue().toHexString())

                        .author(document.getString("author").getValue())
                        .content(document.getString("content").getValue())
                        .build();
                posts.add(answer);
            }
            return posts;
        }
    }

    public int getAnswersCount(User user, String postId) {
        try (MongoClient client = connection.getConnection(user.getUsername(), user.getPassword())) {
            MongoDatabase database = client.getDatabase(connection.getDatabaseName());
            return (int) database.getCollection("answers")
                    .countDocuments(Filters.eq("postID", new ObjectId(postId)));
        }
    }
}
