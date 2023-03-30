package org.bdd4j.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * A page object for the github search.
 */
public class GithubSearchPageObject
{
  private final WebDriver driver;

  /**
   * Creates a new instance.
   *
   * @param driver The driver used by this instance.
   */
  public GithubSearchPageObject(final WebDriver driver)
  {
    this.driver = driver;
  }


  /**
   * Enters a search term.
   *
   * @param searchTerm The search term.
   */
  public void enterSearchTerm(final String searchTerm)
  {
    driver.findElement(By.name("q")).sendKeys(searchTerm);
  }

  /**
   * Hits the enter key.
   */
  public void hitTheEnterKey()
  {
    driver.findElement(By.name("q")).submit();
  }
}
