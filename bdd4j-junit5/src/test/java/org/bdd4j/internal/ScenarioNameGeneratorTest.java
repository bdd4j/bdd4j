package org.bdd4j.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ScenarioNameGeneratorTest {

  @Test
  public void generateName_scenario() throws Throwable {
    final Method method = MockTest.class.getMethod("withScenarioAnnotation");

    final String actual = ScenarioNameGenerator.generateName(method);

    assertThat(actual).isEqualTo("scenario description");
  }

  @Test
  public void generateName_scenarioOutline() throws Throwable {
    final Method method = MockTest.class.getMethod("withScenarioOutlineAnnotation");

    final String actual = ScenarioNameGenerator.generateName(method);

    assertThat(actual).isEqualTo("scenario outline description");
  }

  @Test
  public void generateName_withoutAnnotation() throws Throwable {
    final Method method = MockTest.class.getMethod("withoutAnnotation");

    final String actual = ScenarioNameGenerator.generateName(method);

    assertThat(actual).isEqualTo("withoutAnnotation");
  }

  private static class MockTest {
    @Disabled
    @Scenario("scenario description")
    public void withScenarioAnnotation() {
    }

    @Disabled
    @ScenarioOutline(
        description = "scenario outline description",
        data = ""
    )
    public void withScenarioOutlineAnnotation() {
    }

    public void withoutAnnotation() {
    }
  }
}