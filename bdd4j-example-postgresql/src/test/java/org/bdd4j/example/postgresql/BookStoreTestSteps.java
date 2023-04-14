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
import org.bdd4j.example.postgresql.service.CreateAuthorRequest;
import org.bdd4j.example.postgresql.service.ListBookRequest;
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


  public Given<BookStoreState> givenThatTheAuthorExists(final int id,
                                                        final String name) {
    return given("that the author {0} ''{1}'' exist", id, name).step(state -> {
      state.createAuthorService().create(new CreateAuthorRequest(id, name));
      return TestState.state(state);
    });
  }

  public When<BookStoreState> whenITryToListTheNewBook(final int id,
                                                       final String bookName,
                                                       final int author) {
    return when("I try to list the book {0} ''{1}'' {2}", id, bookName, author).step(
        state -> {
          state.createBookService().listBook(new ListBookRequest(id, bookName, author));
          return TestState.state(state);
        });
  }

  public Then<BookStoreState> thenListingTheNewBookShouldHaveFailedWithTheMessage(
      final String expectedMessage) {
    return then("listing the new book should have failed with the message '{0}'",
        expectedMessage).step(state -> {
      assertThat(state.exception()).hasMessage(expectedMessage);
      return state;
    });
  }
}
