package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.model.User;

public class UserRepository {
    private MongoDatabase database;

    public UserRepository(MongoDatabase database) {
        this.database = database;
    }

    public void save(User user) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = new Document("username", user.getUsername())
                .append("password", user.getPassword());
        collection.insertOne(doc);
    }

    public User findByUsername(String username) {
        MongoCollection<Document> collection = database.getCollection("users");
        Document doc = collection.find(new Document("username", username)).first();
        if (doc != null) {
            User user = new User();
            user.setId(doc.getObjectId("_id").toString());
            user.setUsername(doc.getString("username"));
            user.setPassword(doc.getString("password"));
            return user;
        }
        return null;
    }
}
