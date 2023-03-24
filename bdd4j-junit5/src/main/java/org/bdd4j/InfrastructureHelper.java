package org.bdd4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A helper class that can be used to gather various information about the infrastructure used to
 * run the tests.
 */
public final class InfrastructureHelper
{
  /**
   * Determines the machines' hostname.
   *
   * @return The hostname.
   */
  public static String determineHostname()
  {
    try
    {
      return InetAddress.getLocalHost().getHostName();
    } catch (final UnknownHostException ignored)
    {
      return "Unknown";
    }
  }

  /**
   * Determines the current username.
   *
   * @return The username.
   */
  public static String determineUsername()
  {
    return System.getProperty("user.name");
  }

  /**
   * Determines the name of the operating system.
   *
   * @return The operating system.
   */
  public static String determineOperatingSystem()
  {
    return System.getProperty("os.name");
  }

  /**
   * Determines the number of CPU cores.
   *
   * @return The number of CPU cores.
   */
  public static Integer determineNumberOfCores()
  {
    return Runtime.getRuntime().availableProcessors();
  }

  /**
   * Determines the java version.
   *
   * @return The java version.
   */
  public static String determineJavaVersion()
  {
    return System.getProperty("java.version");
  }

  /**
   * Determines the used file encoding.
   *
   * @return The file encoding.
   */
  public static String determineFileEncoding()
  {
    return System.getProperty("file.encoding");
  }

  /**
   * Determines the available heap size.
   *
   * @return The heap size.
   */
  public static Long determineHeapSize()
  {
    return Runtime.getRuntime().maxMemory();
  }
}
