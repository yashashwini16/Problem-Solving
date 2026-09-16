package lld.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPoolExecutorSolution {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolExecutorSolution(int numThreads, int maxQueueSize) {
        this.taskQueue = new LinkedBlockingQueue<>(maxQueueSize);
        this.workers = new ArrayList<>(numThreads);

        for (int i = 0; i < numThreads; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (this.isShutdown) {
            throw new IllegalStateException("Thread pool has been shut down.");
        }
        taskQueue.put(task); // Blocks if the queue is full
    }

    public void shutdown() {
        this.isShutdown = true;
        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.poll();
                    if (task == null) {
                        if (isShutdown) {
                            break;
                        }
                        task = taskQueue.take(); // Block waiting for tasks
                    }
                    task.run();
                } catch (InterruptedException e) {
                    if (isShutdown && taskQueue.isEmpty()) {
                        break;
                    }
                } catch (Throwable t) {
                    System.err.println("Exception in custom worker thread: " + t.getMessage());
                }
            }
        }
    }

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
