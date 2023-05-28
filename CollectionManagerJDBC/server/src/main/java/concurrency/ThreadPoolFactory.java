package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ThreadPoolFactory {
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    private static final ForkJoinPool forkJoinExecutorsPool = new ForkJoinPool();
    private static final ForkJoinPool forkJoinSendersPool = new ForkJoinPool();

    public static ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }

    public static ForkJoinPool getForkJoinExecutorsPool() {
        return forkJoinExecutorsPool;
    }

    public static ForkJoinPool getForkJoinSendersPool() {
        return forkJoinSendersPool;
    }
}
