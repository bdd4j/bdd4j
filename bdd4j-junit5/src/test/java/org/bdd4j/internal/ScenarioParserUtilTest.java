package org.bdd4j.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class ScenarioParserUtilTest {

  @Test
  public void splitScenarios() {
    final String input = """
        Feature: Guess the word
                
          Scenario: Maker starts a game
            When the Maker starts a game
            Then the Maker waits for a Breaker to join
                
          Scenario: Breaker joins a game
            Given the Maker has started a game with the word "silky"
            When the Breaker joins the Maker's game
            Then the Breaker must guess a word with 5 characters
        """;

    final Collection<String> actual = ScenarioParserUtil.splitScenarios(input);

    final Collection<String> expected = Arrays.asList(
        """          
            Scenario: Maker starts a game
            When the Maker starts a game
            Then the Maker waits for a Breaker to join
            """,
        """
            Scenario: Breaker joins a game
            Given the Maker has started a game with the word "silky"
            When the Breaker joins the Maker's game
            Then the Breaker must guess a word with 5 characters
            """
    );

    assertThat(actual).isEqualTo(expected);
  }
}