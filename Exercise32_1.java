import javax.swing.*;
import java.awt.*;

public class Exercise32_1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Exercise 32.1 - Concurrent Output");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea area = new JTextArea(20, 60);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setEditable(false);
        frame.add(new JScrollPane(area));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Thread A: print numbers 1..100
        Thread numbers = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                final String s = i + " ";
                SwingUtilities.invokeLater(() -> area.append(s));
                try { Thread.sleep(20); } catch (InterruptedException ignored) {}
            }
        });

        // Thread B: print lowercase letters repeating
        Thread letters = new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                char c = (char)('a' + (i % 26));
                final String s = c + "";
                SwingUtilities.invokeLater(() -> area.append(s));
                try { Thread.sleep(15); } catch (InterruptedException ignored) {}
            }
        });

        // Thread C: print 'b' blocks to mimic listing
        Thread bs = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                SwingUtilities.invokeLater(() -> area.append("b"));
                try { Thread.sleep(5); } catch (InterruptedException ignored) {}
            }
        });

        numbers.start();
        letters.start();
        bs.start();
    }
}
