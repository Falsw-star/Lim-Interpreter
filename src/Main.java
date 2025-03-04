import Utils.Data;
import Utils.Functions;
import Utils.Variables;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static final String TEST_CODE = "num aaa III";



    public static void main(String[] args) {

        Variables vars = new Variables();

        while (true) {
            System.out.print("> ");
            String code = scanner.nextLine();
            code = Functions.wash(code);
            try {
                vars = Functions.parse(code, Data.PARSERS, 1, vars);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

    }
}