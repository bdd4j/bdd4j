package org.bdd4j.api;

/**
 * This is a fake API that can be used to test invocations.
 */
public class SomeTestedApi {
  private int numberOfInvocations = 0;

  public void doTheThing() {
    numberOfInvocations++;
  }

  public int getNumberOfInvocations() {
    return numberOfInvocations;
  }
}
