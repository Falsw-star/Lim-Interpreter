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

}
