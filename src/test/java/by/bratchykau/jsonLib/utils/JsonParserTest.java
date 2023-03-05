package by.bratchykau.jsonLib.utils;

import by.bratchykau.jsonLib.models.Person;
import by.bratchykau.jsonLib.models.Root;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonParserTest {

    @Test
    void checkToJsonObject() throws IllegalAccessException {
        Person p1 = new Person("John Doe", 30);

        String json = JsonParser.toJson(p1);

        String expected = "{\"name\":\"John Doe\",\"age\":30}";

        assertEquals(expected, json);
    }

    @Test
    void checkToJsonComplexObject() throws IllegalAccessException {
        Person p1 = new Person("John Doe", 30);
        Person p2 = new Person("Elise", 25);
        Person p3 = new Person("Nikita", 35);
        Person p4 = new Person("Janifer", 30);
        Person p5 = new Person("Dimas", 67);

        Root root = new Root("Worker", List.of(p1, p2, p3, p4, p5));


        String expected = "{\"name\":\"Worker\",\"people\":[{\"name\":\"John Doe\",\"age\":30},{\"name\":\"Elise\",\"age\":25},{\"name\":\"Nikita\",\"age\":35},{\"name\":\"Janifer\",\"age\":30},{\"name\":\"Dimas\",\"age\":67}]}";

        String json = JsonParser.toJson(root);
        assertEquals(expected, json);
    }
}