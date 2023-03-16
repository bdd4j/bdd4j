package org.bdd4j;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StepDSLTest
{

  @Test
  public void when_withMessageFormat()
  {
    final var step =
        StepDSL.when("I pass ''{0}'' they should be rendered properly", "parameters")
            .step(TestState::state);

    assertThat(step.description()).isEqualTo(
        "I pass 'parameters' they should be rendered properly");
  }
}