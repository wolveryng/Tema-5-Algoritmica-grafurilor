//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node {
    private int coordX;
    private int coordY;
    private int number;

    public Node(int coordX, int coordY, int number) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void drawNode(Graphics g, int node_diam) {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", 1, 15));
        g.fillOval(this.coordX - node_diam / 2, this.coordY - node_diam / 2, node_diam, node_diam);
        g.setColor(Color.WHITE);
        g.drawOval(this.coordX - node_diam / 2, this.coordY - node_diam / 2, node_diam, node_diam);
        if (this.number < 10) {
            g.drawString(Integer.valueOf(this.number).toString(), this.coordX + 13 - node_diam / 2, this.coordY + 20 - node_diam / 2);
        } else {
            g.drawString(Integer.valueOf(this.number).toString(), this.coordX + 8 - node_diam / 2, this.coordY + 20 - node_diam / 2);
        }

    }
}
