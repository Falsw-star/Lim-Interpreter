package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    public static Map<Integer, Character> SPLITS = Map.of(1, ' ', 2, '·', 3, '-');

    public static Map<String, Integer> KEYLENGTHS = Map.of(
            "print", 1,
            "let", 2,
            "num", 2,
            "str", 2,
            "mana", 3,
            "chunk", 4
    );

    public static Map<Character, Integer> NUMREFLECT = Map.of(
            'n', 0, 'i', 1, 'v', 5, 'x', 10, 'l', 50, 'c', 100, 'd', 500, 'm', 1000
    );
    public static List<Character> NUMCHARS = List.of('n', 'i', 'v', 'x', 'l', 'c', 'd', 'm');
    public static List<Character> NOMORCHARS = List.of('a', 'b', 'e', 'f', 'g', 'h', 'j', 'k', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'y', 'z');

    public static List<Character> validChars = new ArrayList<>();
    static {
        validChars.addAll(NUMCHARS);
        validChars.addAll(NOMORCHARS);
        validChars.addAll(List.of(' ' ,'·', '-'));
    }
}
