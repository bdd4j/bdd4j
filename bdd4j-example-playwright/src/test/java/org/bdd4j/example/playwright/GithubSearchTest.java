package org.bdd4j.example.playwright;

import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.ScenarioBuilder;
import org.bdd4j.UserStory;
import org.junit.jupiter.api.Disabled;

@Feature("Search for github projects")
@UserStory("""
    As an anonymous user
    I want to be able to search for github projects on the homepage
    In order to find the projects that I'm interested in.
    """)
public class GithubSearchTest {
  @Disabled("Tests cannot be run as a github action")
  @Scenario("Search for 'bdd4j'")
  public void searchForBDD4j(final ScenarioBuilder<GithubSearchSteps, GithubPageObject> builder) {
    final var steps = builder.availableSteps();

    builder.defineSteps(
        steps.whenIOpenTheLandingPage(),
        steps.whenIEnterTheSearchTerm("bdd4j"),
        steps.whenIHitTheEnterKey(),
        steps.thenIShouldBeOnTheSearchResultsPage(),
        steps.thenIShouldFindALinkTo("/bdd4j/bdd4j")
    );
  }
}