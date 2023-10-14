package org.bdd4j.internal;

import org.bdd4j.api.Given;
import org.bdd4j.api.StepVisitor;
import org.bdd4j.api.TestState;
import org.bdd4j.api.Then;
import org.bdd4j.api.When;

/**
 * A visitor that can be used to execute step specific logic.
 *
 * @param <T> The type of the test state.
 */
public final class TestStepVisitor<T> implements StepVisitor<T> {
  private TestState<T> state;

  /**
   * Creates a new instance.
   *
   * @param initialState The initial state of the test.
   */
  public TestStepVisitor(final TestState<T> initialState) {
    this.state = initialState;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final Given<T> step) throws Throwable {
    state = step.applyLogic(state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final When<T> step) throws Throwable {
    state = step.applyLogic(state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final Then<T> step) {
    state = step.applyLogic(state);
  }
}
