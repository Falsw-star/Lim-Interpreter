package Utils;

@FunctionalInterface
public interface Parser<T, U, V> {
    void parse(T t, U u, V v);
}