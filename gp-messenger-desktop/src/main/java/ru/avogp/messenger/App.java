package ru.avogp.messenger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Device device = new Device();
    private static final Config conf = new Config();

    private Endpoint endpoint;
    private List<Service> services = new ArrayList<>();

    private final Logger logger;

    public App() {
        logger = LogManager.getLogger(App.class);
        setupUI();
        setupSocket();
        bindServicesToSocket();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App();
        });
    }

    private void setupUI() {
        conf.apply();
        new UI(device.getConfig(), s -> services.add(s));
    }

    private void bindServicesToSocket() {
        services.forEach(s -> endpoint.listen(s));
    }

    private void setupSocket() {
        try {
            String dest = "ws://localhost:8080/chat";
            endpoint = new Endpoint();
            logger.info("reg services:" + services.size());
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(endpoint, new URI(dest));
        } catch (DeploymentException e) {
            logger.error("Service unavailable");
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
