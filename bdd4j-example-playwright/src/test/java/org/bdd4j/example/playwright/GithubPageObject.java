package org.bdd4j.example.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.assertj.core.api.Assertions;

/**
 * A page object that can be used to interact with the github page.
 */
public class GithubPageObject implements AutoCloseable {
  private final Playwright playwright;

  private final Page page;

  /**
   * Creates a new instance.
   *
   * @param playwright The container that should be used by this instance.
   */
  public GithubPageObject(final Playwright playwright) {
    this(playwright, playwright.firefox().launch());
  }

  /**
   * Creates a new instance.
   *
   * @param playwright The playwright instance that should be used by this instance.
   * @param browser    The browser that should be used.
   */
  protected GithubPageObject(final Playwright playwright,
                             final Browser browser) {
    this.playwright = playwright;
    this.page = browser.newPage();
  }

  /**
   * Opens the landing page.
   */
  public void openLandingPage() {
    page.navigate("https://github.com");
  }

  /**
   * Checks whether the browser is on the given url.
   *
   * @param expectedURL The expected URL.
   */
  public void shouldBeOnTheURL(final String expectedURL) {
    Assertions.assertThat(page.url()).isEqualTo(expectedURL);
  }

  /**
   * Retrieves the landing page object.
   *
   * @return The landing page object.
   */
  public GithubSearchPageObject landingPage() {
    return new GithubSearchPageObject(page);
  }

  /**
   * Retrieves the search results page.
   *
   * @return The search results page.
   */
  public GithubSearchResultPageObject searchResultsPage() {
    return new GithubSearchResultPageObject(page);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    playwright.close();
  }
}
