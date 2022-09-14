import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

class Utils {

    public static List<Integer> sortOddEven(List<Integer> numbers) {

        Function<Integer, Boolean> isEven = a -> a % 2 == 0;

        Comparator<Integer> compareOddAndEven = (a, b) -> {
            if (isEven.apply(a) && isEven.apply(b)) {
                return b.compareTo(a);
            } else {
                return a.compareTo(b);
            }
        };

        numbers.sort(Comparator.comparing(isEven).thenComparing(compareOddAndEven));

        return numbers;
    }
}