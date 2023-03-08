package org.bdd4j;

import java.text.MessageFormat;
import java.util.function.Function;

/**
 * A DSL that can be used to ease building steps.
 */
public class StepDSL
{
  /**
   * A builder method that can be used to create a new given step.
   *
   * @param step A description of the step.
   * @return The builder.
   */
  public static GivenBuilder given(final String step)
  {
    return new GivenBuilder(step);
  }

  /**
   * A builder method that can be used to create a new given step.
   *
   * @param template The template that should be used to describe the step.
   * @param args     The parameters that should be injected into the template.
   * @return The builder.
   */
  public static GivenBuilder given(final String template, Object... args)
  {
    return given(MessageFormat.format(template, args));
  }

  /**
   * A builder method that can be used to create a new when step.
   *
   * @param step A description of the step.
   * @return The builder.
   */
  public static WhenBuilder when(final String step)
  {
    return new WhenBuilder(step);
  }

  /**
   * A builder method that can be used to create a new when step.
   *
   * @param template The template that should be used to describe the step.
   * @param args     The parameters that should be injected into the template.
   * @return The builder.
   */
  public static WhenBuilder when(final String template, final Object... args)
  {
    return when(MessageFormat.format(template, args));
  }

  /**
   * A builder method that can be used to create a new then step.
   *
   * @param step A description of the step.
   * @return The builder.
   */
  public static ThenBuilder then(final String step)
  {
    return new ThenBuilder(step);
  }

  /**
   * A builder method that can be used to create a new then step.
   *
   * @param template The template that should be used to describe the step.
   * @param args     The parameters that should be injected into the template.
   * @return The builder.
   */
  public static ThenBuilder then(final String template, final Object... args)
  {
    return then(MessageFormat.format(template, args));
  }

  /**
   * A builder that can be used to create a {@link Given} step.
   */
  public static class GivenBuilder
  {
    private final String step;

    /**
     * Creates a new instance.
     *
     * @param step The step description.
     */
    private GivenBuilder(final String step)
    {
      this.step = step;
    }

    /**
     * Sets the step logic.
     *
     * @param logic The logic
     * @param <T>   The type of the state.
     * @return The step.
     */
    public <T> Given<T> step(final Function<T, TestState<T>> logic)
    {
      return new Given<>(step, logic);
    }
  }

  /**
   * A builder that can be used to create a {@link When} step.
   */
  public static class WhenBuilder
  {
    private final String step;

    /**
     * Creates a new instance.
     *
     * @param step The step.
     */
    private WhenBuilder(final String step)
    {
      this.step = step;
    }

    /**
     * Sets the step logic for the step.
     *
     * @param logic The logic.
     * @param <T>   The type of the state.
     * @return The step.
     */
    public <T> When<T> step(final Function<T, TestState<T>> logic)
    {
      return new When<>(step, logic);
    }
  }

  /**
   * A builder that can be used to create a {@link Then} step.
   */
  public static class ThenBuilder
  {
    private final String step;

    /**
     * Creates a new instance.
     *
     * @param step The step description.
     */
    private ThenBuilder(final String step)
    {
      this.step = step;
    }

    /**
     * Sets the step logic for the step.
     *
     * @param logic The logic.
     * @param <T>   The type of the state.
     * @return The then step.
     */
    public <T> Then<T> step(final Function<TestState<T>, TestState<T>> logic)
    {
      return new Then<>(step, logic);
    }
  }
}
