package Utils;

import Types.LiNum;
import Types.LiString;
import Types.Named;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;

public class Functions {

    public static String toLowerCase(String str) {
        return str.toLowerCase();
    }

    public static void checkLength(LinkedList<String> lines, int length) {
        if (lines.size() != length) {
            throw new IllegalArgumentException("Invalid number of arguments: " + lines);
        }
    }

    public static LinkedList<String> lines(String code, int level) {
        String[] split = code.split(Data.SPLITS.get(level).toString());
        return new LinkedList<>(Arrays.asList(split));
    }

    public static LiNum parseNum(LinkedList<String> lines, Variables vars) {
        Functions.checkLength(lines, 2);
        String name = lines.poll();
        String value = lines.poll();
        return new LiNum(name, value, vars);
    }

    public static LiString parseString(LinkedList<String> lines, Variables vars) {
        Functions.checkLength(lines, 2);
        String name = lines.poll();
        String value = lines.poll();
        return new  LiString(name, value, vars);
    }

    public static void let(LinkedList<String> lines, Variables vars) {
        Functions.checkLength(lines, 2);

        String name = lines.poll();
        if (!vars.containsKey(name)) {
            throw new IllegalArgumentException("Undefined variable name: " + name);
        }
        Named original = vars.get(name);
        String value = lines.poll();

        if (original instanceof LiNum) {
            vars.put(name, new LiNum(name, value, vars));
        } else if (original instanceof LiString) {
            vars.put(name, new LiString(name, value, vars));
        }
    }

    public static void print(LinkedList<String> lines, Variables vars) {
        Functions.checkLength(lines, 1);

        String name = lines.poll();
        if (!vars.containsKey(name)) {
            System.out.println(name);
            return;
        }
        System.out.println(vars.get(name).toLiString().toString());
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

    public static Variables parse(String code, Map<String, BiConsumer<LinkedList<String>, Variables>> Parsers, Variables vars, int level) {

        LinkedList<String> lines = Functions.lines(code, level);
        LinkedList<String> current = new LinkedList<>();

        while (!lines.isEmpty()) {

            String key = lines.poll();
            if (!Data.KEYLENGTHS.containsKey(key)) {
                throw new IllegalArgumentException("Invalid key: " + key);
            }

            int length = Data.KEYLENGTHS.get(key);
            for (int i = 0; i < length; i++) {
                current.add(lines.poll());
            }

            Parsers.get(key).accept(current, vars);
            current.clear();
        }

        return vars;
    }

}
