import java.util.Arrays;

public class SortingTest {

    public static void main(String[] args) {

        // Gera as listas para realizar os testes
        int[] smallList = TestCases.generateSmallList();
        int[] mediumList = TestCases.generateMediumList();
        int[] largeList = TestCases.generateLargeList();
        int[] sortedList = TestCases.generateSortedList(1000);
        int[] reverseList = TestCases.generateReverseSortedList(1000);
        int[] duplicateList = TestCases.generateDuplicatedList(1000);

        // Testa cada algoritmo nos diferentes cenários
        System.out.println("\n--- Teste com Lista Pequena ---");
        runSortingTests(smallList);

        System.out.println("\n--- Teste com Lista Média ---");
        runSortingTests(mediumList);

        System.out.println("\n--- Teste com Lista Grande ---");
        runSortingTests(largeList);

        System.out.println("\n--- Teste com Lista Ordenada ---");
        runSortingTests(sortedList);

        System.out.println("\n--- Teste com Lista Reversamente Ordenada ---");
        runSortingTests(reverseList);

        System.out.println("\n--- Teste com Lista com Muitos Duplicados ---");
        runSortingTests(duplicateList);
    }

    private static void runSortingTests(int[] arr) {
        testAlgorithm("QuickSort", Arrays.copyOf(arr, arr.length), (array) -> SortingAlgorithms.quickSort(array, 0, array.length - 1));
        testAlgorithm("MergeSort", Arrays.copyOf(arr, arr.length), SortingAlgorithms::mergeSort);
        testAlgorithm("BubbleSort", Arrays.copyOf(arr, arr.length), SortingAlgorithms::bubbleSort);
    }

    private static void testAlgorithm(String name, int[] arr, SortingFunction sortFunction) {
        long startTime = System.nanoTime();
        sortFunction.sort(arr);
        long endTime = System.nanoTime();
        System.out.printf("%s: %.6f ms%n", name, (endTime - startTime) / 1e6);
    }

    @FunctionalInterface
    interface SortingFunction {
        void sort(int[] array);
    }
}
