package org.bdd4j.internal;

/**
 * The various event types that are reported by running a scenario.
 */
public enum TestEventType {
  STEP_EXECUTION_STARTED,

  STEP_EXECUTION_COMPLETED,

  STEP_EXECUTION_FAILED,

  FEATURE_METADATA_REPORTED,

  SCENARIO_METADATA_REPORTED,

  INFRASTRUCTURE_REPORTED
}
