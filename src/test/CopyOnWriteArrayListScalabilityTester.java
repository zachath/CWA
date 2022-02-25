//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.
//Based on the test program by Emterfors and Sander.

package test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Function:    Tests the throughput of the CopyOnWriteArrayList with varying set of work, operations and resources.
 *
 * Thesis:      The reason for this being part of the thesis is to make sure that the program developed by Emterfors &
 *              Sander and later by Bergman & El-Khadri, does not contain any bugs that may have resulted in the
 *              scalability issues with the CopyOnWriteArrayList.
 */
public class CopyOnWriteArrayListScalabilityTester {

    //The data structure to be used throughout the test.
    private static CopyOnWriteArrayList<Integer> CWA = new CopyOnWriteArrayList<>();

    //Used as flag to signal the workers that they should terminate.
    private volatile static boolean testIsFinished;

    //Runtime of tests
    private final static int runTime = 10000;

    private static List<TestResult> testResults = new ArrayList<>();

    //The operations to be performed.
    //private static List<Operations> operations = new ArrayList<>();

    //In order of command line input.
    private static int numberOfElements;
    private static int lookupPercentage;
    private static int iterationPercentage;
    private static int addPercentage;
    private static int removePercentage;
    private static int numberOfThreads;
    private static int testIterations;
    private static String testName;

    /*
    * Execute by:   java test.CopyOnWriteArrayListScalabilityTester [2 to the power of X elements] [Lookup percentage] [Iteration Percentage] [Add Percentage] [Remove Percentage] [Number of threads/cores] [Test Iterations] [Name of test]
    * Example:      java test.CopyOnWriteArrayListScalabilityTester 14 34 33 17 16 4 10 34%Lookup33%Iteration33%Modification4Cores
    * Should NOT be done from inside the package test.
    */
    public static void main(String[] args) {

        getTestSettings(args);

        warmup();
        testResults.clear();
        System.out.println("Finished warmup");

        System.out.println("Starting Tests");
        System.out.println(testName);
        System.out.println("Tests Complete");

        runTest();
    }

    /**
     * Sets all variables based on the input.
     * If an exception is caused the stack trace is printed and test exits.
     * @param args the input given on run.
     */
    private static void getTestSettings(String[] args) {
        try {
            numberOfElements = (int) Math.pow(2, Integer.parseInt(args[0]));
            lookupPercentage = Integer.parseInt(args[1]);
            iterationPercentage = Integer.parseInt(args[2]);
            addPercentage = Integer.parseInt(args[3]);
            removePercentage = Integer.parseInt(args[4]);
            numberOfThreads = Integer.parseInt(args[5]);
            testIterations = Integer.parseInt(args[6]);
            testName = args[7];

            if (lookupPercentage + iterationPercentage + addPercentage + removePercentage != 100) {
                throw new IllegalStateException("Percentages are not correct");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Generates the operations sequence based on the percentages for the operations by adding the amount and then
     * randomly shuffling the list using Collections.shuffle().
     */
    private static List<Operations> generateOperationSequence() {
        List<Operations> operations = new ArrayList<>();
        for (int i = 0; i < lookupPercentage; i++) {
            operations.add(Operations.LOOKUP);
        }

        for (int i = 0; i < iterationPercentage; i++) {
            operations.add(Operations.ITERATE);
        }

        for (int i = 0; i < addPercentage; i++) {
            operations.add(Operations.ADD);
        }

        for (int i = 0; i < removePercentage; i++) {
            operations.add(Operations.REMOVE);
        }
        Collections.shuffle(operations);
        return operations;
    }

    private static void warmup() {
        runTest();
    }

    private static void resetDataStructure() {
        CWA = new CopyOnWriteArrayList<>();
        System.gc();
        testIsFinished = false;
    }

    private static void runTest() {
        int totalOperations = 0;

        for (int i = 0; i < testIterations; i++) {
            System.out.println("Test " + i);

            //Har innehållet någon påverkan?
            for (int j = 0; j < numberOfElements; j++) {
                CWA.add(j);
            }

            List<Thread> threads = new ArrayList<>();
            List<Worker> workers = new ArrayList<>();

            for (int j = 0; j < numberOfThreads; j++) {
                Worker worker = new Worker(generateOperationSequence());
                workers.add(worker);
                threads.add(new Thread(worker));
            }

            long startTime = System.nanoTime();

            for (Thread thread : threads) {
                thread.start();
            }

            try {
                Thread.sleep(runTime);
                testIsFinished = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long totalTime = (long) ((System.nanoTime() - startTime) / 1.0E9);

            for (Worker worker : workers) {
                totalOperations += worker.totalOperations;
            }


            System.out.println("Test [" + i + "] Complete");

            testResults.add(new TestResult(testName, totalOperations, totalTime, numberOfThreads, numberOfElements));

            resetDataStructure();
        }
    }

    private static class Worker implements Runnable {
        private final List<Operations> operations;
        private final Random random;
        private int totalOperations;

        public Worker(List<Operations> operations) {
            this.operations = operations;
            this.random = new Random();
        }

        private void lookup(int index) {
            Integer value = CWA.get(index);

            //Dead code elimination as per Goetz et. al 2007
            if (value.hashCode() == System.nanoTime()) {
                System.out.print(" ");
            }
        }

        private void iterate() {
            for (Integer i : CWA) {
                //Dead code elimination as per Goetz et. al 2007
                if (i.hashCode() == System.nanoTime()) {
                    System.out.print(" ");
                }
            }
        }

        @Override
        public void run() {
            while (!testIsFinished) {
                Operations operation = operations.get(totalOperations % operations.size());
                int randomValue = random.nextInt(operations.size() - 1);

                if (operation == Operations.LOOKUP) {
                    lookup(randomValue);
                }
                else if (operation == Operations.ADD) {
                    CWA.add(randomValue);
                }
                else if (operation == Operations.REMOVE) {
                    CWA.remove(randomValue);
                }
                else if (operation == Operations.ITERATE) {
                    iterate();
                }
                else {
                    throw new RuntimeException("Unsupported Operation");
                }

                totalOperations++;
            }
        }
    }
}
