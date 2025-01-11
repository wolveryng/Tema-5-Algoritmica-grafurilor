//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Meniu {
    public Meniu() {
    }

    private static void init() {
        final JFrame f = new JFrame("Meniu");
        JButton b = new JButton("Graf");
        b.setBounds(300, 300, 150, 40);
        f.add(b);
        f.setSize(800, 800);
        f.setLayout((LayoutManager)null);
        f.setVisible(true);
        f.setSize(800, 800);
        f.setLayout((LayoutManager)null);
        f.setResizable(false);
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graf.initUI();
                f.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Meniu.init();
            }
        });
    }
}
