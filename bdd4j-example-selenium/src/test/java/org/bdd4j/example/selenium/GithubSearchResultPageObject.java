package org.bdd4j.example.selenium;

import java.util.Objects;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A page object for the github search results.
 */
public class GithubSearchResultPageObject
{
  private final WebDriver driver;

  /**
   * Creates a new instance.
   *
   * @param driver The driver that should be used by this instance.
   */
  public GithubSearchResultPageObject(final WebDriver driver)
  {
    this.driver = driver;
  }

  /**
   * Checks whether the page contains the given URL.
   *
   * @param expectedURL The URL that should be checked.
   */
  public void shouldContainALinkTo(final String expectedURL)
  {
    for (final WebElement link : driver.findElements(By.tagName("a")))
    {
      if (Objects.equals(link.getAttribute("href"), expectedURL))
      {
        return;
      }
    }

    Assertions.fail("Missing link to " + expectedURL);
  }
}
