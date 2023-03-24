package org.bdd4j;

import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.stream.Collectors;

public class ReportSummaryTestExecutionListener implements TestExecutionListener
{
    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry)
    {
        var keyValuePairs = entry.getKeyValuePairs()
                                 .entrySet()
                                 .stream()
                                 .map(stringStringEntry -> String.format("%s -> %s", stringStringEntry.getKey(),
                                                                         stringStringEntry.getValue()))
                                 .collect(
                                         Collectors.joining("\n"));
        System.out.printf("id %s entry: %s%n", testIdentifier.getDisplayName(), keyValuePairs);
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan)
    {
        System.out.printf("found %d containers.%n", testPlan.countTestIdentifiers(TestIdentifier::isContainer));
        System.out.printf("found %d tests.%n", testPlan.countTestIdentifiers(TestIdentifier::isTest));
    }
}
