package com.github.avo0303.messenger.mongo;

import com.github.avo0303.messenger.config.Config;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

// just sandbox for mongo
// TODO: delete later
public class MongoClientTest {
  private final Config config = new Config();
  private final String TEST_DATABASE_NAME = config.getProperty("db.test.name");
  // TODO: HIDE!
  private final String URL = config.getProperty("db.url");
  private final ConnectionString connectionString = new ConnectionString(URL);

  @Test
  public void testConnection() {
    MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build());
    MongoDatabase db = mongoClient.getDatabase(TEST_DATABASE_NAME);
    MongoCollection<Document> c = db.getCollection(config.getProperty("db.collection.test.name"));
    Assertions.assertDoesNotThrow(
        () -> mongoClient.listDatabaseNames().first());
  }
}
