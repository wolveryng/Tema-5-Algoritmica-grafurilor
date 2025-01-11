import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graf {
    public Graf() {}

    public static void initUI() {
        JFrame f = new JFrame("Algoritmica Grafurilor");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crează un MyPanel
        myPanel panel = new myPanel();

        // Crează un buton pentru calculul costului minim
        JButton btnCalculateCost = new JButton("Calculați Costul Minim Prim");

        // Acțiunea butonului pentru calculul costului minim
        btnCalculateCost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cost = panel.getTotalCost();
                JOptionPane.showMessageDialog(f, "Costul minim al MST este: " + cost);
            }
        });

        JButton btnCalculateCost2 = new JButton("Calculați Costul Minim Kruskal");
        btnCalculateCost2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cost = panel.getTotalCostKruskal();
            }
        });
        // Adaugă butoanele într-un JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCalculateCost);
        buttonPanel.add(btnCalculateCost2);

        // Setează layout-ul principal
        f.setLayout(new BorderLayout());
        f.add(panel, BorderLayout.CENTER);
        f.add(buttonPanel, BorderLayout.SOUTH);

        f.setSize(1280, 720);
        f.setVisible(true);
    }
}
