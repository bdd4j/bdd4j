package org.bdd4j.internal;

import java.lang.reflect.Method;
import org.bdd4j.api.DataRow;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class BDD4jScenarioOutlineDescriptor extends AbstractTestDescriptor {

  private final Method method;
  private final DataRow row;
  private final Class<?> testClass;

  public BDD4jScenarioOutlineDescriptor(final UniqueId uniqueId,
                                        final String displayName,
                                        final Method method,
                                        final DataRow row,
                                        final Class<?> testClass) {
    super(uniqueId, displayName);
    this.method = method;
    this.row = row;
    this.testClass = testClass;
  }

  @Override
  public Type getType() {
    return Type.TEST;
  }

  public Method getMethod() {
    return method;
  }

  public DataRow getRow() {
    return row;
  }

  public Class<?> getTestClass() {
    return testClass;
  }
}
