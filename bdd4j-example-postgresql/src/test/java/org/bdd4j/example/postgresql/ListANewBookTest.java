package org.bdd4j.example.postgresql;

import org.bdd4j.api.Feature;
import org.bdd4j.api.ScenarioBuilder;
import org.bdd4j.api.ScenarioOutline;
import org.bdd4j.api.UserStory;
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

    //This manual call is required due to https://github.com/bdd4j/bdd4j/issues/54
    builder.build().run();
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
            steps.thenListingTheNewBookShouldHaveSucceeded()
        );

    //This manual call is required due to https://github.com/bdd4j/bdd4j/issues/54
    builder.build().run();
  }
}
