package Labs.PechkurovV;

import Labs.PechkurovV.datastructures.Graph;
import Labs.PechkurovV.datastructures.GraphEdge;
import Labs.PechkurovV.datastructures.Point;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Point[] points = {
                new Point(1, -10),
                new Point(7, -8),
                new Point(-2, -5),
                new Point(2, -3),
                new Point(5, 0),
                new Point(3, 3),
                new Point(5, 4),
                new Point(-4, 6),
                new Point(2, 8)};

        boolean[][] matrix = {
                {false, true, true, true, true, false, false, false, false},
                {true, false, false, false, true, false, false, false, false},
                {true, false, false, true, false, true, false, true, false},
                {true, false, true, false, true, true, false, false, false},
                {true, true, false, true, false, true, true, false, false},
                {false, false, true, true, true, false, true, false, true},
                {false, false, false, false, true, true, false, false, true},
                {false, false, true, false, false, false, false, false, true},
                {false, false, false, false, false, true, true, true, false} };

        Graph g = new Graph(points, matrix);
        List<GraphEdge> area = new ArrayList<>();
        Point point = new Point(10, 20);
        try {
            area = g.execAlgorithm(point);
            System.out.println("Граф: ");
            g.print();
            System.out.println();
            System.out.println("Ланцюги отримані із даного графа:");
            g.printChains();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Точка не надежить графу");
        }

        System.out.println("Ділянка, де знаходиться шукана точка("+point.getX() + ", " + point.getY() + ")");
        for(GraphEdge e : area) {
            System.out.println("(" + e.getAx() + ", " + e.getAy() + "), " + "(" + e.getBx() + ", " + e.getBy() + ")");
        }
    }
}
