package test;

import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        //Elements, look, get, add, remove, cores, iterations
        List<String[]> arguments = Arrays.asList(
                //14E, 50L, 50G, 0A, 0R
                new String[]{"14", "50", "50", "0", "0", "1", "10"},
                new String[]{"14", "50", "50", "0", "0", "2", "10"},
                new String[]{"14", "50", "50", "0", "0", "4", "10"},
                new String[]{"14", "50", "50", "0", "0", "8", "10"},
                new String[]{"14", "50", "50", "0", "0", "16", "10"},
                new String[]{"14", "50", "50", "0", "0", "32", "10"},
                new String[]{"14", "50", "50", "0", "0", "48", "10"},

                //17E, 50L, 50G, 0A, 0R
                new String[]{"17", "50", "50", "0", "0", "1", "10"},
                new String[]{"17", "50", "50", "0", "0", "2", "10"},
                new String[]{"17", "50", "50", "0", "0", "4", "10"},
                new String[]{"17", "50", "50", "0", "0", "8", "10"},
                new String[]{"17", "50", "50", "0", "0", "16", "10"},
                new String[]{"17", "50", "50", "0", "0", "32", "10"},
                new String[]{"17", "50", "50", "0", "0", "48", "10"}
        );

        for(String[] array : arguments) {
            CopyOnWriteArrayListScalabilityTester.main(array);
        }
    }
}
