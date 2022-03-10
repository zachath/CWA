package test;

import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        List<String[]> arguments = Arrays.asList(
                new String[]{"14", "34", "33", "17", "16", "1", "10"},
                new String[]{"14", "34", "33", "17", "16", "2", "10"},
                new String[]{"14", "34", "33", "17", "16", "4", "10"},
                new String[]{"14", "34", "33", "17", "16", "8", "10"},
                new String[]{"14", "34", "33", "17", "16", "16", "10"},
                new String[]{"14", "34", "33", "17", "16", "32", "10"},
                new String[]{"14", "34", "33", "17", "16", "48", "10"},

                new String[]{"14", "0", "85", "8", "7", "1", "10"},
                new String[]{"14", "0", "85", "8", "7", "2", "10"},
                new String[]{"14", "0", "85", "8", "7", "4", "10"},
                new String[]{"14", "0", "85", "8", "7", "8", "10"},
                new String[]{"14", "0", "85", "8", "7", "16", "10"},
                new String[]{"14", "0", "85", "8", "7", "32", "10"},
                new String[]{"14", "0", "85", "8", "7", "48", "10"},

                new String[]{"14", "20", "30", "25", "25", "1", "10"},
                new String[]{"14", "20", "30", "25", "25", "2", "10"},
                new String[]{"14", "20", "30", "25", "25", "4", "10"},
                new String[]{"14", "20", "30", "25", "25", "8", "10"},
                new String[]{"14", "20", "30", "25", "25", "16", "10"},
                new String[]{"14", "20", "30", "25", "25", "32", "10"},
                new String[]{"14", "20", "30", "25", "25", "48", "10"},

                new String[]{"17", "34", "33", "17", "16", "1", "10"},
                new String[]{"17", "34", "33", "17", "16", "2", "10"},
                new String[]{"17", "34", "33", "17", "16", "4", "10"},
                new String[]{"17", "34", "33", "17", "16", "8", "10"},
                new String[]{"17", "34", "33", "17", "16", "16", "10"},
                new String[]{"17", "34", "33", "17", "16", "32", "10"},
                new String[]{"17", "34", "33", "17", "16", "48", "10"},

                new String[]{"17", "0", "85", "8", "7", "1", "10"},
                new String[]{"17", "0", "85", "8", "7", "2", "10"},
                new String[]{"17", "0", "85", "8", "7", "4", "10"},
                new String[]{"17", "0", "85", "8", "7", "8", "10"},
                new String[]{"17", "0", "85", "8", "7", "16", "10"},
                new String[]{"17", "0", "85", "8", "7", "32", "10"},
                new String[]{"17", "0", "85", "8", "7", "48", "10"},

                new String[]{"17", "20", "30", "25", "25", "1", "10"},
                new String[]{"17", "20", "30", "25", "25", "2", "10"},
                new String[]{"17", "20", "30", "25", "25", "4", "10"},
                new String[]{"17", "20", "30", "25", "25", "8", "10"},
                new String[]{"17", "20", "30", "25", "25", "16", "10"},
                new String[]{"17", "20", "30", "25", "25", "32", "10"},
                new String[]{"17", "20", "30", "25", "25", "48", "10"},

                new String[]{"20", "34", "33", "17", "16", "1", "10"},
                new String[]{"20", "34", "33", "17", "16", "2", "10"},
                new String[]{"20", "34", "33", "17", "16", "4", "10"},
                new String[]{"20", "34", "33", "17", "16", "8", "10"},
                new String[]{"20", "34", "33", "17", "16", "16", "10"},
                new String[]{"20", "34", "33", "17", "16", "32", "10"},
                new String[]{"20", "34", "33", "17", "16", "48", "10"},

                new String[]{"20", "0", "85", "8", "7", "1", "10"},
                new String[]{"20", "0", "85", "8", "7", "2", "10"},
                new String[]{"20", "0", "85", "8", "7", "4", "10"},
                new String[]{"20", "0", "85", "8", "7", "8", "10"},
                new String[]{"20", "0", "85", "8", "7", "16", "10"},
                new String[]{"20", "0", "85", "8", "7", "32", "10"},
                new String[]{"20", "0", "85", "8", "7", "48", "10"},

                new String[]{"20", "20", "30", "25", "25", "1", "10"},
                new String[]{"20", "20", "30", "25", "25", "2", "10"},
                new String[]{"20", "20", "30", "25", "25", "4", "10"},
                new String[]{"20", "20", "30", "25", "25", "8", "10"},
                new String[]{"20", "20", "30", "25", "25", "16", "10"},
                new String[]{"20", "20", "30", "25", "25", "32", "10"},
                new String[]{"20", "20", "30", "25", "25", "48", "10"}
        );

        for(String[] array : arguments) {
            CopyOnWriteArrayListScalabilityTester.main(array);
        }
    }
}