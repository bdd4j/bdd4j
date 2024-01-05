package org.bdd4j.example.postgresql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bdd4j.api.StepDSL.given;
import static org.bdd4j.api.StepDSL.then;
import static org.bdd4j.api.StepDSL.when;

import java.text.MessageFormat;
import java.util.UUID;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.Given;
import org.bdd4j.api.Parameters;
import org.bdd4j.api.Step;
import org.bdd4j.api.TestState;
import org.bdd4j.api.Then;
import org.bdd4j.api.When;
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
  public TestState<BookStoreState> init(final Parameters parameters) {
    final var state = new BookStoreState(
        new PostgreSQLContainer<>(generatePostgresVersionString(parameters))
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

  public Step<BookStoreState> thenListingTheNewBookShouldHaveSucceeded() {
    return then("listing the new book should have succeeded").step(state -> {
      state.raiseExceptionIfPresent();
      final var book = state.state().createBookService().loadBook(1);
      assertThat(book).isPresent();
      return state;
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

  /**
   * Generates the postgres version string for the given parameters
   *
   * @param parameters The parameters that have been provided.
   * @return The generated postgres version string.
   */
  private static String generatePostgresVersionString(Parameters parameters) {
    final String configuredVersion = parameters.getString(TestConstants.POSTGRES_VERSION_KEY);

    return MessageFormat.format("postgres:{0}", configuredVersion);
  }
}
