import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;

public class CopyOnWriteArrayListTesting {
    //private static final CopyOnWriteArrayList<String> CWA = new CopyOnWriteArrayList<>();
    private static final CWA<String> CWA = new CWA<>();

    static class Iterating implements Runnable {

        @Override
        public void run() {
            System.out.println("Iterating");
            for (String s : CWA) {
                System.out.println(s);
            }
        }
    }

    static class Adding implements Runnable {
        int amount;
        public Adding(int amount) {
            this.amount = amount;
        }

        @Override
        public void run() {
            System.out.println("Adding");
            for (int i = 0; i < amount; i++) {
                CWA.add("Element: " + (CWA.size() + 1));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread addingThread = new Thread(new Adding(3));
        Thread iteratingThread = new Thread(new Iterating());

        for (int i = 0; i < 2; i++) {
            CWA.add("Element: " + CWA.size());
        }

        iteratingThread.start();
        addingThread.start();

        iteratingThread.join();
        addingThread.join();

        System.out.println("Final Thread:");

        Thread finalThread = new Thread(new Iterating());
        finalThread.start();
        finalThread.join();
    }
}
