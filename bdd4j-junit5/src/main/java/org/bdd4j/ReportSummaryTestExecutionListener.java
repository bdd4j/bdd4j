package org.bdd4j;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class ReportSummaryTestExecutionListener implements TestExecutionListener
{
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan)
    {
        System.out.printf("found %d containers.%n", testPlan.countTestIdentifiers(TestIdentifier::isContainer));
        System.out.printf("found %d tests.%n", testPlan.countTestIdentifiers(TestIdentifier::isTest));
    }
}
