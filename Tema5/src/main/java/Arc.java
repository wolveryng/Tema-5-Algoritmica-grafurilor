//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc {
    private Point start;
    private Point end;
    private int startNode;
    private int endNode;
    private int node_diam = 30;
    private  int value;

    public Arc(Point start, Point end, int StartNode, int EndNode,int value) {
        this.start = start;
        this.end = end;
        this.startNode = StartNode;
        this.endNode = EndNode;
        this.value = value;
    }

    public void drawArc(Graphics g) {
        if (this.start != null) {
            g.setColor(Color.RED);
            g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
        }

    }

    public double getStartX() {
        return this.start.getX();
    }
    public double getStartY() {return this.start.getY();}

    public double getEndX() {return this.end.getX();}
    public double getEndY() {return this.end.getY();}

    public int getValue() {return this.value;}


    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public int getStartNode() {
        return this.startNode;
    }

    public int getEndNode() {
        return this.endNode;
    }


    public void setStart(int X, int Y) {
        this.start.x = X;
        this.start.y = Y;
    }

    public void setEnd(int X, int Y) {
        this.end.x = X;
        this.end.y = Y;
    }

}

