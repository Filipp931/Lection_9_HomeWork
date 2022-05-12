
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StreamsTest {
    @BeforeEach
    private void print(){
        System.out.println("======================================================");
    }

       static List<String> list = new ArrayList<>();
    static {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");

    }
    @Test
    void of() {
        Object test = Streams.of(list);
        assertEquals(Streams.class, test.getClass());
    }

    @Test
    void filter() {
        System.out.println("Filter Test:");
        List<String> temp = list.stream().filter(element -> element.startsWith("f")).collect(Collectors.toList());
        List result = Streams.of(list).filter(element -> element.startsWith("f")).getCollection();
        result.forEach(System.out::println);
        assertEquals(temp, result);
    }

    @Test
    void transform() {
        System.out.println("Transform Test:");
        List temp = list.stream().filter(element -> element.startsWith("f"))
                .map(element -> element.replace("f", "!"))
                .collect(Collectors.toList());
        List result = (List) Streams.of(list).filter(element -> element.startsWith("f"))
                .transform(element -> element.replace("f", "!"))
                .getCollection();
        result.forEach(System.out::println);
        assertEquals(temp, result);
    }

    @Test
    void mapTest() {
        System.out.println("Map Test:");
        Map temp = list.stream().collect(Collectors.toMap(element -> element.substring(0,3), element -> element));
        Map result = Streams.of(list).toMap(element -> element.substring(0,3), element -> element);
        result.entrySet().forEach(System.out::println);
        assertEquals(temp, result);
    }

    @Test
    void name() {
        Streams.of(list).filter(element -> element.startsWith("f"))
                .transform(element -> element.length())
                .getCollection()
                .forEach(System.out::println);
    }
}