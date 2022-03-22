//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.

package test;

/**
 * Container class for the results of tests.
 */
public class TestResult implements Comparable<TestResult> {
    public final int threadCount, elementCount, lookupPercentage, iterationPercentage, addPercentage, removePercentage;
    public final double throughput, totalOperations;

    public TestResult(double totalOperations, double totalTime, int threadCount, int elementCount, int lookupPercentage, int iterationPercentage, int addPercentage, int removePercentage) {
        this.totalOperations = totalOperations;
        this.threadCount = threadCount;
        this.elementCount = elementCount;
        this.throughput = this.totalOperations / totalTime;
        this.lookupPercentage = lookupPercentage;
        this.iterationPercentage = iterationPercentage;
        this.addPercentage = addPercentage;
        this.removePercentage = removePercentage;
    }

    @Override
    public int compareTo(TestResult other) {
        return Double.compare(this.throughput, other.throughput);
    }
}
