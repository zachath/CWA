//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.

package test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Function:    Tests the throughput of the CopyOnWriteArrayList with varying set of work, operations and resources.
 *
 * Thesis:      The reason for this being part of the thesis is to make sure that the program developed by Emterfors &
 *              Sanders and later by Bergman & El-Khadri, does not contain any bugs that may have resulted in the
 *              scalability issues with the CopyOnWriteArrayList.
 */
public class CopyOnWriteArrayListScalabilityTester {

    //The data structure to be used throughout the test.
    private static CopyOnWriteArrayList<Integer> CWA = new CopyOnWriteArrayList<>();

    //The operations to be performed.
    private static List<Operations> operations = new LinkedList<>();

    private static int numberOfElements;
    private static int lookupPercentage;
    private static int iterationPercentage;
    private static int addPercentage;
    private static int removePercentage;
    private static int numberOfThreads;

    /*
    * Execute by:   java test.CopyOnWriteArrayListScalabilityTester [2 to the power of X elements] [Lookup percentage] [Iteration Percentage] [Add Percentage] [Remove Percentage] [Number of threads/cores]
    * Example:      java test.CopyOnWriteArrayListScalabilityTester 14 34 33 17 16 4
    * Should NOT be done from inside the package test.
    */
    public static void main(String[] args) throws InterruptedException {

        getTestSettings(args);

        //warmup

        generateOperationSequence();

        warmup();
        System.out.println("Finished warmup");

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

            if (lookupPercentage + iterationPercentage + addPercentage + removePercentage != 100) {
                System.out.println("Percentages are not correct");
                throw new IllegalStateException("Percentages are not correct");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        /*System.out.println("Number of elements: " + numberOfElements);
        System.out.println("Lookup: " + lookupPercentage + "%");
        System.out.println("Iteration: " + iterationPercentage + "%");
        System.out.println("Modification: " + (addPercentage + removePercentage) + "%");
        System.out.println("Number of threads: " + numberOfThreads);*/
    }

    /**
     * Generates the operations sequence based on the percentages for the operations by adding the amount and then
     * randomly shuffling the list using Collections.shuffle().
     */
    private static void generateOperationSequence() {
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
    }

    private static void testMethodToBeCompiled() {
        int total = 0;
        int old = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i+old);
            old = i;
        }

        for (Integer i : list) {
            total += i;
        }
        System.out.println(total);
    }

    private static void warmup() {
        runTest();
    }

    private static void runTest() {
        throw new UnsupportedOperationException("Not yet created");
    }
}
