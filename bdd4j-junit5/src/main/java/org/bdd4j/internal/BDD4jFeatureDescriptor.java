package org.bdd4j.internal;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

/**
 * A descriptor for a bdd4j feature.
 */
public class BDD4jFeatureDescriptor extends AbstractTestDescriptor {

  /**
   * Creates a new instance.
   *
   * @param uniqueId    The unique ID.
   * @param displayName The display name.
   */
  public BDD4jFeatureDescriptor(final UniqueId uniqueId,
                                final String displayName) {
    super(uniqueId, displayName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType() {
    return Type.CONTAINER;
  }
}
