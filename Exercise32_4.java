public class Exercise32_4 {
    static class MutableInteger {
        private int value = 0;
        public void increment() { value++; }
        public synchronized void incrementSync() { value++; }
        public int get() { return value; }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Exercise 32.4 - 1000 threads incrementing a shared counter");

        // Unsynchronized run
        MutableInteger m1 = new MutableInteger();
        Thread[] t1 = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            t1[i] = new Thread(() -> m1.increment());
            t1[i].start();
        }
        for (Thread t : t1) t.join();
        System.out.println("Unsynchronized result: " + m1.get() + " (expected 1000)");

        // Synchronized run
        MutableInteger m2 = new MutableInteger();
        Thread[] t2 = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            t2[i] = new Thread(() -> m2.incrementSync());
            t2[i].start();
        }
        for (Thread t : t2) t.join();
        System.out.println("Synchronized result: " + m2.get() + " (expected 1000)");

        // Note: This demonstrates lost updates without synchronization.
    }
}
