package bdd4j;

public record Given(String name,
                    Runnable runnable) implements Step
{
}
