package net.efrei.s6.databases.aurelducyoni.utils;

import java.util.Collection;
import java.util.Map;

public class JSON {

    private JSON() {}

    @SuppressWarnings("unchecked")
    public static String stringify(Map<String, ?> object) {
        StringBuilder result = new StringBuilder();
        result.append('{');

        for (Map.Entry<String, ?> entry : object.entrySet()) {
            if (result.length() > 1)
                result.append(',');

            result.append(stringify(entry.getKey()));
            result.append(':');
            if (entry.getValue() == null)
                result.append("null");
            else if (entry.getValue() instanceof Map)
                result.append(stringify((Map<String, ?>) entry.getValue()));
            else if (entry.getValue() instanceof Collection)
                result.append(stringify((Collection<?>) entry.getValue()));
            else if (entry.getValue() instanceof Number)
                result.append(stringify((Number) entry.getValue()));
            else if (entry.getValue() instanceof String)
                result.append(stringify((String) entry.getValue()));
            else if (entry.getValue() instanceof Boolean)
                result.append(stringify((Boolean) entry.getValue()));
            else
                result.append("{/* Unsupported JSON class ").append(entry.getValue().getClass()).append(" */}");
        }

        result.append('}');
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public static String stringify(Collection<?> array) {
        StringBuilder result = new StringBuilder();
        result.append('[');

        for (Object item : array) {
            if (result.length() > 1)
                result.append(',');

            if (item == null)
                result.append("null");
            else if (item instanceof Map)
                result.append(stringify((Map<String, ?>) item));
            else if (item instanceof Collection)
                result.append(stringify((Collection<?>) item));
            else if (item instanceof Number)
                result.append(stringify((Number) item));
            else if (item instanceof String)
                result.append(stringify((String) item));
            else if (item instanceof Boolean)
                result.append(stringify((Boolean) item));
            else
                result.append("{/* Unsupported JSON class ").append(item.getClass()).append(" */}");
        }

        result.append(']');
        return result.toString();
    }

    public static String stringify(String string) {
        if (string == null)
            return "null";

        StringBuilder result = new StringBuilder();
        result.append('\"');

        for (char c : string.toCharArray()) {
            switch (c) {
                case '\\':
                    result.append("\\\\");
                    break;
                case '"':
                    result.append("\\\"");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                default:
                    if (c < ' ') {
                        result.append("\\u");

                        String hex = Integer.toHexString(c);
                        for (int i = hex.length(); i < 4; i++)
                            result.append('0');
                        result.append(hex);
                        break;
                    }

                    result.append(c);
                    break;
            }
        }

        result.append('\"');
        return result.toString();
    }

    public static String stringify(Number number) {
        if (number == null)
            return "null";

        if (number.doubleValue() % 1 == 0)
            return Integer.toString(number.intValue());

        return Double.toString(number.doubleValue());
    }

    public static String stringify(Boolean bool) {
        if (bool == null)
            return "null";

        return Boolean.toString(bool);
    }

}
