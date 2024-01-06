package org.bdd4j.internal;

import java.util.Collection;
import java.util.List;

/**
 * A utility class that defines the cucumber keywords.
 */
public final class CucumberKeywords {
  public static final String FEATURE = "Feature";


  public static final String SCENARIO = "Scenario";

  public static final String SCENARIO_OUTLINE = "Scenario Outline";

  public static final String GIVEN = "Given";

  public static final String WHEN = "When";

  public static final String THEN = "Then";

  public static final String AND = "And";

  public static final Collection<String> KEYWORDS =
      List.of(FEATURE, SCENARIO, SCENARIO_OUTLINE, GIVEN, WHEN, THEN, AND);

  /**
   * Private constructor to prevent instantiation.
   */
  private CucumberKeywords() {

  }
}
