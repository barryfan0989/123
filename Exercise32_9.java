import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Exercise32_9 {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();

        // Writer thread: keeps adding numbers to the set
        Thread writer = new Thread(() -> {
            int i = 0;
            while (i < 200) {
                set.add(i);
                System.out.println("Added " + i);
                i++;
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        });

        // Reader thread: obtains iterator and traverses repeatedly
        Thread reader = new Thread(() -> {
            try {
                while (true) {
                    Iterator<Integer> it = set.iterator();
                    while (it.hasNext()) {
                        Integer v = it.next();
                        // Slow down so writer can modify concurrently
                        try { Thread.sleep(30); } catch (InterruptedException ignored) {}
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
