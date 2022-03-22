package own;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTesting {
    //private static final CopyOnWriteArrayList<String> CWA = new CopyOnWriteArrayList<>();
    //private static final own.CWA<String> CWA = new own.CWA<>();
    private static final own.CWANoCopy<Integer> CWA = new CWANoCopy<>();

    public static void add(int amount) {
        System.out.println("Adding");
        for (Integer i : CWA) {CWA.add(CWA.size() + 1);}

        //for (int i = 0; i < amount; i++) {CWA.add(CWA.size() + 1);} //Med own.CWA så verkar addering ske innan itereringen börjar
        System.out.println("done - add");
    }

    public static void iterate() {
        System.out.println("Iterating");
        for (Integer i : CWA) {
            System.out.println(i);
        }
        System.out.println("done - iter");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread addingThread = new Thread(() -> add(10));
        Thread iteratingThread = new Thread(CopyOnWriteArrayListTesting::iterate);

        for (int i = 0; i < 5; i++) {
            CWA.add(CWA.size());
        }

        iteratingThread.start();
        addingThread.start();

        iteratingThread.join();
        addingThread.join();

        System.out.println("Final Thread:");

        Thread finalThread = new Thread(CopyOnWriteArrayListTesting::iterate);
        finalThread.start();
        finalThread.join();
    }
}
