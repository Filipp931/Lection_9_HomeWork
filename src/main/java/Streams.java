
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public class Streams<E, T extends Collection<E>> {
    private final T collection;

    private Streams(T collection) {
        this.collection = collection;
    }

    /**
     * Получение объекта Streams<> из коллеции
     * @param collection - коллекция, из которой получаем
     * @param <E> - параметр коллекции
     * @param <T> - класс коллекции
     * @return  new Streams<>
     */
    public static<E, T extends Collection<E>> Streams<E, T> of(T collection) {
        return new Streams<>(collection);
    }

    /**
     * Фильтр к текущему Streams
     * @param predicate - условие фильтрации
     * @return new Streams<>, содержащий отфильтрованную коллекцию
     */
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

    /**
     * Преобразует элементы Streams в другие
     * @param function - функция преобразования
     * @param <R> Параметр результирующей коллекции
     * @param <C> Коллекция, параметризованная R
     * @return new Streams<>, содержащий измененную коллекцию
     */
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

    /**
     * преобразует Streams в Map
     * @param keyFunction функция получения ключа
     * @param valueFunction функция получения значения
     * @param <R> - тип ключа
     * @param <V> - тип значения
     * @return Map<R,V>
     */
    public <R, V> Map<R,V> toMap(Function<E, R> keyFunction, Function<E, V> valueFunction) {
        Map<R, V> map = new HashMap<>();
        this.collection.forEach(element -> map.put(keyFunction.apply(element), valueFunction.apply(element)));
        return map;
    }

    public T getCollection() {
        return collection;
    }
}