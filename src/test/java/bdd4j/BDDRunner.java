package bdd4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BDDRunner
{
  public static void scenario(final Step... steps)
  {
    for (final Step step : steps)
    {
      final LocalDateTime timestamp = LocalDateTime.now();
      System.out.println("> Running step: " + step.getClass().getSimpleName() + " " + step.name());
      step.runnable().run();

      final long executionTime = timestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS);

      System.out.println(
          "> Completed step: " + step.getClass().getSimpleName() + " " + step.name() + " in " +
              executionTime + "ms");
    }

    System.out.println();
  }
}
