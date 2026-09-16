package coding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * PROBLEM 2: Concurrency & String Buffer/Builder
 * 
 * This playground demonstrates the difference between StringBuilder (thread-unsafe) and 
 * StringBuffer (thread-safe, synchronized) under concurrent writes.
 * 
 * Thread-Safety Question & Answers:
 * 1. Why is StringBuffer safe compared to StringBuilder?
 *    - StringBuffer synchronizes almost all of its methods (using the 'synchronized' keyword on the instance). 
 *      This ensures that only one thread can modify its internal char array or shift its offsets at a time.
 *    - StringBuilder lacks synchronization, making it faster but unsafe for concurrent modifications.
 * 
 * 2. What happens inside memory allocation if multiple threads append to a shared StringBuilder?
 *    - StringBuilder maintains a char/byte array (value) and a count of characters.
 *    - When appending, the JVM checks if capacity is sufficient. If not, it allocates a larger array, 
 *      copies the old elements, and updates the reference.
 *    - Without synchronization:
 *      * Race Condition on 'count': Thread A and Thread B read the same 'count' value. Thread A writes its characters, 
 *        then Thread B writes its characters at the same 'count' offset, overwriting Thread A's data.
 *      * ArrayOutOfBoundsException: Thread A and Thread B verify there is remaining capacity. Thread A increments 'count' 
 *        and writes. Thread B then attempts to write at the incremented count, but capacity was not expanded because the 
 *        capacity check occurred before Thread A's write, resulting in writing past the array bounds.
 *      * Corrupted reference: Array resizing involves allocating a new array and reassigning the field pointer. If two threads 
 *        trigger resizing concurrently, updates might get lost or point to corrupted heap space.
 */
public class StringConcurrencyPlayground {

    /**
     * A custom, lightweight thread-safe accumulator.
     * Implement this or analyze its locking strategy.
     */
    public static class ThreadSafeStringAccumulator {
        private final StringBuilder builder = new StringBuilder();

        // TODO: Implement thread-safe append and toString methods using explicit synchronization
        public void append(String str) {
            // Write synchronized block or use a ReentrantLock
        }

        @Override
        public String toString() {
            // Write synchronized block or use a ReentrantLock to return the current value safely
            return "";
        }
    }

    public static void runSimulation() throws InterruptedException {
        int threadCount = 10;
        int appendsPerThread = 1000;
        String appendVal = "A";

        System.out.println("=== RUNNING STRING CONCURRENCY SIMULATION ===");
        System.out.println("Spawning " + threadCount + " threads, each appending '" + appendVal + "' " + appendsPerThread + " times...");

        // 1. Test with StringBuilder (Unsafe)
        StringBuilder unsafeBuilder = new StringBuilder();
        ExecutorService service1 = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            service1.submit(() -> {
                for (int j = 0; j < appendsPerThread; j++) {
                    try {
                        unsafeBuilder.append(appendVal);
                    } catch (Exception e) {
                        // StringBuilder often throws ArrayIndexOutOfBoundsException in concurrent scenarios
                        System.err.println("StringBuilder Error: " + e.getMessage());
                    }
                }
            });
        }
        service1.shutdown();
        service1.awaitTermination(5, TimeUnit.SECONDS);

        // 2. Test with StringBuffer (Safe)
        StringBuffer safeBuffer = new StringBuffer();
        ExecutorService service2 = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            service2.submit(() -> {
                for (int j = 0; j < appendsPerThread; j++) {
                    safeBuffer.append(appendVal);
                }
            });
        }
        service2.shutdown();
        service2.awaitTermination(5, TimeUnit.SECONDS);

        int expectedLength = threadCount * appendsPerThread;
        System.out.println("Expected final length: " + expectedLength);
        System.out.println("StringBuilder length:  " + unsafeBuilder.length() + " (Unsafe - typically less than expected or crashed)");
        System.out.println("StringBuffer length:   " + safeBuffer.length() + " (Safe - always equal to expected)");
    }
    
    public static void main(String[] args) throws InterruptedException {
        runSimulation();
    }
}
