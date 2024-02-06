package com.github.avo0303.messenger.service;

import com.github.avo0303.messenger.config.Config;
import com.github.avo0303.messenger.model.User;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import java.security.SecureRandom;
import java.util.Base64;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthServiceImpl implements AuthService {
  private final Logger logger;
  private final SecureRandom secureRandom = new SecureRandom(); // threadsafe
  private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  @Inject
  @Named(value = "users")
  private MongoCollection<Document> collection;

  @Inject
  private Config config;

  public AuthServiceImpl() {
    logger = LoggerFactory.getLogger(AuthService.class);
  }

  @Override
  public String register(@Valid User user) throws AuthServiceException {
    collection.insertOne(new Document(user.props()));
    return "reg";
  }

  @Override
  public String auth(@Valid User user) throws AuthServiceException {
    return "auth";
  }

  @Override
  public Config getConfig() {
    return config;
  }
}
