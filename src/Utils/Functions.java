package Utils;

import Types.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Functions {

    public static String wash(String code) {
        char[] c_code = code.toLowerCase().toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : c_code) {
            if (Data.validChars.contains(c)) {
                result.append(c);
            }
        }
        return result.toString();
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

    public static LiNum parseNum(LinkedList<String> lines, Variables vars, int level) {
        Functions.checkLength(lines, 2);
        String name = new LiString("_", lines.poll(), vars).toString();
        String value = lines.poll();
        return new LiNum(name, value, vars);
    }

    public static LiString parseString(LinkedList<String> lines, Variables vars, int level) {
        Functions.checkLength(lines, 2);
        String name = new LiString("_", lines.poll(), vars).toString();
        String value = lines.poll();
        return new  LiString(name, value, vars);
    }

    public static Chunk parseChunk(LinkedList<String> lines, Variables vars, int level) {
        Functions.checkLength(lines, 3);
        String name = lines.poll();
        String params = lines.poll();
        String code = lines.poll();
        return new Chunk(name, params, code, level, vars);
    }

    public static void createNum(LinkedList<String> lines, Variables vars, int level) {
        vars.create(Functions.parseNum(lines, vars, level));
    }

    public static void createString(LinkedList<String> lines, Variables vars, int level) {
        vars.create(Functions.parseString(lines, vars, level));
    }

    public static void createChunk(LinkedList<String> lines, Variables vars, int level) {
        vars.create(Functions.parseChunk(lines, vars, level));
    }

    // public static ManaCradle parseMana(LinkedList<String> lines, Variables vars, int level)

    // public static Interactable parseInteractable(String value, Variables vars, int level)

    public static void let(LinkedList<String> lines, Variables vars, int level) {
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

    public static void print(LinkedList<String> lines, Variables vars, Integer level) {
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

    public static Variables parse(String code, Map<String, Parser<LinkedList<String>, Variables, Integer>> Parsers, int level, Variables vars) {

        LinkedList<String> lines = Functions.lines(code, level);
        LinkedList<String> current = new LinkedList<>();

        while (!lines.isEmpty()) {

            String key = lines.poll();
            if (!Data.KEYLENGTHS.containsKey(key)) {
                throw new IllegalArgumentException("Invalid key: " + key);
            }

            int length = Data.KEYLENGTHS.get(key).apply(vars);
            for (int i = 0; i < length; i++) {
                current.add(lines.poll());
            }

            Parsers.get(key).parse(current, vars, level);
            current.clear();
        }

        return vars;
    }

    public static Variables parse(String code, Map<String, Parser<LinkedList<String>, Variables, Integer>> Parsers, int level) {
        return parse(code, Parsers, level, new Variables());
    }

}
