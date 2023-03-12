package org.bdd4j;

/**
 * The common interface for objects that represent a step execution event.
 */
public interface StepExecutionEvent extends ScenarioEvent
{
  /**
   * The textual description of the step.
   *
   * @return The textual description of the step.
   */
  String step();
}
