package org.bdd4j.example.selenium;

import org.bdd4j.BDD4jRunner;
import org.bdd4j.BDD4jTest;
import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.UserStory;
import org.junit.jupiter.api.Test;

@BDD4jTest
@Feature("Search for github projects")
@UserStory("""
    As an anonymous user
    I want to be able to search for github projects on the homepage
    In order to find the projects that I'm interested in.
    """)
public class GithubSearchTest
{
  @Scenario("Search for 'bdd4j'")
  @Test
  public void searchForBDD4j(final GithubSearchSteps steps)
  {
    BDD4jRunner.scenario(steps,
        steps.givenThatIOpenTheLandingPage(),
        steps.whenIEnterTheSearchTerm("bdd4j"),
        steps.whenIHitTheEnterKey(),
        steps.thenIShouldBeOnTheSearchResultsPage(),
        steps.thenIShouldFindTheLinkToTheBdd4jProject());
  }
}
