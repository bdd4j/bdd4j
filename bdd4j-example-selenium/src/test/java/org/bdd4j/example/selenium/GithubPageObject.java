package org.bdd4j.example.selenium;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * A page object that can be used to interact with the github page.
 */
public class GithubPageObject implements AutoCloseable
{
  private final BrowserWebDriverContainer<?> container;
  private final RemoteWebDriver driver;

  /**
   * Creates a new instance.
   *
   * @param container The container that should be used by this instance.
   */
  public GithubPageObject(final BrowserWebDriverContainer<?> container)
  {
    this(container, container.getWebDriver());
  }

  /**
   * Creates a new instance.
   *
   * @param container The container that should be used by this instance.
   * @param driver    The driver that should be used.
   */
  protected GithubPageObject(final BrowserWebDriverContainer<?> container,
                             final RemoteWebDriver driver)
  {
    this.container = container;
    this.driver = driver;
  }

  /**
   * Opens the landing page.
   */
  public void openLandingPage()
  {
    driver.get("https://github.com");
  }

  /**
   * Checks whether the browser is on the given url.
   *
   * @param expectedURL The expected URL.
   */
  public void shouldBeOnTheURL(final String expectedURL)
  {
    Assertions.assertThat(driver.getCurrentUrl()).isEqualTo(expectedURL);
  }

  /**
   * Retrieves the landing page object.
   *
   * @return The landing page object.
   */
  public GithubSearchPageObject landingPage()
  {
    return new GithubSearchPageObject(driver);
  }

  /**
   * Retrieves the search results page.
   *
   * @return The search results page.
   */
  public GithubSearchResultPageObject searchResultsPage()
  {
    return new GithubSearchResultPageObject(driver);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close()
  {
    container.close();
  }
}
