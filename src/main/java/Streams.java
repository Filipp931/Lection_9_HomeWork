
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public class Streams<E, T extends Collection<E>> {
    private final T collection;

    private Streams(T collection) {
        this.collection = collection;
    }

    public static<E, T extends Collection<E>> Streams<E, T> of(T collection) {
        return new Streams<>(collection);
    }

    public Streams<E, T> filter(Predicate<? super E> predicate) {
        T col = null;
        try {
            col = (T) this.collection.getClass().getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        T finalCol = col;
        collection.forEach(element -> {
            if(predicate.test(element)) {
                finalCol.add(element);
            }
                });
        return new Streams<>(col);
    }

    public <R, C extends Collection<R>> Streams<R,C> transform(Function<E, R> function) {
        C col = null;
        Iterator<E> iterator = this.collection.iterator();
        try {
            col = (C) collection.getClass().getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (iterator.hasNext()){
            col.add(function.apply(iterator.next()));
        }
        return new Streams<>(col);
    }

    public <R, V> Map<R,V> toMap(Function<E, R> key, Function<E, V> value) {
        Map<R, V> map = new HashMap<>();
        this.collection.forEach(element -> map.put(key.apply(element), value.apply(element)));
        return map;
    }

    public T getCollection() {
        return collection;
    }
}