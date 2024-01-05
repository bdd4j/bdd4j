package org.bdd4j.internal;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.bdd4j.api.DataRow;
import org.bdd4j.api.Feature;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.commons.support.HierarchyTraversalMode;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

public class BDD4jTestEngine implements TestEngine {

  private static final BDD4jDisplayNameGenerator NAME_GENERATOR = new BDD4jDisplayNameGenerator();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return "bdd4j";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TestDescriptor discover(final EngineDiscoveryRequest request,
                                 final UniqueId uniqueId) {
    final EngineDescriptor engineDescriptor = new EngineDescriptor(uniqueId, "BDD4j");

    request.getSelectorsByType(ClassSelector.class).forEach(selector -> {
      if (selector.getJavaClass().isAnnotationPresent(Feature.class)) {
        final BDD4jFeatureDescriptor feature = new BDD4jFeatureDescriptor(
            UniqueId.root(getId(), UUID.randomUUID().toString()),
            NAME_GENERATOR.generateDisplayNameForClass(selector.getJavaClass()),
            selector.getJavaClass());

        final List<Method> scenarios = ReflectionSupport.findMethods(selector.getJavaClass(),
            (method) -> method.isAnnotationPresent(Scenario.class) &&
                !method.isAnnotationPresent(Disabled.class),
            HierarchyTraversalMode.BOTTOM_UP);

        for (final Method scenario : scenarios) {
          final String scenarioName = ScenarioNameGenerator.generateName(scenario);

          final UniqueId id = UniqueId.root(getId(), scenarioName);

          feature.addChild(new BDD4jScenarioDescriptor(id, scenarioName, scenario));
        }

        final List<Method> scenarioOutlines = ReflectionSupport.findMethods(selector.getJavaClass(),
            (method) -> method.isAnnotationPresent(ScenarioOutline.class) &&
                !method.isAnnotationPresent(Disabled.class),
            HierarchyTraversalMode.BOTTOM_UP);

        for (final Method scenario : scenarioOutlines) {
          final String scenarioName = ScenarioNameGenerator.generateName(scenario);
          final Collection<DataRow> rows = getDataRows(scenario);

          for (final DataRow row : rows) {
            final UniqueId id = UniqueId.root(getId(), scenarioName + " " + row);

            feature.addChild(new BDD4jScenarioOutlineDescriptor(id, scenarioName, scenario, row));
          }
        }

        engineDescriptor.addChild(feature);
      }
    });

    return engineDescriptor;
  }

  private Collection<DataRow> getDataRows(final Method scenario) {
    return new TableParser().parseTable(scenario.getAnnotation(ScenarioOutline.class).data());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final ExecutionRequest request) {
    new BDD4jTestExecutor().execute(request, request.getRootTestDescriptor());
  }
}
