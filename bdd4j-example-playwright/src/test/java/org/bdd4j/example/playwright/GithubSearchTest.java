package org.bdd4j.example.playwright;

import org.bdd4j.api.Feature;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioSpec;
import org.bdd4j.api.UserStory;
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
  public ScenarioSpec<GithubSearchSteps, GithubPageObject> searchForBDD4j() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.whenIOpenTheLandingPage(),
            steps.whenIEnterTheSearchTerm("bdd4j"),
            steps.whenIHitTheEnterKey(),
            steps.thenIShouldBeOnTheSearchResultsPage(),
            steps.thenIShouldFindALinkTo("/bdd4j/bdd4j")
        );
  }
}