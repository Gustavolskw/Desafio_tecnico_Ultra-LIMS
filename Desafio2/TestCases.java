import java.util.Random;
import java.util.stream.IntStream;

public class TestCases {

    private static final Random random = new Random();

    // Lista Pequena (10 - 100 elementos)
    public static int[] generateSmallList() {
        return random.ints(10, 1, 100).toArray();
    }

    //  Lista Média (1.000 - 10.000 elementos)
    public static int[] generateMediumList() {
        return random.ints(1000, 1, 10000).toArray();
    }

    //  Lista Grande (100.000 elementos ou mais)
    public static int[] generateLargeList() {
        return random.ints(100000, 1, 1000000).toArray();
    }

    //  Lista Ordenada (Melhor Caso)
    public static int[] generateSortedList(int size) {
        return IntStream.rangeClosed(1, size).toArray();
    }

    //  Lista Reversamente Ordenada (Pior Caso)
    public static int[] generateReverseSortedList(int size) {
        return IntStream.iterate(size, i -> i - 1).limit(size).toArray();
    }

    //  Lista com Muitos Duplicados
    public static int[] generateDuplicatedList(int size) {
        return random.ints(size, 1, 10).toArray(); // Números entre 1 e 10
    }
}
