package Utils;

import java.util.Arrays;
import java.util.LinkedList;

public class Functions {

    public static String toLowerCase(String str) {
        return str.toLowerCase();
    }

    public static LinkedList<String> lines(String code, int level) {
        String[] split = code.split(Data.SPLITS.get(level).toString());
        return new LinkedList<>(Arrays.asList(split));
    }

    public static LinkedList<String> getStrings(char[] value) {
        boolean breakNext = false;
        LinkedList<String> strings = new LinkedList<>();

        if (value[0] == '=') {
            strings.add("");
        }

        StringBuilder current = new StringBuilder();
        for (int i = 0; i < value.length; i++) {

            if (breakNext) {
                breakNext = false;
                continue;
            }

            char c = value[i];

            if (c == '=') {
                strings.add(current.toString());
                current = new StringBuilder();
                current.append(c);
                current.append(value[i + 1]);
                breakNext = true;
                strings.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        strings.add(current.toString());
        return strings;
    }

}
