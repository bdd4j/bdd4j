package org.bdd4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bdd4j.StepDSL.then;
import static org.bdd4j.StepDSL.when;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ConditionalStepDescriptionGeneratorTest {

  @Test
  public void singleStep() {
    var generator = new ConditionalStepDescriptionGenerator();

    var step = when("something happens").step(TestState::state);

    var expected = "When something happens";
    var actual = generator.generateStepDescriptionFor(step);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void twoStepsWithTheSameKeyword() {
    var generator = new ConditionalStepDescriptionGenerator();

    var stepOne = when("something happens").step(TestState::state);
    var stepTwo = when("something else happens").step(TestState::state);

    var expectedOne = "When something happens";
    var actualOne = generator.generateStepDescriptionFor(stepOne);

    var expectedTwo = "And something else happens";
    var actualTwo = generator.generateStepDescriptionFor(stepTwo);

    var assertions = new SoftAssertions();

    assertions.assertThat(actualOne).isEqualTo(expectedOne);
    assertions.assertThat(actualTwo).isEqualTo(expectedTwo);

    assertions.assertAll();
  }

  @Test
  public void threeStepsWithAlteringKeywords() {
    var generator = new ConditionalStepDescriptionGenerator();

    var stepOne = when("something happens").step(TestState::state);
    var stepTwo = when("something else happens").step(TestState::state);
    var stepThree = then("something should have happened").step((state) -> state);

    var expectedOne = "When something happens";
    var actualOne = generator.generateStepDescriptionFor(stepOne);

    var expectedTwo = "And something else happens";
    var actualTwo = generator.generateStepDescriptionFor(stepTwo);

    var expectedThree = "Then something should have happened";
    var actualThree = generator.generateStepDescriptionFor(stepThree);

    var assertions = new SoftAssertions();

    assertions.assertThat(actualOne).isEqualTo(expectedOne);
    assertions.assertThat(actualTwo).isEqualTo(expectedTwo);
    assertions.assertThat(actualThree).isEqualTo(expectedThree);

    assertions.assertAll();
  }
}