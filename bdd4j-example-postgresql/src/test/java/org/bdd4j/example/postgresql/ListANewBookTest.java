package org.bdd4j.example.postgresql;

import org.bdd4j.Feature;
import org.bdd4j.ScenarioBuilder;
import org.bdd4j.ScenarioOutline;
import org.bdd4j.UserStory;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Feature("List a new book")
@UserStory("""
    As a bookstore clerk
    I should be able to list a new books
    So that my users can find the books I have in store.
    """)
public class ListANewBookTest {
  @ScenarioOutline("The author does not exist")
  @ArgumentsSource(PostgresVersionProvider.class)
  public void theAuthorDoesNotExist(
      final String postgresVersion,
      final ScenarioBuilder<BookStoreTestSteps, BookStoreState> builder) {
    final var steps = builder.availableSteps();

    builder.withParameter(TestConstants.POSTGRES_VERSION_KEY, postgresVersion).defineSteps(
        steps.givenThatNoAuthorsExist(),
        steps.whenITryToListTheNewBook(1, "How bdd4j changed my life and other made-up stories", 1),
        steps.thenListingTheNewBookShouldHaveFailedWithTheMessage(
            "The author with the ID 1 does not exist")
    );
  }

  @ScenarioOutline("Successfully list a book")
  @ArgumentsSource(PostgresVersionProvider.class)
  public void successfullyListABook(
      final String postgresVersion,
      final ScenarioBuilder<BookStoreTestSteps, BookStoreState> builder) {
    final var steps = builder.availableSteps();

    builder.withParameter(TestConstants.POSTGRES_VERSION_KEY, postgresVersion)
        .defineSteps(
            steps.givenThatTheAuthorExists(1, "Richard Bachmann"),
            steps.whenITryToListTheNewBook(1, "How bdd4j changed my life and other made-up stories",
                1),
            steps.thenListingTheNewBookShouldHaveFailedWithTheMessage(
                "The author with the ID 1 does not exist")
        );
  }
}
