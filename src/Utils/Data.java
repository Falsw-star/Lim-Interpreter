package Utils;

import java.util.*;

public class Data {

    public static Map<String, Parser<LinkedList<String>, Variables, Integer>> PARSERS = Map.of(
            "pass", (lines, vars, level) -> {},
            "print", Functions::print,
            "let", Functions::let,
            "num", Functions::createNum,
            "str", Functions::createString,
            "chunk", Functions::createChunk
    );

    public static Map<Integer, Character> SPLITS = Map.of(1, ' ', 2, '·', 3, '-');

    public static Map<String, KeyLength> KEYLENGTHS = Map.of(
            "pass", vars -> 0,
            "print", vars -> 1,
            "let", vars -> 2,
            "num", vars -> 2,
            "str", vars -> 2,
            "mana", vars -> 3,
            "chunk", vars -> 3
    );

    public static Map<Character, Integer> NUMREFLECT = Map.of(
            'n', 0, 'i', 1, 'v', 5, 'x', 10, 'l', 50, 'c', 100, 'd', 500, 'm', 1000
    );
    public static Set<Character> NUMCHARS = Set.of('n', 'i', 'v', 'x', 'l', 'c', 'd', 'm');
    public static Set<Character> NOMORCHARS = Set.of('a', 'b', 'e', 'f', 'g', 'h', 'j', 'k', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'y', 'z');

    public static Set<Character> validChars = new HashSet<>() {
    };
    static {
        validChars.addAll(NUMCHARS);
        validChars.addAll(NOMORCHARS);
        validChars.addAll(List.of(' ' ,'·', '-', '='));
    }
}
