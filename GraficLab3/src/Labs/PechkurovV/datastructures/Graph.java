package Labs.PechkurovV.datastructures;

import Labs.PechkurovV.datastructures.tree.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
    private List<GraphVertex> vertexes; //відсортовано по y координаті
    private List<GraphEdge> edges;
    private final int N;
    private Trapezium trapezium;
    public Node myRoot;

    public Graph(Point[] points, boolean[][] matrix, Point point) throws IOException {
        N = points.length;
        vertexes = new ArrayList<>(N);
        edges = new ArrayList<>();

        for(Point p : points) {
            vertexes.add(new GraphVertex(p));
        }

        for(int i = 0; i < N; ++i) {
            for(int j = i; j < N; ++j) {
                if(matrix[i][j]) {
                    edges.add(new GraphEdge(vertexes.get(i), vertexes.get(j)));
                }
            }
        }
        vertexes.sort(Comparator.comparingDouble(GraphVertex::getY));
        List<GraphVertex> xSortedVetrtexes = new ArrayList<>(N);
        xSortedVetrtexes.addAll(vertexes);
        xSortedVetrtexes.sort(Comparator.comparingDouble(GraphVertex::getX));

        GraphEdge left = new GraphEdge(new GraphVertex(xSortedVetrtexes.get(0).getX(), vertexes.get(0).getY()),
                new GraphVertex(xSortedVetrtexes.get(0).getX(), vertexes.get(N-1).getY()));
        GraphEdge right = new GraphEdge(new GraphVertex(xSortedVetrtexes.get(N-1).getX(), vertexes.get(0).getY()),
                new GraphVertex(xSortedVetrtexes.get(N-1).getX(), vertexes.get(N-1).getY()));

        trapezium = new Trapezium(left, right, vertexes.get(0).getY(), vertexes.get(N-1).getY());
        edges.add(left);
        edges.add(right);
        edges.sort(Comparator.comparingDouble(obj->obj.middleInIntervalX(vertexes.get(0).getY(), vertexes.get(N-1).getY())));

        for(int i = 0; i < edges.size(); ++i) {
            edges.get(i).setName("e" + i);
        }

        myRoot = buildTrapezium(vertexes, edges, trapezium);
        graphVisualization();
        localization(myRoot, point);
    }

    private Node buildTrapezium(List<GraphVertex> V, List<GraphEdge> E, Trapezium T) {
        if(V.size() == 0) {
            return new Node(T, 0); //листок
        }

        List<List<GraphEdge>> Edg = new ArrayList<>(); //ребра трапеції
        List<List<GraphVertex>> Vert = new ArrayList<>(); //вершини трапеції, відсортовані по y-координаті
        List<List<Node>> U = new ArrayList<>(); //список трапецій
        Trapezium[] Tr = new Trapezium[2];
        int weight = 0;
        for(int i = 0; i < 2; ++i) {
            Edg.add(new ArrayList<>());
            Vert.add(new ArrayList<>());
            U.add(new ArrayList<>());
            Tr[i] = new Trapezium();
        }

        float yMed = V.get((V.size()-1)/2).getY(); //медіана
        Tr[0].setMinY(T.getMinY());
        Tr[0].setMaxY(yMed);
        Tr[1].setMinY(yMed);
        Tr[1].setMaxY(T.getMaxY());

        E.sort(Comparator.comparingDouble(obj->obj.middleInIntervalX(Tr[0].getMinY(), Tr[0].getMaxY())));
        for(GraphEdge e : E) {
            for(int i = 0; i < 1; ++i) {
                int in = Tr[i].edgeBelongs(e);
                //e має кінець в Tr[i]
                if(in == 1) {
                    Edg.get(i).add(e);
                    if(Tr[i].vertexBelongs(e.getA())) {
                        Vert.get(i).add(e.getA());
                        weight++;
                    }
                    if(Tr[i].vertexBelongs(e.getB())) {
                        Vert.get(i).add(e.getB());
                        weight++;
                    }
                    //видаляємо дублікати
                    Set<GraphVertex> set = new LinkedHashSet<>(Vert.get(i));
                    Vert.get(i).clear();
                    Vert.get(i).addAll(set);
                    Vert.get(i).sort(Comparator.comparingDouble(GraphVertex::getY));
                }
                //e перетинає Tr[i]
                else if(in == 0 || e == Tr[i].getRight()) {
                    Edg.get(i).add(e);
                    if(Tr[i].getLeft() == null) {
                        Tr[i].setLeft(e);
                    }
                    else {
                        Tr[i].setRight(e);
                        Node n = buildTrapezium(Vert.get(i), Edg.get(i), Tr[i]);
                        U.get(i).add(n);
                        if (e != T.getRight()) {
                            U.get(i).add(new Node(e));
                        }
                        Edg.get(i).clear();
                        Vert.get(i).clear();
                        Edg.get(i).add(e);
                        Tr[i] = new Trapezium();
                        Tr[0].setMinY(T.getMinY());
                        Tr[0].setMaxY(yMed);
                        Tr[1].setMinY(yMed);
                        Tr[1].setMaxY(T.getMaxY());
                        Tr[i].setLeft(e);
                        Tr[i].setRight(null);
                    }
                }
            }
        }


        E.sort(Comparator.comparingDouble(obj->obj.middleInIntervalX(Tr[1].getMinY(), Tr[1].getMaxY())));
        for(GraphEdge e : E) {
            for(int i = 1; i < 2; ++i) {
                int in = Tr[i].edgeBelongs(e);
                //e має кінець в Tr[i]
                if(in == 1) {
                    Edg.get(i).add(e);
                    if(Tr[i].vertexBelongs(e.getA())) {
                        Vert.get(i).add(e.getA());
                        weight++;
                    }
                    if(Tr[i].vertexBelongs(e.getB())) {
                        Vert.get(i).add(e.getB());
                        weight++;
                    }
                    //видаляємо дублікати
                    Set<GraphVertex> set = new LinkedHashSet<>(Vert.get(i));
                    Vert.get(i).clear();
                    Vert.get(i).addAll(set);
                    Vert.get(i).sort(Comparator.comparingDouble(GraphVertex::getY));
                }
                //e перетинає Tr[i]
                else if(in == 0 || e == Tr[i].getRight()) {
                    Edg.get(i).add(e);
                    if(Tr[i].getLeft() == null) {
                        Tr[i].setLeft(e);
                    }
                    else {
                        Tr[i].setRight(e);
                        Node n = buildTrapezium(Vert.get(i), Edg.get(i), Tr[i]);
                        U.get(i).add(n);
                        if (e != T.getRight()) {
                            U.get(i).add(new Node(e));
                        }
                        Edg.get(i).clear();
                        Vert.get(i).clear();
                        Edg.get(i).add(e);
                        Tr[i] = new Trapezium();
                        Tr[0].setMinY(T.getMinY());
                        Tr[0].setMaxY(yMed);
                        Tr[1].setMinY(yMed);
                        Tr[1].setMaxY(T.getMaxY());
                        Tr[i].setLeft(e);
                        Tr[i].setRight(null);
                    }
                }
            }
        }


        Node root = new Node(yMed, weight+1);
        root.setLeft(balance(U.get(0)));
        root.setRight(balance(U.get(1)));
        return root;
    }

    private Node balance(List<Node> U) {
        List<Node> edgs = new ArrayList<>();
        List<Node> leaves = new ArrayList<>();
        for(int i = 0; i < U.size(); ++i) {
            if(i%2 == 0) {
                leaves.add(U.get(i));
            }
            else {
                edgs.add(U.get(i));
            }
        }

        Node root = balancedEdgeTree(edgs, 0, edgs.size()-1);
        balancedTree(root, leaves);
        return  root;
    }

    /**
     * @param edgs - список ребер
     * @return корінь збалансованого дерева
     */
    private Node balancedEdgeTree(List<Node> edgs, int i, int j) {
        if(i == j) {
            return edgs.get(i);
        }
        if(i > j) {
            return null;
        }
        int k = (i+j)/2;
        Node n = edgs.get(k);
        n.setLeft(balancedEdgeTree(edgs, i, k-1));
        n.setRight(balancedEdgeTree(edgs, k+1, j));
        return n;
    }

    private void balancedTree(Node root, List<Node> leaves) {
        if(root == null) {
            return;
        }

        if(root.getLeft() != null && root.getRight() != null) {
            balancedTree(root.getLeft(), leaves);
        }
        if(root.getLeft() == null) {
            root.setLeft(leaves.remove(0));
        }
        if(root.getRight() == null) {
            root.setRight(leaves.remove(0));
            return;
        }
        balancedTree(root.getRight(), leaves);
    }

    private void printNodeShapeToFile(Node root, File file) {
        if(root != null) {
            printNodeShapeToFile(root.getLeft(), file);

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(root.shape());
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            printNodeShapeToFile(root.getRight(), file);
        }
    }

    private void printTreeToFile(Node root, File file) {
        if(root != null) {
            printTreeToFile(root.getLeft(), file);

            if(root.getLeft() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getLeft() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if(root.getRight() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getRight() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            printTreeToFile(root.getRight(), file);
        }
    }

    private void localization(Node root, Point point) {

        if(root.getLeft() == null && root.getRight() == null) {
            Trapezium t = root.getTrapezium();
            if(Float.compare(t.getMinY(), point.getY()) > 0) {
                System.out.println("Точка знаходиться за межами графа");
            }
            else if(Float.compare(t.getMaxY(), point.getY()) < 0) {
                System.out.println("Точка знаходиться за межами графа");
            }
            else if(t.getLeft().getSide(point) == -1 || t.getRight().getSide(point) == 1) {
                System.out.println("Точка знаходиться за межами графа");
            }
            else {
                System.out.println(t);
            }
        }
        else if(root.getEdge() != null) {
            GraphEdge edge = root.getEdge();
            if(edge.getSide(point) == 0) {
                System.out.println("Точка знаходиться на ребрі " + edge);
            }
            else if(edge.getSide(point) == -1) {
                localization(root.getLeft(), point);
            }
            else {
                localization(root.getRight(), point);
            }
        }
        else {
            Float vertex = root.getMedian();
            if(Float.compare(vertex, point.getY()) > 0) {
                localization(root.getLeft(), point);
            }
            else {
                localization(root.getRight(), point);
            }
        }
    }

    public void graphVisualization() throws IOException {
        File file = new File("graphVisualization.txt");
        try(FileWriter writer = new FileWriter(file))
        {
            writer.write("digraph G {\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        printNodeShapeToFile(myRoot, file);
        printTreeToFile(myRoot, file);
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write("}");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
