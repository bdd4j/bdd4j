package org.bdd4j.internal;

import static java.util.Objects.nonNull;

import java.util.function.Function;

/**
 * A utility class
 */
abstract class Util {
  /**
   * Applies a given function f to a given object 'in' and returns the result.
   * Returns null if the given object 'in' is null.
   *
   * @param in  the generically typed input object
   * @param f   the function to apply on the in object if it is not null
   * @param <I> the type of the in object
   * @param <O> the type of the output of f applied to in.
   * @return the return value of f applied to in if in is not null, otherwise null.
   */
  public static <I, O> O ifNonNullApply(I in, Function<I, O> f) {
    return nonNull(in) ? f.apply(in) : null;
  }
}
