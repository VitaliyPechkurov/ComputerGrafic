package Labs.PechkurovV;

import Labs.PechkurovV.algorithm.JarvisAlgorithm;
import Labs.PechkurovV.drawer.FigureDrawer;
import Labs.PechkurovV.pointreader.PointReader;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PointReader pointReader = new PointReader("pointsJarvis.txt");
        ArrayList<Point2D> points = pointReader.getPoints();
        JarvisAlgorithm jarvisAlgorithm = new JarvisAlgorithm(points);

        ArrayList<Point2D> convexHull = jarvisAlgorithm.getConvexHall();
        new FigureDrawer("Опукла оболонка", convexHull, points, true);

        System.out.println("Розмір опуклої оболонки: " + convexHull.size());
        System.out.println("Точки опуклої оболонки: ");
        for (Point2D p : convexHull) {
            System.out.println(p);
        }
    }
}
