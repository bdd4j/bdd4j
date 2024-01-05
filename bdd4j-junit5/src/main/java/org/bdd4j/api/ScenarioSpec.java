package org.bdd4j.api;

import java.util.function.BiFunction;

public interface ScenarioSpec<S extends BDD4jSteps<TS>, TS>
    extends BiFunction<ScenarioBuilder<S, TS>, S, BDD4jScenario<TS>> {
}
