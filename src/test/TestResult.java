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
    public final String testName, dirName;

    public TestResult(String testName, String dirName, int totalOperations, long totalTime, int threadCount, int elementCount, int lookupPercentage, int iterationPercentage, int addPercentage, int removePercentage) {
        this.testName = testName;
        this.dirName = dirName;
        this.totalOperations = totalOperations;
        this.threadCount = threadCount;
        this.elementCount = elementCount;
        this.throughput = this.totalOperations / totalTime;
        this.lookupPercentage = lookupPercentage;
        this.iterationPercentage = iterationPercentage;
        this.addPercentage = addPercentage;
        this.removePercentage = removePercentage;
    }

    /**
     * Creates the specified file. If not possible for any reason (e.g. it already exists) an exception is thrown.
     * @param file to create.
     * @throws Exception File could not be created.
     */
    public static void createFile(File file) {
        try {
            if (!file.createNewFile()) {
                throw new Exception("File could not be created");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the specified directory.
     * @param dir to create.
     */
    public static void createDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Save the results of the test to a single .csv file.
     * @param resultList list of result.
     */
    public static void createCSV(List<TestResult> resultList) {

        String dirName = "..\\results\\" + resultList.get(0).dirName;
        String fileName = "..\\results\\" + dirName + "\\" + resultList.get(0).testName + ".csv";

        createDirectory(new File(dirName));

        File file = new File(fileName);
        createFile(file);

        try (PrintWriter writer = new PrintWriter(file)) {
            StringBuilder builder = new StringBuilder();
            builder.append("Thread Count,");
            builder.append("Element Count,");
            builder.append("Throughput,");
            builder.append("Lookup Percentage,");
            builder.append("Iteration Percentage,");
            builder.append("Add Percentage,");
            builder.append("Remove Percentage");
            builder.append("\n");

            int i = 0;
            for (TestResult result : resultList) {
                builder.append(i).append(",");
                builder.append(result.threadCount).append(",");
                builder.append(result.elementCount).append(",");
                builder.append(result.throughput).append(",");
                builder.append(result.lookupPercentage).append(",");
                builder.append(result.iterationPercentage).append(",");
                builder.append(result.addPercentage).append(",");
                builder.append(result.removePercentage);
                builder.append("\n");
                i++;
            }

            writer.write(builder.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(TestResult other) {
        return Long.compare(this.throughput, other.throughput);
    }
}
