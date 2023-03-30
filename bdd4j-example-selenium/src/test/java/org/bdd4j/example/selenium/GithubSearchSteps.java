package org.bdd4j.example.selenium;

import org.bdd4j.BDD4jSteps;
import org.bdd4j.TestState;
import org.bdd4j.Then;
import org.bdd4j.When;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static org.bdd4j.StepDSL.then;
import static org.bdd4j.StepDSL.when;

/**
 * The steps used to test the github search feature.
 */
public class GithubSearchSteps implements BDD4jSteps<GithubPageObject>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public TestState<GithubPageObject> init()
    {
        final var capabilities = new ChromeOptions();
        final var container =
                new BrowserWebDriverContainer<>().withCapabilities(capabilities);

        container.start();

        return TestState.state(new GithubPageObject(container));
    }

    public When<GithubPageObject> whenIOpenTheLandingPage()
    {
        return when("I open the landing page").step(state -> {
            state.openLandingPage();

            return TestState.state(state);
        });
    }

    public When<GithubPageObject> whenIEnterTheSearchTerm(final String searchTerm)
    {
        return when("I enter the search term ''{0}''", searchTerm).step(state -> {
            state.landingPage()
                 .enterSearchTerm(searchTerm);

            return TestState.state(state);
        });
    }

    public When<GithubPageObject> whenIHitTheEnterKey()
    {
        return when("I hit the enter key").step(state -> {
            state.landingPage()
                 .hitTheEnterKey();

            return TestState.state(state);
        });
    }

    public Then<GithubPageObject> thenIShouldFindALinkTo(final String expectedLink)
    {
        return then("I should find the link to ''{0}''", expectedLink).step(state -> {
            state.state()
                 .searchResultsPage()
                 .shouldContainALinkTo(expectedLink);
            return state;
        });
    }

    public Then<GithubPageObject> thenIShouldBeOnTheSearchResultsPage()
    {
        return then("I should be on the search results page").step(state -> {
            state.state()
                 .shouldBeOnTheURL("https://github.com/search?q=bdd4j&type=");
            try
            {
                state.state()
                     .wait();
            } catch (final InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            return state;
        });
    }
}
