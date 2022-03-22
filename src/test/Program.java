package test;

import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        //Elements, look, get, add, remove, cores, iterations
        List<String[]> arguments = Arrays.asList(
                //14E, 0L, 0G, 100A, 0R
                new String[]{"14", "0", "0", "100", "0", "1", "10"},
                new String[]{"14", "0", "0", "100", "0", "2", "10"},
                new String[]{"14", "0", "0", "100", "0", "4", "10"},
                new String[]{"14", "0", "0", "100", "0", "8", "10"},
                new String[]{"14", "0", "0", "100", "0", "16", "10"},
                new String[]{"14", "0", "0", "100", "0", "32", "10"},
                new String[]{"14", "0", "0", "100", "0", "48", "10"},

                //17E, 0L, 0G, 100A, 0R
                new String[]{"17", "0", "0", "100", "0", "1", "10"},
                new String[]{"17", "0", "0", "100", "0", "2", "10"},
                new String[]{"17", "0", "0", "100", "0", "4", "10"},
                new String[]{"17", "0", "0", "100", "0", "8", "10"},
                new String[]{"17", "0", "0", "100", "0", "16", "10"},
                new String[]{"17", "0", "0", "100", "0", "32", "10"},
                new String[]{"17", "0", "0", "100", "0", "48", "10"},

                //14E, 0L, 0G, 0A, 100R
                new String[]{"14", "0", "0", "0", "100", "1", "10"},
                new String[]{"14", "0", "0", "0", "100", "2", "10"},
                new String[]{"14", "0", "0", "0", "100", "4", "10"},
                new String[]{"14", "0", "0", "0", "100", "8", "10"},
                new String[]{"14", "0", "0", "0", "100", "16", "10"},
                new String[]{"14", "0", "0", "0", "100", "32", "10"},
                new String[]{"14", "0", "0", "0", "100", "48", "10"}

                //17E, 0L, 0G, 0A, 100R
                /*new String[]{"17", "0", "0", "0", "100", "1", "10"},
                new String[]{"17", "0", "0", "0", "100", "2", "10"},
                new String[]{"17", "0", "0", "0", "100", "4", "10"},
                new String[]{"17", "0", "0", "0", "100", "8", "10"},
                new String[]{"17", "0", "0", "0", "100", "16", "10"},
                new String[]{"17", "0", "0", "0", "100", "32", "10"},
                new String[]{"17", "0", "0", "0", "100", "48", "10"}*/
        );

        for(String[] array : arguments) {
            CopyOnWriteArrayListScalabilityTester.main(array);
        }
    }
}
