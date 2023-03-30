package org.bdd4j;

import org.junit.jupiter.api.TestReporter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class simplifies building and processing report entries with BDD4j specific
 * fields.
 */
public final class BDD4jReportEntry
{
    private static final String STEP_FIELD = "step";
    private static final String TYPE_FIELD = "type";

    private static final String ERROR_MESSAGE_FIELD = "error_message";

    private static final String EXECUTION_TIME_FIELD = "execution_time_in_ms";

    private final Map<String, String> entry;

    /**
     * Creates a new instance.
     *
     * @param entry The entry data.
     */
    public BDD4jReportEntry(final Map<String, String> entry)
    {
        this.entry = new ConcurrentHashMap<>(entry);
    }

    /**
     * Retrieves the step description.
     *
     * @return The description of the step or {@link Optional#empty()} if there is none..
     */
    public Optional<String> step()
    {
        return Optional.ofNullable(entry.getOrDefault(STEP_FIELD, null));
    }

    /**
     * Retrieves the type of the event.
     *
     * @return The type of the event or {@link Optional#empty()} if there is none.
     */
    public Optional<TestEventType> type()
    {
        return Optional.ofNullable(entry.getOrDefault(TYPE_FIELD, null))
                       .map(TestEventType::valueOf);
    }

    /**
     * Retrieves the error message of the event.
     *
     * @return The error message of the event or {@link Optional#empty()} if there is none.
     */
    public Optional<String> errorMessage()
    {
        return Optional.ofNullable(entry.getOrDefault(ERROR_MESSAGE_FIELD, null));
    }

    /**
     * Retrieves the execution time of the event.
     * The unit of the value is milliseconds.
     *
     * @return The execution time of the event or {@link Optional#empty()} if there is none.
     */
    public Optional<Long> executionTime()
    {
        return Optional.ofNullable(entry.getOrDefault(EXECUTION_TIME_FIELD, null))
                       .map(Long::valueOf);
    }

    /**
     * Creates a copy of the report entries data and returns it in a map structure.
     *
     * @return The data.
     */
    public Map<String, String> asMap()
    {
        return new ConcurrentHashMap<>(entry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "BDD4jReportEntry{" + "type=" + type() + "\n" + "step=" + step() + "\n" +
                "executionTime=" + executionTime() + "\n" + "errorMessage=" + errorMessage() + "\n" + '}';
    }

    /**
     * Creates a builder for a new entry.
     *
     * @return The builder.
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * A builder that can be used to build entries for the {@link TestReporter}
     */
    public static class Builder
    {
        private final Map<String, String> entry = new ConcurrentHashMap<>();

        /**
         * Copies the data stored in the given map to the builder.
         *
         * @param data The data that should be copied.
         * @return The builder.
         */
        public Builder copy(final Map<String, String> data)
        {
            entry.putAll(data);

            return this;
        }

        /**
         * Adds a new property to the entry.
         *
         * @param key   The key.
         * @param value The value.
         * @return The builder.
         */
        public Builder with(final String key, final String value)
        {
            entry.put(key, value);
            return this;
        }

        /**
         * Sets the step description.
         *
         * @param step The step.
         * @return The builder.
         */
        public Builder step(final String step)
        {
            return with(STEP_FIELD, step);
        }

        /**
         * Sets the type of the entry.
         *
         * @param type The type.
         * @return The builder.
         */
        public Builder type(final TestEventType type)
        {
            return with(TYPE_FIELD, type.name());
        }

        /**
         * Sets the type to {@link TestEventType#STEP_EXECUTION_STARTED}
         *
         * @return The builder.
         */
        public Builder stepExecutionStarted()
        {
            return type(TestEventType.STEP_EXECUTION_STARTED);
        }

        /**
         * Sets the type to {@link TestEventType#STEP_EXECUTION_FAILED}
         *
         * @return The builder.
         */
        public Builder stepExecutionFailed()
        {
            return type(TestEventType.STEP_EXECUTION_FAILED);
        }

        /**
         * Sets the type to {@link TestEventType#STEP_EXECUTION_COMPLETED}
         *
         * @return The builder.
         */
        public Builder stepExecutionCompleted()
        {
            return type(TestEventType.STEP_EXECUTION_COMPLETED);
        }

        /**
         * Sets the error message to the entry.
         *
         * @param errorMessage The error message.
         * @return The builder.
         */
        public Builder errorMessage(final String errorMessage)
        {
            return with(ERROR_MESSAGE_FIELD, errorMessage);
        }

        /**
         * Sets the execution time.
         *
         * @param executionTimeInMs The execution time in milliseconds.
         * @return The builder.
         */
        public Builder executionTime(final Long executionTimeInMs)
        {
            return with(EXECUTION_TIME_FIELD, String.valueOf(executionTimeInMs));
        }

        /**
         * Builds the entry.
         *
         * @return The entry.
         */
        public BDD4jReportEntry build()
        {
            return new BDD4jReportEntry(entry);
        }
    }
}
