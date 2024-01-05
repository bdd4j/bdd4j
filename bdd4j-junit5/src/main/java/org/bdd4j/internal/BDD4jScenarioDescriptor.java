package org.bdd4j.internal;

import java.lang.reflect.Method;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class BDD4jScenarioDescriptor extends AbstractTestDescriptor {

  private final Method method;

  public BDD4jScenarioDescriptor(final UniqueId uniqueId,
                                 final String displayName,
                                 Method method) {
    super(uniqueId, displayName);
    this.method = method;
  }

  @Override
  public Type getType() {
    return Type.TEST;
  }

  public Method getMethod() {
    return method;
  }
}
