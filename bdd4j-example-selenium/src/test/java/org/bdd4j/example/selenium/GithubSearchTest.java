package org.bdd4j.example.selenium;

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
  @Disabled
  @Scenario("Search for 'bdd4j'")
  public ScenarioSpec<GithubSearchSteps, GithubPageObject> searchForBDD4j() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.whenIOpenTheLandingPage(),
            steps.whenIEnterTheSearchTerm("bdd4j"),
            steps.whenIHitTheEnterKey(),
            steps.thenIShouldBeOnTheSearchResultsPage(),
            steps.thenIShouldFindALinkTo("https://github.com/bdd4j/bdd4j"));
  }
}
