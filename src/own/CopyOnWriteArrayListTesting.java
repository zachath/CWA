package own;

public class CopyOnWriteArrayListTesting {
    //private static final CopyOnWriteArrayList<String> own.CWA = new CopyOnWriteArrayList<>();
    //private static final own.CWA<String> own.CWA = new own.CWA<>();
    private static final CWALocks<String> CWA = new CWALocks<>();

    public static void add(int amount) {
        System.out.println("Adding");
        for (int i = 0; i < amount; i++) {
            CWA.add("Element: " + (CWA.size() + 1));
        }
    }

    public static void iterate() {
        System.out.println("Iterating");
        for (String s : CWA) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //Körde med 100 och då printade den första interationen 55 element, så det är timing.
        Thread addingThread = new Thread(() -> add(10));
        Thread iteratingThread = new Thread(CopyOnWriteArrayListTesting::iterate);

        double start = System.nanoTime();

        for (int i = 0; i < 5; i++) {
            CWA.add("Element: " + CWA.size());
        }

        iteratingThread.start();
        addingThread.start();

        iteratingThread.join();
        addingThread.join();

        System.out.println("Final Thread:");

        Thread finalThread = new Thread(CopyOnWriteArrayListTesting::iterate);
        finalThread.start();
        finalThread.join();

        //System.out.println((System.nanoTime() - start) / 1.0E9);
    }
}
