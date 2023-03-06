package bdd4j;

public record Then(String name, Runnable runnable) implements Step
{
}
