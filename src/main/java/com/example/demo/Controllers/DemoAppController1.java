package com.example.demo.Controllers;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

@RestController
@RequestMapping("/users")
public class DemoAppController1 extends MongoConfig{
    
    @PostMapping("/insertUser")
    public String insertUser(@RequestParam String name, @RequestParam String email) {
        createUser(name, email);
        return "User Created!";
    }

    @GetMapping("/getUser/{email}")
    public Document getUserByMail(@PathVariable String email) {
        return findUserByMail(email);
    }

    @PatchMapping("/updateUser/{email}")
    public String updateUserByMail(@PathVariable String email, @RequestParam String name) {
        changeUserByMail(email, name);
        return "User Updated Successfully!";
    }

    @PutMapping("/replaceUser/{org_email}")
    public String replaceUserByMail(@PathVariable String org_email, @RequestParam String name, @RequestParam String email) {
        putUserByMail(org_email, name, email);
        return "User replaced Successfully!";
    }

    @DeleteMapping("/deleteUser/{email}")
    public String deleteUserByMail(@PathVariable String email) {
        removeUserByMail(email);
        return "User deleted Successfully!";
    }

}

class MongoConfig {
    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> userCollection;
    
    public MongoConfig() {
        this.client = MongoClients.create("mongodb://localhost:27017");
        this.database = client.getDatabase("Users");
        this.userCollection = database.getCollection("user");
    }

    public void createUser(String name, String email) {
        Document doc = new Document("name", name).append("email", email);
        userCollection.insertOne(doc);
    }

    public Document findUserByMail(String email) {
        Bson findMail = Filters.eq("email", email);
        return userCollection.find(findMail).first();
    }

    public void changeUserByMail(String email, String name) {
        Bson findMail = Filters.eq("email", email);
        Bson update = Updates.set("name", name);
        userCollection.updateOne(findMail, update);
    }

    public void putUserByMail(String org_email, String name, String email) {
        Bson findMail = Filters.eq("email", org_email);
        Document doc = new Document("name", name).append("email", email);
        userCollection.replaceOne(findMail, doc);
    }

    public void removeUserByMail(String email) {
        Bson findMail = Filters.eq("email", email);
        userCollection.deleteOne(findMail);
    }
}

