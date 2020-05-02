package Labs.PechkurovV;

import Labs.PechkurovV.datastructures.Graph;
import Labs.PechkurovV.datastructures.Point;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Point[] points = {
                new Point(3, -5),
                new Point(-5, 0),
                new Point(4, 1),
                new Point(1, 2),
                new Point(-1, 6)};

        boolean[][] matrix = {
                {false, true, true, true, false},
                {true, false, false, true, true},
                {true, false, false, false, true},
                {true, true, false, false, false},
                {false, true, true, false, false}};

        System.out.println("Точка є у трапеції:");
        new Graph(points, matrix, new Point(2, 3));
    }
}
