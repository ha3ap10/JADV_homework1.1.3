package ru.netology;

import java.util.concurrent.RecursiveTask;

public class ArraySum extends RecursiveTask<Integer> {
    
    int start;
    int end;
    int[] array;

    public ArraySum(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        switch (diff) {
            case 0:
                return 0;
            case 1:
                return array[start];
            case 2:
                return array[start] + array[start + 1];
            default:
                return forkTasksAndGetResult();
        }
    }

    private int forkTasksAndGetResult() {
        final int middle = (end - start) / 2 + start;
        ArraySum task1 = new ArraySum(start, middle, array);
        ArraySum task2 = new ArraySum(middle, end, array);
        invokeAll(task1, task2);
        return task1.join() + task2.join();
    }
}
