package org.example.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private MongoDatabase database;

    public TodoRepository(MongoDatabase database) {
        this.database = database;
    }

    public void add(Todo todo) {
        MongoCollection<Document> collection = database.getCollection("todos");
        Document doc = new Document("userId", todo.getUserId())
                .append("title", todo.getTitle())
                .append("completed", todo.isCompleted());
        collection.insertOne(doc);
    }

    public List<Todo> findByUserId(String userId) {
        MongoCollection<Document> collection = database.getCollection("todos");
        List<Todo> todos = new ArrayList<>();
        for (Document doc : collection.find(new Document("userId", userId))) {
            Todo todo = new Todo();
            todo.setId(doc.getObjectId("_id").toString());
            todo.setUserId(doc.getString("userId"));
            todo.setTitle(doc.getString("title"));
            todo.setCompleted(doc.getBoolean("completed"));
            todos.add(todo);
        }
        return todos;
    }

    public void remove(String id) {
        MongoCollection<Document> collection = database.getCollection("todos");
        collection.deleteOne(new Document("_id", new org.bson.types.ObjectId(id)));
    }
}
