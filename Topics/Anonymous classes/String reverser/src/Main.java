import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        StringReverser reverser = new StringReverser() {
            public String reverse(String str) {
                Deque<String> reverseString = new ArrayDeque<>(List.of(str.split("")));
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < str.length(); i++) {
                    stringBuilder.append(reverseString.pollLast());
                }

                return stringBuilder.toString();
            }
        };

        System.out.println(reverser.reverse(line));
    }

    interface StringReverser {

        String reverse(String str);
    }

}