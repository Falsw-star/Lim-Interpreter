import Types.Num;
import Utils.Data;
import Utils.Functions;
import Utils.Variables;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class Main {

    public static final String TEST_CODE = "num fooI XX num fooII XXX num fooIII fooI=mfooII print fooIII";

    public static Map<String, Consumer<LinkedList<String>>> PARSERS = Map.of(
            "print", Main::parsePrint,
            "num", Main::parseNum
    );

    private static Variables VARS = new Variables();

    private static int level = 1;

    public static void main(String[] args) {
        String lowerCase = TEST_CODE.toLowerCase();
        parseKey(Functions.lines(lowerCase, level));
    }

    public static LinkedList<LinkedList<String>> parseKey(LinkedList<String> lines) {

        LinkedList<LinkedList<String>> parsed = new LinkedList<>();
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

            parsed.add(current);
            PARSERS.get(key).accept(current);
            current.clear();
        }

        return parsed;
    }

    public static void checkLength(LinkedList<String> lines, int length) {
        if (lines.size() != length) {
            throw new IllegalArgumentException("Invalid number of arguments: " + lines);
        }
    }

    public static void parseNum(LinkedList<String> lines) {
        checkLength(lines, 2);

        String name = lines.poll();
        String value = lines.poll();
        Num var = new Num(name, value, VARS);
        VARS.create(var);

    }

    public static void parsePrint(LinkedList<String> lines) {
        checkLength(lines, 1);

        String name = lines.poll();
        Object value = VARS.get(name);
        System.out.println(value);
    }
}