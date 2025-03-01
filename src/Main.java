import Utils.Data;
import Utils.Functions;
import Utils.Variables;

import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static final String TEST_CODE = "num aaa III";

    public static Map<String, BiConsumer<LinkedList<String>, Variables>> PARSERS = Map.of(
            "print", Main::print,
            "let", Main::let,
            "num", Main::createNum,
            "str", Main::createString
    );

    public static void main(String[] args) {

        Variables vars = new Variables();

        while (true) {
            System.out.print("> ");
            String code = scanner.nextLine();
            code = Functions.toLowerCase(code);
            try {
                vars = Functions.parse(code, PARSERS, vars, 1);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

    }



    public static void createNum(LinkedList<String> lines, Variables vars) {
        vars.create(Functions.parseNum(lines, vars));
    }

    public static void createString(LinkedList<String> lines, Variables vars) {
        vars.create(Functions.parseString(lines, vars));
    }

    public static void let(LinkedList<String> lines, Variables vars) {
        Functions.let(lines, vars);
    }

    public static void print(LinkedList<String> lines, Variables vars) {
        Functions.print(lines, vars);
    }
}