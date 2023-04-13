package org.bdd4j.example.postgresql;

import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.ScenarioBuilder;
import org.bdd4j.UserStory;

@Feature("List a new book")
@UserStory("""
    As a bookstore clerk
    I should be able to list a new books
    So that my users can find the books I have in store.
    """)
public class ListANewBookTest {
  @Scenario("The author does not exist")
  public void theAuthorDoesNotExist(final ScenarioBuilder<BookStoreTestSteps> builder) {
    final var steps = builder.availableSteps();

    builder.defineSteps(
        steps.givenThatNoAuthorsExist(),
        steps.whenITryToListTheNewBook(1, "How bdd4j changed my life and other made-up stories", 1),
        steps.thenListingTheNewBookShouldHaveFailedWithTheMessage(
            "The author with the ID 1 does not exist")
    );
  }

  @Scenario("Successfully list a book")
  public void successfullyListABook(final ScenarioBuilder<BookStoreTestSteps> builder) {
    final var steps = builder.availableSteps();

    builder.defineSteps(
        steps.givenThatTheAuthorExists(1, "Richard Bachmann"),
        steps.whenITryToListTheNewBook(1, "How bdd4j changed my life and other made-up stories", 1),
        steps.thenListingTheNewBookShouldHaveFailedWithTheMessage(
            "The author with the ID 1 does not exist")
    );
  }
}
