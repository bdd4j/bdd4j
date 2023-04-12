package org.bdd4j.example.postgresql;

import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.ScenarioBuilder;
import org.bdd4j.UserStory;

@Feature("Create a book")
@UserStory("""
    As a bookstore clerk
    I should be able to create books
    So that my users can find the books I have in store.
    """)
public class CreateBookTest {
  @Scenario("The author does not exist")
  public void doTheThing(final ScenarioBuilder<BookStoreTestSteps> builder) {
    final var steps = builder.availableSteps();

    builder.defineSteps(
        steps.givenThatNoAuthorsExist(),
        steps.whenITryToCreateTheBook(1, "How bdd4j changed my life and other made-up stories", 1),
        steps.thenCreatingTheBookShouldHaveFailedWithTheMessage("Failed to create the book")
    );
  }
}
