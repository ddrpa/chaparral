package cc.ddrpa.chaparral.desensitizer;

/**
 * All desensitizer must implement this interface.
 *
 * @param <T> type of the value it desensitizes
 */
public interface IDesensitizer<T> {
    Object desensitize(T value);
}