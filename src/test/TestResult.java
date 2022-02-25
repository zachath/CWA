//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.

package test;

/**
 * Container class for the results of tests.
 */
public class TestResult {
    private final String testName;
    private final int totalOperations, threadCount, elementCount;
    private final long throughput;

    public TestResult(String testName, int totalOperations, long totalTime, int threadCount, int elementCount) {
        this.testName = testName;
        this.totalOperations = totalOperations;
        this.threadCount = threadCount;
        this.elementCount = elementCount;
        this.throughput = this.totalOperations / totalTime;
    }
}
