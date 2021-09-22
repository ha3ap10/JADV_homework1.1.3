package ru.netology;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static final int LENGTH_ARRAY = 100_000_000;
    public static final int MAX_RANDOM = 10;

    public static void main(String[] args){

        long startTime;
        long endTime;

        int[] myArray = generateArray();

        //Однопоточный подсчет суммы элементов массива
        startTime = System.currentTimeMillis();
        int sumOneThread = arraySum(myArray);
        endTime = System.currentTimeMillis();
        long timeOneThread = endTime - startTime;

        System.out.printf("Однопоточный подсчет суммы элементов массива:\n" +
                "Результат - %d\nЗа %d миллисекунд\n", sumOneThread, timeOneThread);
        System.out.println();

        //Многопоточный подсчет суммы элементов массива
        startTime = System.currentTimeMillis();
//        final ExecutorService forkJoinPool = Executors.newWorkStealingPool(); //пример из лекции, так и не разобрался как с этим работать
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        final ForkJoinTask<Integer> result = forkJoinPool.submit(new ArraySum(0, myArray.length, myArray));
        int sumMultiThread = result.join();
        endTime = System.currentTimeMillis();
        long timeMultiThread = endTime - startTime;

        System.out.printf("Многопоточный подсчет суммы элементов массива:\n" +
                "Результат - %d\nЗа %d миллисекунд\n", sumMultiThread, timeMultiThread);

        forkJoinPool.shutdown();
    }

    public static int arraySum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static int[] generateArray() {
        Random random = new Random();
        int[] array = new int[LENGTH_ARRAY];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(MAX_RANDOM);
        }
        return array;
    }
}
