package test;

import coding.*;
import coding.solutions.*;
import lld.*;
import lld.solutions.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner {

    // ANSI Colors for terminal output
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";

    private static int totalTests = 0;
    private static int passedTests = 0;

    public static void main(String[] args) {
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println(CYAN + "   INTERACTIVE INTERVIEW PREP TEST HARNESS       " + RESET);
        System.out.println(CYAN + "==================================================" + RESET);

        // Run solutions verification
        System.out.println("\n" + YELLOW + "--- STEP 1: Verifying Reference Solutions ---" + RESET);
        runAllTests(true);

        // Run user code verification
        System.out.println("\n" + YELLOW + "--- STEP 2: Verifying Your Implementations ---" + RESET);
        int preTests = totalTests;
        int prePassed = passedTests;
        runAllTests(false);

        System.out.println("\n" + CYAN + "==================================================" + RESET);
        int userTotal = totalTests - preTests;
        int userPassed = passedTests - prePassed;
        System.out.println("Result: " + userPassed + "/" + userTotal + " of your coding exercises passed.");
        if (userPassed == userTotal) {
            System.out.println(GREEN + "Congratulations! All your implementations are 100% correct!" + RESET);
        } else {
            System.out.println(YELLOW + "Tip: Open files in 'coding/' and 'lld/' to complete the TODOs." + RESET);
        }
        System.out.println(CYAN + "==================================================" + RESET);
    }

    private static void runAllTests(boolean runSolutions) {
        String mode = runSolutions ? "Reference Solution" : "User Code";
        
        testGeofenceService(runSolutions, mode);
        testCabRouteRotator(runSolutions, mode);
        testDependencyCycleDetector(runSolutions, mode);
        testLRUCache(runSolutions, mode);
        testCustomThreadPoolExecutor(runSolutions, mode);
    }

    private static void testGeofenceService(boolean useSolution, String mode) {
        String testName = "Geofence Boundary Detection (" + mode + ")";
        try {
            // Setup
            List<GeofenceService.Vehicle> vehicles = List.of(
                new GeofenceService.Vehicle("v1", 0, 5),     // On circumference of g1
                new GeofenceService.Vehicle("v2", 3, 4),     // On circumference of g1 (3^2 + 4^2 = 5^2)
                new GeofenceService.Vehicle("v3", 0, 0),     // Inside g1
                new GeofenceService.Vehicle("v4", 10, 10)    // Way outside
            );

            List<GeofenceService.CircularGeofence> geofences = List.of(
                new GeofenceService.CircularGeofence("g1", 0, 0, 5)
            );

            List<GeofenceService.Vehicle> result;
            if (useSolution) {
                result = GeofenceServiceSolution.findVehiclesOnBoundary(vehicles, geofences, 1e-6);
            } else {
                result = GeofenceService.findVehiclesOnBoundary(vehicles, geofences, 1e-6);
            }

            // Assertions
            assertEqual(2, result.size(), testName + " - Correct count of vehicles on boundary");
            boolean hasV1 = result.stream().anyMatch(v -> v.id().equals("v1"));
            boolean hasV2 = result.stream().anyMatch(v -> v.id().equals("v2"));
            assertTrue(hasV1 && hasV2, testName + " - Correct vehicles matched");
            
            pass(testName);
        } catch (Throwable t) {
            fail(testName, t);
        }
    }

    private static void testCabRouteRotator(boolean useSolution, String mode) {
        String testName = "In-Place Array Rotator (" + mode + ")";
        try {
            // Test 1: Standard right rotation
            String[] arr1 = {"A", "B", "C", "D"};
            if (useSolution) {
                CabRouteRotatorSolution.rotate(arr1, 1);
            } else {
                CabRouteRotator.rotate(arr1, 1);
            }
            assertArrayEqual(new String[]{"D", "A", "B", "C"}, arr1, testName + " - shift right by 1");

            // Test 2: Shift larger than length
            String[] arr2 = {"A", "B", "C"};
            if (useSolution) {
                CabRouteRotatorSolution.rotate(arr2, 5); // equivalent to rotating by 2
            } else {
                CabRouteRotator.rotate(arr2, 5);
            }
            assertArrayEqual(new String[]{"B", "C", "A"}, arr2, testName + " - shift right by 5");

            // Test 3: Negative shift (leftward rotation)
            String[] arr3 = {"A", "B", "C", "D"};
            if (useSolution) {
                CabRouteRotatorSolution.rotate(arr3, -1); // equivalent to rotating by 3
            } else {
                CabRouteRotator.rotate(arr3, -1);
            }
            assertArrayEqual(new String[]{"B", "C", "D", "A"}, arr3, testName + " - shift left by 1");

            pass(testName);
        } catch (Throwable t) {
            fail(testName, t);
        }
    }

    private static void testDependencyCycleDetector(boolean useSolution, String mode) {
        String testName = "Graph Cycle Detection (" + mode + ")";
        try {
            // Test 1: Cycle A -> B -> C -> A
            Map<String, List<String>> graphWithCycle = new HashMap<>();
            graphWithCycle.put("A", List.of("B"));
            graphWithCycle.put("B", List.of("C"));
            graphWithCycle.put("C", List.of("A"));

            boolean cycleDetected;
            if (useSolution) {
                cycleDetected = DependencyCycleDetectorSolution.hasCycle(graphWithCycle);
            } else {
                cycleDetected = DependencyCycleDetector.hasCycle(graphWithCycle);
            }
            assertTrue(cycleDetected, testName + " - detect cycle A->B->C->A");

            // Test 2: DAG (no cycles)
            Map<String, List<String>> graphNoCycle = new HashMap<>();
            graphNoCycle.put("A", List.of("B", "C"));
            graphNoCycle.put("B", List.of("C"));
            graphNoCycle.put("C", Collections.emptyList());

            if (useSolution) {
                cycleDetected = DependencyCycleDetectorSolution.hasCycle(graphNoCycle);
            } else {
                cycleDetected = DependencyCycleDetector.hasCycle(graphNoCycle);
            }
            assertFalse(cycleDetected, testName + " - detect no cycle in DAG");

            pass(testName);
        } catch (Throwable t) {
            fail(testName, t);
        }
    }

    private static void testLRUCache(boolean useSolution, String mode) {
        String testName = "Custom LRU Cache (" + mode + ")";
        try {
            if (useSolution) {
                LRUCacheSolution<String, Integer> cache = new LRUCacheSolution<>(2);
                cache.put("one", 1);
                cache.put("two", 2);
                assertEqual(1, cache.get("one"), testName + " - get element one");
                cache.put("three", 3); // evicts "two" (since "one" was accessed recently)
                assertNull(cache.get("two"), testName + " - element two evicted");
                assertEqual(1, cache.get("one"), testName + " - element one remains");
                assertEqual(3, cache.get("three"), testName + " - element three exists");
            } else {
                LRUCache<String, Integer> cache = new LRUCache<>(2);
                cache.put("one", 1);
                cache.put("two", 2);
                assertEqual(1, cache.get("one"), testName + " - get element one");
                cache.put("three", 3);
                assertNull(cache.get("two"), testName + " - element two evicted");
                assertEqual(1, cache.get("one"), testName + " - element one remains");
                assertEqual(3, cache.get("three"), testName + " - element three exists");
            }
            pass(testName);
        } catch (Throwable t) {
            fail(testName, t);
        }
    }

    private static void testCustomThreadPoolExecutor(boolean useSolution, String mode) {
        String testName = "Custom Thread Pool Executor (" + mode + ")";
        try {
            int numThreads = 3;
            int maxQueue = 5;
            AtomicInteger taskCounter = new AtomicInteger(0);
            CountDownLatch latch = new CountDownLatch(10);

            if (useSolution) {
                CustomThreadPoolExecutorSolution pool = new CustomThreadPoolExecutorSolution(numThreads, maxQueue);
                
                // Let workers start
                Thread.sleep(100);
                
                for (int i = 0; i < 10; i++) {
                    pool.execute(() -> {
                        try {
                            Thread.sleep(50);
                            taskCounter.incrementAndGet();
                            latch.countDown();
                        } catch (InterruptedException ignored) {}
                    });
                }
                
                boolean finished = latch.await(2, TimeUnit.SECONDS);
                pool.shutdown();
                
                assertTrue(finished, testName + " - all tasks completed");
                assertEqual(10, taskCounter.get(), testName + " - total tasks executed");
            } else {
                CustomThreadPoolExecutor pool = new CustomThreadPoolExecutor(numThreads, maxQueue);
                
                Thread.sleep(100);
                
                // If unimplemented, executor might fail immediately or execute zero tasks
                for (int i = 0; i < 10; i++) {
                    pool.execute(() -> {
                        taskCounter.incrementAndGet();
                        latch.countDown();
                    });
                }
                
                latch.await(200, TimeUnit.MILLISECONDS); // shorter timeout for user code to prevent hanging tests
                pool.shutdown();
                
                // We won't block strictly on correctness here if the user code is unimplemented
                // but let's check basic structure.
                if (pool.getActiveWorkerCount() == 0) {
                    throw new IllegalStateException("User Thread Pool has 0 active workers (unimplemented).");
                }
            }
            pass(testName);
        } catch (Throwable t) {
            fail(testName, t);
        }
    }

    // Helper Assertions
    private static void assertTrue(boolean val, String msg) {
        totalTests++;
        if (!val) throw new AssertionError("Expected TRUE: " + msg);
        passedTests++;
    }

    private static void assertFalse(boolean val, String msg) {
        totalTests++;
        if (val) throw new AssertionError("Expected FALSE: " + msg);
        passedTests++;
    }

    private static void assertEqual(Object expected, Object actual, String msg) {
        totalTests++;
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected [" + expected + "], but got [" + actual + "]: " + msg);
        }
        passedTests++;
    }

    private static void assertNull(Object val, String msg) {
        totalTests++;
        if (val != null) throw new AssertionError("Expected NULL, but got: [" + val + "]: " + msg);
        passedTests++;
    }

    private static void assertArrayEqual(String[] expected, String[] actual, String msg) {
        totalTests++;
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError("Expected " + Arrays.toString(expected) + ", but got " + Arrays.toString(actual) + ": " + msg);
        }
        passedTests++;
    }

    private static void pass(String testName) {
        System.out.println(GREEN + "  [PASSED] " + testName + RESET);
    }

    private static void fail(String testName, Throwable t) {
        System.out.println(RED + "  [FAILED] " + testName + RESET);
        System.out.println("           Reason: " + t.getMessage());
    }
}
