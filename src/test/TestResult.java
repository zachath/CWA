//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.

package test;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Container class for the results of tests.
 */
public class TestResult implements Comparable<TestResult> {
    private static final String DIRECTORY = "../../results/";

    public final int totalOperations, threadCount, elementCount, lookupPercentage, iterationPercentage, addPercentage, removePercentage;
    public final long throughput;

    public TestResult(String testName, String dirName, int totalOperations, long totalTime, int threadCount, int elementCount, int lookupPercentage, int iterationPercentage, int addPercentage, int removePercentage) {
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
        return Long.compare(this.throughput, other.throughput);
    }
}
