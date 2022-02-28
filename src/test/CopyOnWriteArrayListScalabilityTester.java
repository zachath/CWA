//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.
//Based on the test program by Emterfors and Sander.
package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    private final static int runTime = 1000;

    //Stores all test results, reset after warmup.
    private static final List<TestResult> testResults = new ArrayList<>();

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
    private static String fileName;
    private static String dirName;

    /*
    * Execute by:   java test.CopyOnWriteArrayListScalabilityTester [2 to the power of X elements] [Lookup percentage] [Iteration Percentage] [Add Percentage] [Remove Percentage] [Number of threads/cores] [Test Iterations]
    * Example:      java test.CopyOnWriteArrayListScalabilityTester 14 34 33 17 16 4 10
    * Should NOT be done from inside the package test.
    */
    public static void main(String[] args) {
        getTestSettings(args);

        warmup();

        System.out.println("Starting Tests");
        System.out.println(fileName);
        System.out.println("Tests Complete");

        runTest(true);
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
            dirName = lookupPercentage + "%Look-" + iterationPercentage + "%Iter-" + (addPercentage + removePercentage) + "%Mod-" + numberOfElements + "Elements-";
            fileName = dirName + numberOfThreads + "Threads";

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

    /**
     * The entire test program is run once to ensure dynamic compilation, results are discarded and test reset afterwards.
     */
    private static void warmup() {
        System.out.println("Starting Warmup");
        runTest(false);
        testResults.clear();
        System.out.println("Finished Warmup");
    }

    /**
     * Resets the tests by creating a new instance of the CWA, calling garbage collection and setting the testIsFinished
     * flag to true.
     */
    private static void resetDataStructure() {
        CWA = new CopyOnWriteArrayList<>();
        System.gc();
        //testIsFinished = false;
    }

    /**
     * Runs as many tests specified by the testIterations variable.
     * The CWA is populated with as many elements as specified,
     * then workers and threads are created then started.
     * They run for runTime milliseconds before the testIsFinished flag is set
     * to false and the threads terminate. The amount of totalOperations is tallied up and
     * a TestResult instance is created for every test. Finally, the test is reset.
     */
    private static void runTest(boolean writeToFile) {
        for (int i = 0; i < testIterations; i++) {
            System.out.println("Test " + i);
            int totalOperations = 0;

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
                //testIsFinished = true;
                for (Thread thread : threads) {
                    thread.interrupt();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long totalTime = (long) ((System.nanoTime() - startTime) / 1.0E9);

            for (Worker worker : workers) {
                totalOperations += worker.totalOperations;
            }


            System.out.println("Test " + i + " Completee");

            testResults.add(new TestResult(fileName, dirName, totalOperations, totalTime, numberOfThreads, numberOfElements, lookupPercentage, iterationPercentage, addPercentage, removePercentage));

            resetDataStructure();
            threads.clear();
            workers.clear();
        }

        if (writeToFile) {
            CSVCreator csvCreator = new CSVCreator(dirName, fileName, testResults);
            csvCreator.createCSV();
        }
    }

    /**
     * Performs the work in the created threads.
     */
    private static class Worker implements Runnable {
        private final List<Operations> operations;
        private final Random random;
        private int totalOperations;

        public Worker(List<Operations> operations) {
            this.operations = operations;
            this.random = new Random();
        }

        /**
         * Gets the element specified by random int.
         * to avoid dead code elimination the hashcode of value is compared to
         * the current time.
         */
        private void lookup() {
            Integer value = CWA.get(random.nextInt(CWA.size() - 1));

            //Dead code elimination as per Goetz et. al 2006
            if (value.hashCode() == System.nanoTime()) {
                System.out.print(" ");
            }
        }

        /**
         * Iterates through the CWA without doing anything,
         * to avoid dead code elimination the hashcode of i is compared to
         * the current time.
         */
        private void iterate() {
            for (Integer i : CWA) {
                //Dead code elimination as per Goetz et. al 2006
                if (i.hashCode() == System.nanoTime()) {
                    System.out.print(" ");
                }
            }
        }

        /**
         * Performs the operation specified by operations and count the total amount of operations until
         * testIsFinished is set to true by the main thread.
         */
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                //Iterates through the list using modulus.
                Operations operation = operations.get(totalOperations % operations.size());

                if (operation == Operations.LOOKUP) {
                    lookup();
                }
                else if (operation == Operations.ADD) {
                    CWA.add(random.nextInt(CWA.size() - 1));
                }
                else if (operation == Operations.REMOVE) {
                    CWA.remove(random.nextInt(CWA.size() - 1));
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
