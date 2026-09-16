package lld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * PROBLEM 6: Custom Thread Pool Executor
 * 
 * Implement a custom thread-pool executor simulating how a web server distributes incoming
 * requests (tasks) across a fixed pool of worker threads.
 * 
 * CORE JAVA CORNER: Overloading vs Overriding Rules in Java
 * --------------------------------------------------------
 * 1. Method Overloading (Compile-Time Polymorphism):
 *    - Happens in the same class (or inheritance hierarchy).
 *    - Methods have the same name but different signatures (different type, number, or order of parameters).
 *    - Changing the return type alone is NOT allowed; the parameters MUST differ.
 *    - Can throw different exceptions, and have different access modifiers.
 * 
 * 2. Method Overriding (Runtime Polymorphism):
 *    - Happens between a Superclass and a Subclass.
 *    - Methods have the same name, same parameter types, and same order.
 *    - Access Modifier Constraint: Subclass method CANNOT be more restrictive than the superclass.
 *      (e.g., if super is 'protected', sub must be 'protected' or 'public', not 'private').
 *    - Checked Exception Constraint: Subclass method CANNOT declare new or broader checked exceptions.
 *      (e.g., if super throws 'IOException', sub can throw 'FileNotFoundException' or nothing, but not 'Exception').
 *    - Return Type Constraint: Subclass can use a "covariant return type" (a subclass of the superclass return type).
 *    - Final, private, or static methods CANNOT be overridden.
 */
public class CustomThreadPoolExecutor {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolExecutor(int numThreads, int maxQueueSize) {
        this.taskQueue = new LinkedBlockingQueue<>(maxQueueSize);
        this.workers = new ArrayList<>(numThreads);

        // TODO: Initialize and start the worker threads
    }

    /**
     * Submits a task to the queue. If the queue is full, block or reject.
     */
    public void execute(Runnable task) throws InterruptedException {
        if (this.isShutdown) {
            throw new IllegalStateException("Thread pool has been shut down.");
        }
        
        // TODO: Enqueue the task into the blocking taskQueue
    }

    /**
     * Gracefully shuts down the thread pool.
     */
    public void shutdown() {
        this.isShutdown = true;
        for (WorkerThread worker : workers) {
            worker.interrupt(); // Interrupt worker if sleeping or waiting
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            // TODO: Implement the worker loop.
            // 1. Loop while the pool is not shutdown or the queue is not empty.
            // 2. Fetch task from taskQueue (blocking take() or poll()).
            // 3. Run the task: task.run().
            // 4. Handle InterruptedException gracefully to allow shutdown.
        }
    }

    // Getters for testing
    public int getQueueSize() {
        return taskQueue.size();
    }

    public int getActiveWorkerCount() {
        int active = 0;
        for (WorkerThread w : workers) {
            if (w.isAlive() && !w.isInterrupted()) {
                active++;
            }
        }
        return active;
    }
}
