import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Exercise32_10 {
    public static void main(String[] args) {
        Set<Integer> syncSet = Collections.synchronizedSet(new HashSet<>());

        Thread writer = new Thread(() -> {
            int i = 0;
            while (i < 200) {
                syncSet.add(i);
                System.out.println("Added " + i);
                i++;
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        });

        Thread reader = new Thread(() -> {
            try {
                while (true) {
                    // To iterate safely over a synchronizedSet, lock the set
                    synchronized (syncSet) {
                        Iterator<Integer> it = syncSet.iterator();
                        while (it.hasNext()) {
                            Integer v = it.next();
                            try { Thread.sleep(30); } catch (InterruptedException ignored) {}
                        }
                    }
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                }
            } catch (Exception e) {
                System.out.println("Reader caught: " + e);
                e.printStackTrace();
            }
        });

        writer.start();
        reader.start();
    }
}
