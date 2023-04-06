package org.bdd4j.example.playwright;

import com.microsoft.playwright.Page;
import org.assertj.core.api.Assertions;

/**
 * A page object for the github search results.
 */
public class GithubSearchResultPageObject {
  private final Page page;

  /**
   * Creates a new instance.
   *
   * @param page The driver that should be used by this instance.
   */
  public GithubSearchResultPageObject(final Page page) {
    this.page = page;
  }

  /**
   * Checks whether the page contains the given URL.
   *
   * @param expectedURL The URL that should be checked.
   */
  public void shouldContainALinkTo(final String expectedURL) {
    if (page.locator("a").all().stream()
        .noneMatch(locator -> expectedURL.equals(locator.getAttribute("href")))) {
      Assertions.fail("Missing link to " + expectedURL);
    }
  }
}