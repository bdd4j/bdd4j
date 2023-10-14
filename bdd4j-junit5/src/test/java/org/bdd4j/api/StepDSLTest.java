package org.bdd4j.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StepDSLTest {
  @Test
  public void given_withMessageFormat() {
    final var step =
        StepDSL.given("that I pass ''{0}'' they should be rendered properly", "parameters")
            .step(TestState::state);

    assertThat(step.description()).isEqualTo(
        "that I pass 'parameters' they should be rendered properly");
  }

  @Test
  public void given_withStaticName() {
    final var step =
        StepDSL.given("that I enter some text")
            .step(TestState::state);

    assertThat(step.description()).isEqualTo(
        "that I enter some text");
  }

  @Test
  public void when_withMessageFormat() {
    final var step =
        StepDSL.when("I pass ''{0}'' they should be rendered properly", "parameters")
            .step(TestState::state);

    assertThat(step.description()).isEqualTo(
        "I pass 'parameters' they should be rendered properly");
  }

  @Test
  public void when_withStaticName() {
    final var step =
        StepDSL.when("I do something")
            .step(TestState::state);

    assertThat(step.description()).isEqualTo("I do something");
  }

  @Test
  public void then_withMessageFormat() {
    final var step =
        StepDSL.then("I should be able to pass ''{0}''", "parameters")
            .step(state -> state);

    assertThat(step.description()).isEqualTo(
        "I should be able to pass 'parameters'");
  }

  @Test
  public void then_withStaticName() {
    final var step =
        StepDSL.then("something should have happened")
            .step(state -> state);

    assertThat(step.description()).isEqualTo(
        "something should have happened");
  }
}