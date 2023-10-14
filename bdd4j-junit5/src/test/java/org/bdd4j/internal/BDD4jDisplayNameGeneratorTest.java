package org.bdd4j.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.bdd4j.api.Feature;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;
import org.bdd4j.internal.BDD4jDisplayNameGenerator;
import org.junit.jupiter.api.Test;

class BDD4jDisplayNameGeneratorTest {

  @Test
  public void generateDisplayNameForClass_withAnnotation() {
    final var generator = new BDD4jDisplayNameGenerator();

    final var displayName = generator.generateDisplayNameForClass(TestWithAnnotation.class);

    assertThat(displayName).isEqualTo("Feature: Cool feature");
  }

  @Test
  public void generateDisplayNameForClass_withoutAnnotation() {
    final var generator = new BDD4jDisplayNameGenerator();

    final var displayName = generator.generateDisplayNameForClass(TestWithoutAnnotation.class);

    assertThat(displayName).isEqualTo("Feature: TestWithoutAnnotation");
  }

  @Test
  public void generateDisplayNameForNestedClass_withAnnotation() {
    final var generator = new BDD4jDisplayNameGenerator();

    final var displayName = generator.generateDisplayNameForNestedClass(TestWithAnnotation.class);

    assertThat(displayName).isEqualTo("Feature: Cool feature");
  }

  @Test
  public void generateDisplayNameForNestedClass_withoutAnnotation() {
    final var generator = new BDD4jDisplayNameGenerator();

    final var displayName =
        generator.generateDisplayNameForNestedClass(TestWithoutAnnotation.class);

    assertThat(displayName).isEqualTo("Feature: TestWithoutAnnotation");
  }

  @Test
  public void generateDisplayNameForMethod_withAnnotation() throws Throwable {
    final var generator = new BDD4jDisplayNameGenerator();

    final var testClass = TestWithAnnotation.class;

    final var method = testClass.getMethod("doTheThing");

    final var displayName = generator.generateDisplayNameForMethod(testClass, method);

    assertThat(displayName).isEqualTo("Scenario: Do the thing");
  }

  @Test
  public void generateDisplayNameForMethod_withoutAnnotation() throws Throwable {
    final var generator = new BDD4jDisplayNameGenerator();

    final var testClass = TestWithoutAnnotation.class;

    final var method = testClass.getMethod("doTheThing");

    final var displayName = generator.generateDisplayNameForMethod(testClass, method);

    assertThat(displayName).isEqualTo("Scenario: doTheThing");
  }

  @Test
  public void generateDisplayNameForParameterizedMethod() throws Throwable {
    final var generator = new BDD4jDisplayNameGenerator();

    final var testClass = ParameterizedTest.class;

    final var method = testClass.getMethod("doTheParameterizedThing");

    final var displayName = generator.generateDisplayNameForMethod(testClass, method);

    assertThat(displayName).isEqualTo("Scenario: Do the parameterized thing");
  }

  @Feature("Cool feature")
  private static class TestWithAnnotation {
    @Scenario("Do the thing")
    public void doTheThing() {
    }
  }

  @Feature("Parameterized very cool feature")
  private static class ParameterizedTest {

    @ScenarioOutline("Do the parameterized thing")
    public void doTheParameterizedThing() {

    }
  }

  private static class TestWithoutAnnotation {

    public void doTheThing() {
    }
  }
}