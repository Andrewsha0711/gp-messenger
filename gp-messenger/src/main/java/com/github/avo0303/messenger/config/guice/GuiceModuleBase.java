package com.github.avo0303.messenger.config.guice;

import com.github.avo0303.messenger.config.Config;
import com.github.avo0303.messenger.service.AuthService;
import com.github.avo0303.messenger.service.AuthServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Named;
import java.util.List;
import org.bson.Document;

public class GuiceModuleBase extends AbstractModule {
  private final Config config = new Config();
  private MongoClient mongoClient;
  private final List<String> mongoCollections = List.of("users");

  @Override
  protected void configure() {
    bind(AuthService.class).to(AuthServiceImpl.class);
    // mongoCollections.forEach(e -> bind(MongoCollection.class)
    // .annotatedWith(Names.named(e))
    // .toInstance(getMongoCollection(e)));
  }

  private MongoClient getMongoClient() {
    if (mongoClient == null) {
      ConnectionString connectionString = new ConnectionString(config.getProperty("db.url"));
      mongoClient = MongoClients.create(MongoClientSettings.builder()
          .applyConnectionString(connectionString)
          .build());
    }
    return mongoClient;
  }

  private MongoDatabase getMongoDatabase() {
    return getMongoClient().getDatabase(config.getProperty("db.name"));
  }

  @Provides
  @Named("users")
  private MongoCollection<Document> getMongoCollection() {
    return getMongoDatabase().getCollection("users");
  }
}
