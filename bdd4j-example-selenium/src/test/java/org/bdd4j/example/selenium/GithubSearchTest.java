package org.bdd4j.example.selenium;

import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.ScenarioBuilder;
import org.bdd4j.UserStory;

@Feature("Search for github projects")
@UserStory("""
    As an anonymous user
    I want to be able to search for github projects on the homepage
    In order to find the projects that I'm interested in.
    """)
public class GithubSearchTest {
  @Scenario("Search for 'bdd4j'")
  public void searchForBDD4j(ScenarioBuilder<GithubSearchSteps> scenarioBuilder) {
    var steps = scenarioBuilder.availableSteps();
    scenarioBuilder.registerSteps(
        steps.whenIOpenTheLandingPage(),
        steps.whenIEnterTheSearchTerm("bdd4j"),
        steps.whenIHitTheEnterKey(),
        steps.thenIShouldBeOnTheSearchResultsPage(),
        steps.thenIShouldFindALinkTo("https://github.com/bdd4j/bdd4j"));
  }
}
