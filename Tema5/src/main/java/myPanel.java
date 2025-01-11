import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class myPanel extends JPanel {
    private int nodeNr = 1;
    private final int offset = 50;
    private Vector<Node> listaNoduri = new Vector<>();
    private Vector<Arc> listaArce = new Vector<>();
    private Point pointStart = null;
    private Point pointEnd = null;
    private boolean isDragging = false;
    private boolean isLeft;
    private Node movingNode = null;
    private int value;
    private int[][] adjMatrix;
    private int totalCost;
    private Vector<Arc> listaMSTArce = new Vector<>();


    public myPanel() {
        adjMatrix = new int[nodeNr][nodeNr];  // Inițializează matricea de adiacență pentru 1 nod
        for (int i = 0; i < adjMatrix.length; i++) {
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);  // Inițializează cu INF
        }

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton drawButton = new JButton("Desenează Graficul");
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Creează și deschide o fereastră nouă pentru a desena graficul
                new GraphWindow(listaNoduri, listaMSTArce);
            }
        });

        // Adăugăm butonul la panoul curent
        this.add(drawButton);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                myPanel.this.pointStart = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                myPanel.this.movingNode = null;
                boolean con = true;
                Node startNode;

                if (!myPanel.this.isDragging && SwingUtilities.isLeftMouseButton(e)) {
                    myPanel.this.addNode(e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    Arc arc = null;
                    boolean startFound = false;
                    startNode = null;
                    Node endNode = null;

                    // Găsește nodurile de start și end
                    for (int j = 0; j < myPanel.this.listaNoduri.size(); ++j) {
                        Node existingNode = myPanel.this.listaNoduri.get(j);
                        if (!startFound && myPanel.this.ArcCompare(myPanel.this.pointStart, existingNode)) {
                            startFound = true;
                            startNode = existingNode;
                            j = -1;
                        }

                        if (startFound && existingNode != startNode && myPanel.this.ArcCompare(myPanel.this.pointEnd, existingNode)) {
                            endNode = existingNode;
                            break;
                        }
                    }

                    if (startNode != null && endNode != null) {
                        // Cere valoarea pentru arc
                        String inputValue = JOptionPane.showInputDialog(myPanel.this, "Enter the value for the edge:");
                        if (inputValue != null && !inputValue.trim().isEmpty()) {
                            int value = Integer.parseInt(inputValue);
                            arc = new Arc(myPanel.this.pointStart, myPanel.this.pointEnd, startNode.getNumber(), endNode.getNumber(), value);
                            myPanel.this.listaArce.add(arc);
                            adjMatrix[startNode.getNumber()][endNode.getNumber()] = value;
                            adjMatrix[endNode.getNumber()][startNode.getNumber()] = value;  // graf neorientat
                        }
                    }
                }
                myPanel.this.repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                myPanel.this.pointEnd = e.getPoint();
                myPanel.this.isDragging = true;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    myPanel.this.isLeft = true;
                } else {
                    myPanel.this.isLeft = false;
                    if (myPanel.this.movingNode == null) {
                        Iterator<Node> var2 = myPanel.this.listaNoduri.iterator();
                        while (var2.hasNext()) {
                            Node i = var2.next();
                            if (myPanel.this.ArcCompare(myPanel.this.pointStart, i)) {
                                myPanel.this.movingNode = i;
                            }
                        }
                    }
                }
                myPanel.this.repaint();
            }
        });
    }
    // Algoritmul Prim pentru MST
    public void prim() {
        totalCost = 0;
        listaMSTArce.clear();

        int[] v = new int[nodeNr + 1];
        int[] parent = new int[nodeNr + 1];
        Arrays.fill(v, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        v[1] = 0;

        Set<Integer> N1 = new HashSet<>();
        Set<Integer> N1barat = new HashSet<>();
        for (int i = 1; i <= nodeNr; i++) {
            N1barat.add(i);
        }


        while (N1.size() < nodeNr) {
            int minNode = -1;
            int minWeight = Integer.MAX_VALUE;


            for (int node : N1barat) {
                if (v[node] < minWeight) {
                    minWeight = v[node];
                    minNode = node;
                }
            }


            if (minNode == -1) {
                return;
            }

            N1.add(minNode);
            N1barat.remove(minNode);


            if (parent[minNode] != -1) {
                int weight = adjMatrix[parent[minNode]][minNode];
                totalCost += weight;

                Node startNode = listaNoduri.get(parent[minNode] - 1);
                Node endNode = listaNoduri.get(minNode - 1);

                Arc mstArc = new Arc(myPanel.this.pointStart, myPanel.this.pointEnd, startNode.getNumber(), endNode.getNumber(), weight);
                listaMSTArce.add(mstArc);
            }


            for (int adj = 0; adj < nodeNr; adj++) {
                if (adj != minNode && N1barat.contains(adj)) {
                    int weight = adjMatrix[minNode][adj];
                    if (weight != Integer.MAX_VALUE && v[adj] > weight) {
                        v[adj] = weight;
                        parent[adj] = minNode;
                    }
                }
            }
        }
    }

    public void runKruskalAlgorithm() {
        listaMSTArce.clear();
        totalCost = 0;


        listaArce.sort(Comparator.comparingInt(Arc::getValue)); // sortam arcele dupa cel mai mic cost


        int[] parent = new int[nodeNr];
        int[] rank = new int[nodeNr];

        // Inițializare DSU
        for (int i = 0; i < nodeNr; i++) {
            parent[i] = i;// fiecare nod este propiul parinte
            rank[i] = 0;// rangul intial este 0
        }

//Se parcurg toate muchiile sortate și se adaugă în MST doar dacă extremitățile muchiei nu aparțin aceleiași componente.
        
        for (Arc arc : listaArce) {
            int startNode = arc.getStartNode();
            int endNode = arc.getEndNode();
            if (find(startNode,parent) != find(endNode,parent)) {
                listaMSTArce.add(arc);
                totalCost += arc.getValue();
                union(startNode, endNode,parent,rank);
            }
        }


        JOptionPane.showMessageDialog(this, "Costul total al MST: " + totalCost, "Rezultat Kruskal", JOptionPane.INFORMATION_MESSAGE);
        repaint();
    }

//gaseste reprezentatnul unei componente
    private int find(int node, int[] parent) {
        if (parent[node] != node) {
            parent[node] = find(parent[node], parent); // compresia drumului
        }
        return parent[node];
    }

//uneste 2 componente conexe daca sunt distincte
    private void union(int node1, int node2, int[] parent, int[] rank) {
        int root1 = find(node1, parent);
        int root2 = find(node2, parent);

        if (root1 != root2) {
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                rank[root1]++;
            }
        }
    }



    public int getTotalCostKruskal() {
        runKruskalAlgorithm();
        return totalCost;
    }

    private void resizeAdjMatrix() {
        int newSize = nodeNr;
        int[][] newAdjMatrix = new int[newSize][newSize];

        for (int i = 0; i < adjMatrix.length; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, adjMatrix[i].length);
        }


        for (int i = 0; i < newAdjMatrix.length; i++) {
            for (int j = 0; j < newAdjMatrix[i].length; j++) {
                if (i == j) {
                    newAdjMatrix[i][j] = 0;
                } else {
                    newAdjMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        adjMatrix = newAdjMatrix;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Arc arc : listaArce) {
            arc.drawArc(g);
            double midX = (arc.getStartX() + arc.getEndX()) / 2;
            double midY = (arc.getStartY() + arc.getEndY()) / 2;
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(arc.getValue()), (int) midX, (int) midY);
        }

        for (Node node : listaNoduri) {
            node.drawNode(g, 30);
        }
    }

    // Adaugă un nod în grafic
    public void addNode(int x, int y) {
        Node node = new Node(x, y, nodeNr++);
        listaNoduri.add(node);
        resizeAdjMatrix();
    }

    public double eudist(double X1, double Y1, double X2, double Y2) {
        return Math.sqrt((X1 - X2) * (X1 - X2) + (Y1 - Y2) * (Y1 - Y2));
    }

    public boolean ArcCompare(Point p1, Node n) {
        double dist = this.eudist(p1.getX(), p1.getY(), (double) n.getCoordX(), (double) n.getCoordY());
        return dist <= 30.0;
    }

    public int getTotalCost() {
        myPanel.this.prim();
        System.out.println("Costul total al MST este: " + totalCost);
        return totalCost;
    }


    private class GraphWindow extends JFrame {
        private Vector<Node> noduri;
        private Vector<Arc> mstArce;

        public GraphWindow(Vector<Node> noduri,Vector<Arc> mstArce) {
            this.noduri = noduri;
            this.mstArce = mstArce;

            setTitle("Graficul");
            setSize(800, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(Color.BLACK);
            for (Arc arc : mstArce) {
                Node startNode = noduri.get(arc.getStartNode() - 1);
                Node endNode = noduri.get(arc.getEndNode() - 1);
                g.drawLine(startNode.getCoordX(), startNode.getCoordY(), endNode.getCoordX(), endNode.getCoordY()); // Desenează linie între noduri

                // Afișează costul arcului în mijlocul liniei
                int middleX = (startNode.getCoordX() + endNode.getCoordX()) / 2;
                int middleY = (startNode.getCoordY() + endNode.getCoordY()) / 2;
                g.drawString(String.valueOf(arc.getValue()), middleX, middleY);
            }
            for (Node node : noduri) {
                node.drawNode(g, 30);
            }
        }
    }
}
