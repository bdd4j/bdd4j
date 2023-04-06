package org.bdd4j.example.selenium;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A page object for the github search results.
 */
public class GithubSearchResultPageObject {
  private final WebDriver driver;

  /**
   * Creates a new instance.
   *
   * @param driver The driver that should be used by this instance.
   */
  public GithubSearchResultPageObject(final WebDriver driver) {
    this.driver = driver;
  }

  /**
   * Checks whether the page contains the given URL.
   *
   * @param expectedURL The URL that should be checked.
   */
  public void shouldContainALinkTo(final String expectedURL) {
    if (driver.findElements(By.tagName("a")).stream().map(extractHref())
        .noneMatch(urlMatcher(expectedURL))) {
      Assertions.fail("Missing link to " + expectedURL);
    }
  }

  /**
   * Extracts the href attributes value.
   *
   * @return The mapping function.
   */
  private static Function<WebElement, String> extractHref() {
    return element -> element.getAttribute("href");
  }

  /**
   * Builds a predicate that checks if two URLs match.
   *
   * @param otherURL The other URL.
   * @return The predicate.
   */
  private static Predicate<String> urlMatcher(final String otherURL) {
    return url -> Objects.equals(url, otherURL);
  }
}
