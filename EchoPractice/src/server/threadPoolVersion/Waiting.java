package server.threadPoolVersion;

import java.net.Socket;
import java.util.Optional;

public class Waiting implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                if (SocketQueue.isEmpty())
                    synchronized (Thread.currentThread()) {
                        Thread.currentThread().wait(100);
                    }
                else {
                    Optional<Thread> thread = ThreadPool.getThread();

                    if (!thread.isEmpty())
                        thread.get().start();
                }
            } catch (InterruptedException interruptedException) {
                System.out.println("UnExpected Interrupt occurred!! (Waiting) : " + interruptedException);
            }
        }
    }
}
