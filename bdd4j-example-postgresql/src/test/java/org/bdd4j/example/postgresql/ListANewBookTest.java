package org.bdd4j.example.postgresql;

import org.bdd4j.api.Feature;
import org.bdd4j.api.ScenarioOutline;
import org.bdd4j.api.ScenarioOutlineSpec;
import org.bdd4j.api.UserStory;

@Feature("List a new book")
@UserStory("""
    As a bookstore clerk
    I should be able to list a new books
    So that my users can find the books I have in store.
    """)
public class ListANewBookTest {
  private static final String POSTGRES_VERSIONS =
      """
          | postgres version |
          | 16.0             |
          | 15.4             |
          | 14.9             |
          | 13.12            |
          """;

  @ScenarioOutline(description = "The author does not exist",
      data = POSTGRES_VERSIONS)
  public ScenarioOutlineSpec<BookStoreTestSteps, BookStoreState> theAuthorDoesNotExist() {
    return (builder, steps, row) ->
        builder.withParameter(
                TestConstants.POSTGRES_VERSION_KEY, row.getString("postgres version"))
            .defineScenario(
                steps.givenThatNoAuthorsExist(),
                steps.whenITryToListTheNewBook(1,
                    "How bdd4j changed my life and other made-up stories",
                    1),
                steps.thenListingTheNewBookShouldHaveFailedWithTheMessage(
                    "The author with the ID 1 does not exist")
            );
  }

  @ScenarioOutline(
      description = "Successfully list a book",
      data = POSTGRES_VERSIONS
  )
  public ScenarioOutlineSpec<BookStoreTestSteps, BookStoreState> successfullyListABook() {
    return (builder, steps, row) ->
        builder.withParameter(TestConstants.POSTGRES_VERSION_KEY, row.getString("postgres version"))
            .defineScenario(
                steps.givenThatTheAuthorExists(1, "Richard Bachmann"),
                steps.whenITryToListTheNewBook(1,
                    "How bdd4j changed my life and other made-up stories",
                    1),
                steps.thenListingTheNewBookShouldHaveSucceeded()
            );
  }
}
