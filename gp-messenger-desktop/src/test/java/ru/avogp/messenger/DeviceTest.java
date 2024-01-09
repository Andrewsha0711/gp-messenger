package ru.avogp.messenger;

import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class DeviceTest {
  private static final Logger logger = LoggerFactory.getLogger(DeviceTest.class);
  private static Device device;

  @BeforeAll
  public static void setup() {
    device = new Device();
  }

  @Test
  public void testLoadDeviceInfo() {
    String info = device.toString();
    logger.info(() -> info);
    Assertions.assertNotNull(info);
  }

  @Test
  public void testInitDevice() {
    Assertions.assertNotNull(device);
  }

  @Test
  public void determineOsTest() {
    String osName = (String) System.getProperties().get("os.name");
    logger.info(() -> osName);
    Assertions.assertEquals("Linux", osName);
  }
}
