import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            Map<String, Integer> variableMap = new HashMap<>();
            System.out.println(new Parser(new Tokenizer(input)).expression().evaluate(variableMap));
        }
    }

}
