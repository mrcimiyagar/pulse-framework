package kasper.android.pulseframework.locks;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Locks {

    private static boolean running = false;

    private static Queue<Runnable> accesses = new LinkedBlockingQueue<>();

    public static void runSafeOnIdTable(Runnable runnable) {
        Locks.accesses.add(runnable);
        if (!running) runQueue();
    }

    private static void runQueue() {
        running = true;
        while (!accesses.isEmpty()) {
            Runnable runnable = accesses.poll();
            runnable.run();
        }
        running = false;
    }
}
