import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams<E, T extends Collection<E>> {
    private final T collection;

    private Streams(T collection) {
        this.collection = collection;
    }

    public static<E, T extends Collection<E>> Streams of(T collection) {
        return new Streams<>(collection);
    }

    public Streams<E, T> filter(Predicate<E> predicate) {
        T col = this.getCollection();
        col.removeIf(predicate);
        return new Streams<>(col);
    }

    public <R> Streams transform(Function<E, R> function) {
        Collection<R> col = null;
        try {
            col = collection.getClass().getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        collection.forEach(element -> {
            R temp = function.apply(element);
            col.add(temp);
                });
        return new Streams(col);
    }

    public Map toMap(........) {


    }

    private T getCollection() {
        return collection;
    }
}