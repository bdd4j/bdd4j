package org.bdd4j.example.postgresql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bdd4j.StepDSL.given;
import static org.bdd4j.StepDSL.then;
import static org.bdd4j.StepDSL.when;

import java.util.UUID;
import org.bdd4j.BDD4jSteps;
import org.bdd4j.Given;
import org.bdd4j.TestState;
import org.bdd4j.Then;
import org.bdd4j.When;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * The steps used to test the book store.
 */
public final class BookStoreTestSteps implements BDD4jSteps<BookStoreState> {

  /**
   * {@inheritDoc}
   */
  @Override
  public TestState<BookStoreState> init() {
    final var state = new BookStoreState(
        new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("book-store")
            .withUsername("book-manager")
            .withPassword(UUID.randomUUID().toString()));

    state.start();
    state.applySchemaMigrations();

    return TestState.state(state);
  }

  public Given<BookStoreState> givenThatNoAuthorsExist() {
    return given("that no authors exist").step(TestState::state);
  }

  public When<BookStoreState> whenITryToCreateTheBook(final int id,
                                                      final String bookName,
                                                      final int author) {
    return when("I try to create the book").step(
        state -> {
          state.bookRepository().create(new BooksRecord(id, bookName, author));
          return TestState.state(state);
        });
  }

  public Then<BookStoreState> thenCreatingTheBookShouldHaveFailedWithTheMessage(
      final String expectedMessage) {
    return then("creating the book should have failed with the message '{0}'",
        expectedMessage).step(state -> {
      assertThat(state.exception()).hasMessage(expectedMessage);
      return state;
    });
  }
}
