package com.github.avo0303.messenger;

import com.github.avo0303.messenger.endpoint.ChatEndpoint;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;

public class App {

  private static class WebsocketServer {
    private Server server;
    private String host = "localhost";
    private int port = 8080;
    private String keyStorePath = "gp-keystore.pkcs12";
    private String keyStorePassword = "password";
    private String keyManagerPassword = "password";
    private List<Handler> webSocketHandlerList = new ArrayList<>();
    // MessageHandler messagehandler;

    private void keyStore() throws KeyStoreException, FileNotFoundException,
        IOException, NoSuchAlgorithmException,
        CertificateException {
      KeyStore ks = KeyStore.getInstance("pkcs12");
      ks.load(null, new char[] {});
      try (FileOutputStream fos = new FileOutputStream(keyStorePath)) {
        ks.store(fos, null);
      }
    }

    public WebsocketServer() throws Exception {
      server = new Server();

      keyStore();
      // connector configuration
      SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
      sslContextFactory.setKeyStorePath(keyStorePath);
      sslContextFactory.setKeyStorePassword(keyStorePassword);
      sslContextFactory.setKeyManagerPassword(keyManagerPassword);
      SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(
          sslContextFactory, HttpVersion.HTTP_1_1.asString());
      HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(new HttpConfiguration());
      ServerConnector sslConnector = new ServerConnector(
          server, sslConnectionFactory, httpConnectionFactory);
      sslConnector.setHost(host);
      sslConnector.setPort(port);
      server.addConnector(sslConnector);

      // handler configuration
      HandlerCollection handlerCollection = new HandlerCollection();
      handlerCollection.setHandlers(
          webSocketHandlerList.toArray(new Handler[0]));
      server.setHandler(handlerCollection);

      ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
      context.setContextPath("/");
      server.setHandler(context);
      // Initialize javax.websocket layer
      JakartaWebSocketServletContainerInitializer.configure(
          context, (servletContext, wsContainer) -> {
            // This lambda will be called at the appropriate place in the
            // ServletContext initialization phase where you can initialize
            // and configure your websocket container.

            // Configure defaults for container
            wsContainer.setDefaultMaxTextMessageBufferSize(65535);

            // Add WebSocket endpoint to javax.websocket layer
            wsContainer.addEndpoint(ChatEndpoint.class);
          });

      // ContextHandler wsContextHandler = new ContextHandler();
      // wsContextHandler.setHandler(wsHandler);
      // wsContextHandler.setContextPath(
      // "/"); // this context path doesn't work ftm
      webSocketHandlerList.add(context);

      // messagehandler = new MessageHandler();
      // new Thread(messagehandler).start();

      server.start();
      server.join();
    }
  }

  public static void main(String[] args) throws Exception {
    // This part configures the server for http on 'port' and https on 'sslPort'

    final int port = 5040;
    final int sslPort = 8443;
    final Server server = new Server(port);

    SslContextFactory.Server contextFactory = new SslContextFactory.Server();
    contextFactory.setKeyStorePath("./keystore");
    contextFactory.setKeyStorePassword("password");
    SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(
        contextFactory, org.eclipse.jetty.http.HttpVersion.HTTP_1_1.toString());

    HttpConfiguration config = new HttpConfiguration();
    config.setSecureScheme("https");
    config.setSecurePort(sslPort);
    config.setOutputBufferSize(32786);
    config.setRequestHeaderSize(8192);
    config.setResponseHeaderSize(8192);
    SecureRequestCustomizer customizer = new SecureRequestCustomizer(false);
    HttpConfiguration sslConfiguration = new HttpConfiguration(config);
    sslConfiguration.addCustomizer(new SecureRequestCustomizer(false));
    HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(sslConfiguration);

    ServerConnector connector = new ServerConnector(
        server, sslConnectionFactory, httpConnectionFactory);
    connector.setPort(sslPort);
    server.addConnector(connector);

    ///////////////////////////////////////////////////
    // This part configures the context handlers for HTTP and WebSocket

    // HTTP Context
    ContextHandler httpContext = new ContextHandler();
    httpContext.setContextPath("/");
    httpContext.setResourceBase(".");
    httpContext.setClassLoader(Thread.currentThread().getContextClassLoader());

    ServletContextHandler contextHandler = new ServletContextHandler();
    contextHandler.addServlet(AuthServlet.class, "/auth");
    httpContext.setHandler(contextHandler);

    // WebSocket Context
    ServletContextHandler wsContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
    wsContext.setContextPath("/");

    // Initialize javax.websocket layer
    JakartaWebSocketServletContainerInitializer.configure(
        wsContext, (servletContext, wsContainer) -> {
          // This lambda will be called at the appropriate place in the
          // ServletContext initialization phase where you can initialize
          // and configure your websocket container.

          // Configure defaults for container
          wsContainer.setDefaultMaxTextMessageBufferSize(65535);

          // Add WebSocket endpoint to javax.websocket layer
          wsContainer.addEndpoint(ChatEndpoint.class);
        }); // Add both context handlers to the server
    HandlerCollection handlerCollection = new HandlerCollection();
    handlerCollection.setHandlers(new Handler[] { httpContext, wsContext });
    server.setHandler(handlerCollection);

    try {
      server.start();
      // server.dump(System.err);
      server.join();
    } catch (Throwable t) {
      t.printStackTrace(System.err);
    }
  }
}
