package org.bdd4j;

import java.util.Collections;
import java.util.List;

public class ScenarioBuilder
{
    private List<Step<?>> steps = Collections.emptyList();

    @SafeVarargs
    public final <S> void addSteps(Step<S>... steps)
    {
        this.steps = List.of(steps);
    }

    public List<Step<?>> steps()
    {
        return steps;
    }
}
