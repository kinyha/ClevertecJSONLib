package by.bratchykau.jsonLib.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class JsonParserJsonObj {

    public static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> result = new HashMap<>();

        int i = 0;
        if (json.charAt(i) == '{') {
            i++;
            skipWhitespaces(json, i);

            // loop through each key-value pair
            while (json.charAt(i) != '}') {
                StringBuilder key = new StringBuilder();
                StringBuilder value = new StringBuilder();

                // read the key
                while (json.charAt(i) != ':') {
                    if (json.charAt(i) != ' ') {
                        key.append(json.charAt(i));
                    }
                        i++;
                }
                i++;
                skipWhitespaces(json, i);

                // read the value
                char c = json.charAt(i);
                if (c == '[') {
                    // value is a nested object
                    int depth = 1;
                    i++;
                    skipWhitespaces(json, i);
                    value.append(c);
                    while (depth > 0) {
                        c = json.charAt(i);
                        if (c == '[') {
                            depth++;
                        } else if (c == ']') {
                            depth--;
                        }
                        value.append(c);
                        i++;
                    }
                } else if (c == '"') {
                    // value is a string
                    i++;
                    while (json.charAt(i) != '"') {
                        value.append(json.charAt(i));
                        i++;
                    }
                    i++; // skip '"'
                } else {
                    // value is a number or boolean
                    while (json.charAt(i) != ',' && json.charAt(i) != '}') {
                            value.append(json.charAt(i));

                        i++;
                    }
                }
                result.put(key.toString(), value.toString());

                if (json.charAt(i) == ',') {
                    i++;
                    skipWhitespaces(json, i);
                }
            }
        }

        return result;
    }

    public static Object parseJsonFromJson(String json, Class<?> clazz) {
        Map<String, String> result = parseJsonToMap(json);
        //delete all " from key
        result = deleteQuotes(result);
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            Object obj = null;
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == result.size()) {
                    boolean match = true;
                    Object[] args = new Object[parameterTypes.length];
                    int i = 0;
                    for (Class<?> parameterType : parameterTypes) {
                        Field[] fields = clazz.getDeclaredFields();
                        fields[i].setAccessible(true);
                        String value = result.get(fields[i].getName());
                        if (value != null && parameterType == String.class) {
                            args[i] = value;
                        } else if (value != null && (parameterType == int.class || parameterType == Integer.class)) {
                            args[i] = Integer.parseInt(value);
                        } else if (value != null && (parameterType == boolean.class || parameterType == Boolean.class)) {
                            args[i] = Boolean.parseBoolean(value);
                        } else if (value != null && (parameterType == long.class || parameterType == Long.class)) {
                            args[i] = Long.parseLong(value);
                        } else if (value != null && (parameterType == double.class || parameterType == Double.class)) {
                            args[i] = Double.parseDouble(value);
                        } else if (value != null && (parameterType == float.class || parameterType == Float.class)) {
                            args[i] = Float.parseFloat(value);
                        } else if (parameterType == List.class) {
                            args[i] = parseToList(value);
                        } else {
                            match = false;
                            break;
                        }
                        i++;
                    }
                    if (match) {
                        obj = constructor.newInstance(args);
                    }
                }
            }
            if (obj == null) {
                throw new IllegalArgumentException("No matching constructor found for class " + clazz.getName() + " and JSON data " + json);
            }

            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Object> parseToList(String value) {
        List<Object> list = new ArrayList<>();

        if (value != null && value.charAt(0) == '[') {
            List<Object> elements = new ArrayList<>();
            String[] elementsString = value.substring(1, value.length() - 1).split(", ");
            for (String elementString : elementsString) {
                if (elementString.length() > 0) {
                    if (elementString.equals("null")) {
                        list.add(null);
                    } else if (elementString.equals("true")) {
                        list.add(true);
                    } else if (elementString.equals("false")) {
                        list.add(false);
                    } else if (elementString.charAt(0) == '"' && elementString.charAt(elementString.length() - 1) == '"') {
                        list.add(elementString.substring(1, elementString.length() - 1));
                    } else if (elementString.indexOf('.') != -1) {
                        list.add(Double.parseDouble(elementString));
                    } else if (elementString.charAt(0) == '{' && elementString.charAt(elementString.length() - 1) == '}') {
                        list.add(parseJsonToMap(elementString));
                    } else {
                        list.add(Integer.parseInt(elementString));
                    }
                }
            }

            // add the element to the list


        }

        return list;
    }

    private static void skipWhitespaces(String json, int i) {
        while (i < json.length() && json.charAt(i) == ' ') {
            i++;
        }
    }

    private static Map<String, String> deleteQuotes(Map<String, String> result) {
        Map<String, String> result2 = new HashMap<>();
        for (Map.Entry<String, String> entry : result.entrySet()) {
            result2.put(entry.getKey().replace("\"", "").replace("]", ""), entry.getValue());
        }
        return result2;
    }
}
