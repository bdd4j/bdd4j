package bdd4j;

public record When(String name, Runnable runnable) implements Step
{
}
