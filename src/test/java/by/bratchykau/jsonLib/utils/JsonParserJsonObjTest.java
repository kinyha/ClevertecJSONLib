package by.bratchykau.jsonLib.utils;

import by.bratchykau.jsonLib.models.Person;
import by.bratchykau.jsonLib.models.PersonAnother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserJsonObjTest {

    @Test
    void checkParseJsonToMap() {
        String json = "{\"name\":\"John Smith\",\"age\":35}";
        assertTrue(JsonParserJsonObj.parseJsonToMap(json).containsKey("\"name\""));
        assertTrue(JsonParserJsonObj.parseJsonToMap(json).containsKey("\"age\""));
    }

    @Test
    void checkParseJsonFromJsonForObject() {
        String json = "{\"name\":\"John Smith\",\"age\":35}";

        Person person = (Person) JsonParserJsonObj.parseJsonFromJson(json, Person.class);

        assertEquals("John Smith", person.getName());
        assertEquals(35, person.getAge());
    }

    @Test
    void checkParseJsonFromJsonForAnotherObject() {
        String json = "{\"name\":\"John Smith\",\"age\":35,\"isMarried\":true,\"phones\":[\"123\",\"456\"]}";

        PersonAnother person = (PersonAnother) JsonParserJsonObj.parseJsonFromJson(json, PersonAnother.class);

        assertEquals("John Smith", person.getName());
        assertEquals(35, person.getAge());
        assertTrue(person.isMarried());
    }

    @Test
    public void testParseJsonToMap() {
        String json = "{\"name\": \"John Doe\", \"age\": 30, \"isMarried\": true, \"address\": {\"street\": \"123 Main St\", \"city\": \"Anytown\", \"state\": \"CA\", \"zip\": \"12345\"}, \"phoneNumbers\": [\"555-555-1234\", \"555-555-5678\"]}";

        Map<String, String> result = JsonParserJsonObj.parseJsonToMap(json);

        assertEquals(7, result.size());
        assertEquals("\"John Doe\"", result.get("\"name\"").trim());
        assertEquals("30", result.get("\"age\"").trim());
        assertEquals("true", result.get("\"isMarried\"").trim());
        assertEquals("{\"street\": \"123 Main St\"", result.get("\"address\"").trim());
    }


    @Test
    public void testParseJsonFromJsonWithNoMatchingConstructor() {
        String json = "{\"name\": \"John Doe\", \"age\": 30, \"isMarried\": true, \"address\": {\"street\": \"123 Main St\", \"city\": \"Anytown\", \"state\": \"CA\", \"zip\": \"12345\"}, \"phoneNumbers\": [\"555-555-1234\", \"555-555-5678\"]}";
        Assertions.assertThrows(IllegalArgumentException.class, () -> JsonParserJsonObj.parseJsonFromJson(json, Person.class));
    }


}