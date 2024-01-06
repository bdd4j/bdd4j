package org.bdd4j.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.assertj.core.api.SoftAssertions;
import org.bdd4j.api.BDD4jScenario;
import org.bdd4j.api.BDD4jSteps;
import org.bdd4j.api.Given;
import org.bdd4j.api.Parameters;
import org.bdd4j.api.Step;
import org.bdd4j.api.StepDSL;
import org.bdd4j.api.TestState;
import org.bdd4j.api.Then;
import org.bdd4j.api.When;
import org.junit.jupiter.api.Test;

class FeatureParserTest {

  @Test
  public void oneExampleScenario() {
    final var feature = """
        Feature: Cool feature
          Scenario: Awesome scenario
          
            Given that I enter 'cool'
              And that everything is fine and dandy
            When I invoke the feature
            Then the result should be 'awesome'
        """;

    final List<BDD4jScenario<?>> scenarios =
        new FeatureParser(Collections.singleton(ExampleScenarioSteps.class)).parse(feature);

    final ExampleScenarioSteps steps = new ExampleScenarioSteps();

    assertThat(scenarios).hasSize(1);

    final SoftAssertions assertions = new SoftAssertions();

    assertions.assertThat(scenarios.getFirst().steps()).hasSize(4);

    assertions.assertThat(scenarios.getFirst().step(0))
        .hasValueSatisfying((actual) ->
            matchStep(steps.givenThatIEnter("cool"), actual));

    assertions.assertThat(scenarios.getFirst().step(1))
        .hasValueSatisfying((actual) ->
            matchStep(steps.givenThatEverythingIsFineAndDandy(), actual));

    assertions.assertThat(scenarios.getFirst().step(2))
        .hasValueSatisfying((actual) ->
            matchStep(steps.whenIInvokeTheFeature(), actual));

    assertions.assertThat(scenarios.getFirst().step(3))
        .hasValueSatisfying((actual) ->
            matchStep(steps.thenTheResultShouldBe("awesome"), actual));

    assertions.assertAll();
  }

  @Test
  public void twoScenarios() {
    final var feature = """
        Feature: Cool feature
          Scenario: Awesome scenario
          
            Given that I enter 'cool'
              And that everything is fine and dandy
            When I invoke the feature
            Then the result should be 'awesome'
            
          Scenario: Not so awesome scenario
          
            Given that I enter 'not cool'
              And that everything is fine and dandy
            When I invoke the feature
            Then the result should be 'not awesome'
        """;

    final List<BDD4jScenario<?>> scenarios =
        new FeatureParser(Collections.singleton(ExampleScenarioSteps.class)).parse(feature);

    final ExampleScenarioSteps steps = new ExampleScenarioSteps();

    assertThat(scenarios).hasSize(2);

    final SoftAssertions assertions = new SoftAssertions();

    assertions.assertThat(scenarios.getFirst().steps()).hasSize(4);

    assertions.assertThat(scenarios.getFirst().step(0))
        .hasValueSatisfying((actual) ->
            matchStep(steps.givenThatIEnter("cool"), actual));

    assertions.assertThat(scenarios.getFirst().step(1))
        .hasValueSatisfying((actual) ->
            matchStep(steps.givenThatEverythingIsFineAndDandy(), actual));

    assertions.assertThat(scenarios.getFirst().step(2))
        .hasValueSatisfying((actual) ->
            matchStep(steps.whenIInvokeTheFeature(), actual));

    assertions.assertThat(scenarios.getFirst().step(3))
        .hasValueSatisfying((actual) ->
            matchStep(steps.thenTheResultShouldBe("awesome"), actual));

    assertions.assertAll();
  }

  private static void matchStep(final Step<?> expected, final Step<?> actual) {
    final SoftAssertions assertions = new SoftAssertions();

    assertions.assertThat(actual.description()).isEqualTo(expected.description());
    assertions.assertThat(actual).isInstanceOf(expected.getClass());

    assertions.assertAll();
  }

  public static class AwesomeBusinessFeature {
    public static String invoke(final String input) {
      return "cool".equals(input) ? "awesome" : "so not awesome";
    }
  }

  public static class ExampleScenarioSteps implements BDD4jSteps<Map<String, String>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TestState<Map<String, String>> init(final Parameters parameters) {
      return TestState.state(new ConcurrentHashMap<>());
    }

    public Given<Map<String, String>> givenThatIEnter(final String input) {
      return StepDSL.given("that I enter ''{0}''", input)
          .step(state -> {
            state.put("input", input);
            return TestState.state(state);
          });
    }

    public Given<Map<String, String>> givenThatEverythingIsFineAndDandy() {
      return StepDSL.given("that everything is fine and dandy").step(TestState::state);
    }

    public When<Map<String, String>> whenIInvokeTheFeature() {
      return StepDSL.when("I invoke the feature").step(state -> {
        state.put("result",
            AwesomeBusinessFeature.invoke(state.get("input")));

        return TestState.state(state);
      });
    }

    public Then<Map<String, String>> thenTheResultShouldBe(final String expectedResult) {
      return StepDSL.then("the result should be ''{0}''", expectedResult).step(state -> {
        assertThat(state.state().get("result")).isEqualTo(expectedResult);
        return state;
      });
    }
  }
}