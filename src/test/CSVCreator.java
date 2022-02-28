package test;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class CSVCreator {
    private final List<TestResult> resultList;
    private final String dirName;
    private final String filePath;

    public CSVCreator(String dirName, String fileName, List<TestResult> resultList) {
        this.resultList = resultList;
        this.dirName = "results\\" + dirName;
        this.filePath = this.dirName + "\\" + fileName + ".csv";
    }

    /**
     * Creates the specified file. If not possible for any reason (e.g. it already exists) an exception is thrown.
     * @param file to create.
     * @throws Exception File could not be created.
     */
    public void createFile(File file) {
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
    public void createDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Save the results of the test to a single .csv file.
     */
    public void createCSV() {
        System.out.println("Dirname: " + dirName);
        System.out.println("fileName: " + filePath);
        createDirectory(new File(dirName));

        File file = new File(filePath);
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
}
