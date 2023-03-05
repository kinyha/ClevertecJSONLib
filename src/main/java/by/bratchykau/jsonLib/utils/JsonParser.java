package by.bratchykau.jsonLib.utils;

import java.lang.reflect.Field;
import java.util.List;

public class JsonParser {

    public static String toJson(Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);

            sb.append("\"").append(f.getName()).append("\":");

            if (isArray(f.getType())) {
                extractedArray(obj, sb);
            } else if (isList(f.getType())) {
                extractedList(obj, sb, f);
            } else {
                extractedPrimitiv(obj, sb, fields, i, f);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static void extractedPrimitiv(Object obj, StringBuilder sb, Field[] fields, int i, Field f) throws IllegalAccessException {
        if (f.getType() == String.class) {
            sb.append("\"").append(f.get(obj)).append("\"");
        } else {
            sb.append(f.get(obj));
        }

        if (i < fields.length - 1) {
            sb.append(",");
        }
    }

    private static void extractedList(Object obj, StringBuilder sb, Field f) throws IllegalAccessException {
        sb.append("[");
        List<?> list = (List<?>) f.get(obj);
        for (int j = 0; j < list.size(); j++) {
            if (j > 0) {
                sb.append(",");
            }
            sb.append(toJson(list.get(j)));
        }
        sb.append("]");
    }

    private static void extractedArray(Object obj, StringBuilder sb) throws IllegalAccessException {
        sb.append("[");
        for (int j = 0; j < java.lang.reflect.Array.getLength(obj); j++) {
            if (j > 0) {
                sb.append(",");
            }
            sb.append(toJson(java.lang.reflect.Array.get(obj, j)));
        }
        sb.append("]");
    }

    private static boolean isList(Class<?> type) {
        return type.getName().startsWith("java.util.List");
    }


    private static boolean isArray(Class<?> type) {
        return type.isArray();
    }

    private static boolean isPrimitive(Class<?> type) {
        return type == String.class || type == Integer.class || type == Double.class || type == Boolean.class;
    }
}

