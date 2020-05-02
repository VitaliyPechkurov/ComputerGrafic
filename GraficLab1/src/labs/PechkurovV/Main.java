package labs.PechkurovV;

import labs.PechkurovV.algorithm.Algo;
import labs.PechkurovV.datastructure.Point;
import labs.PechkurovV.view.PolygonView;


public class Main {
    public static void main(String[] args) {
        Point point = new Point(50, 50);
        System.out.println("Чи є точка в многокутнику? " + Algo.pointIsInPolygon(Algo.createPolygon(), point));
        new PolygonView(Algo.createPolygon(), point);
    }
}
