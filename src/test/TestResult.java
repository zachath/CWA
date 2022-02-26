//Part of the bachelor thesis work by Michael Foussianis and Zacharias Thorell.

package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container class for the results of tests.
 */
public class TestResult implements Comparable<TestResult> {
    public final int totalOperations, threadCount, elementCount;
    public final long throughput;

    public TestResult(int totalOperations, long totalTime, int threadCount, int elementCount) {
        this.totalOperations = totalOperations;
        this.threadCount = threadCount;
        this.elementCount = elementCount;
        this.throughput = this.totalOperations / totalTime;
    }

    /**
     * Returns the average throughput of the list.
     * @param resultList as input.
     * @return average of resultList.
     */
    public static long getAverageThroughput(List<TestResult> resultList) {
        long total = 0;
        for (TestResult result : resultList) {
            total += result.throughput;
        }
        return total / resultList.size();
    }

    /**
     * Returns the median throughput of the list.
     * @param resultList as input.
     * @return median of resultList.
     */
    public static long getMedianThroughput(List<TestResult> resultList) {
        List<TestResult> sortedList = new ArrayList<>(resultList);
        Collections.sort(sortedList);

        return (sortedList.get(sortedList.size()/2).throughput + sortedList.get(sortedList.size()/2 - 1).throughput)/2;
    }

    @Override
    public int compareTo(TestResult other) {
        return Long.compare(this.throughput, other.throughput);
    }
}
