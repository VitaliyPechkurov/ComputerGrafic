package Labs.PechkurovV;

import Labs.PechkurovV.drawer.FigureDrawer;
import Labs.PechkurovV.pointreader.PointReader;
import Labs.PechkurovV.quickhull.AlgorithmQuickHall2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PointReader pointReader = new PointReader("pointsQuickHull.txt");
        ArrayList<Point2D> points = pointReader.getPoints();

        System.out.println("Точки, що входять в опуклу оболонку:");
        ArrayList<Point2D> result = AlgorithmQuickHall2D.quickHullAlgoMain(points);
        for (Point2D p : result){
            System.out.println(p);
        }

        new FigureDrawer("Опукла оболонка", result, points,true);
    }
}
