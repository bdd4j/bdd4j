package org.bdd4j.example.playwright;

import com.microsoft.playwright.Page;

/**
 * A page object for the github search.
 */
public class GithubSearchPageObject {
  private final Page page;

  /**
   * Creates a new instance.
   *
   * @param page The page used by this instance.
   */
  public GithubSearchPageObject(final Page page) {
    this.page = page;
  }


  /**
   * Enters a search term.
   *
   * @param searchTerm The search term.
   */
  public void enterSearchTerm(final String searchTerm) {
    page.locator("[name=q]").first().type(searchTerm);
  }

  /**
   * Hits the enter key.
   */
  public void hitTheEnterKey() {
    page.locator("[name=q]").first().press("Enter");
  }
}