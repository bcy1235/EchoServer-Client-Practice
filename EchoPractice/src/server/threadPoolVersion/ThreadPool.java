package server.threadPoolVersion;

import java.util.Optional;

public class ThreadPool {
    static Thread[] threads;
    // Default ThreadPool size = 10
    static int THREAD_POOL_SIZE;

    static  {
        THREAD_POOL_SIZE = 10;
    }

    static void initialize() {
        threads = new Thread[THREAD_POOL_SIZE];

        int count = 0;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new EchoService(++count));
        }
    }
    static void setThreadPoolSize(int poolSize) {
        THREAD_POOL_SIZE = poolSize;
    }
    static Optional<Thread> getThread() {
        for (Thread thread : threads) {
            Thread.State state = thread.getState();

            if (state == Thread.State.NEW)
                return Optional.of(thread);
            else if (state == Thread.State.WAITING) {
                synchronized (thread) {
                    thread.notify();
                }
                while (thread.getState() != Thread.State.RUNNABLE) {

                }
                return Optional.of(thread);
            }

        }
        return Optional.empty();
    }
}
