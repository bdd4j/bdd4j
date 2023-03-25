package org.bdd4j;

/**
 * The various event types that are reported by running a scenario.
 */
public enum TestEventType {
  INFRASTRUCTURE_REPORTED,

  STEP_EXECUTION_STARTED,

  STEP_EXECUTION_COMPLETED,

  STEP_EXECUTION_FAILED
}
