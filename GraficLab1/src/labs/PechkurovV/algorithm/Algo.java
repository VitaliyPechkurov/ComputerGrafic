package labs.PechkurovV.algorithm;

import labs.PechkurovV.datastructure.*;
import labs.PechkurovV.datastructure.*;

public class Algo {
    private static int countLeftIntersections(Polygon polygon, Point point) {
        int intNum = 0; //кількість перетинів
        for(int i = 0; i < polygon.getEdges().size(); ++i) {
            Edge e = polygon.getEdges().get(i);
            if(e.isAlreadyUsed()) {
                continue;
            }

            /*
            * Якщо точка належить ребру, що належить многокутнику,
            * то алгоритм повертає непарне число
            */
            if(e.pointBelongsToEdge(point)) {
                return 1;
            }

            /*
            * Якщо є перетин між горизонтальною прямою і ребром многокутника,
            * то збільшуємо кількість перетинів на 1
            */
            if(e.isHorizontalLineIntersect(point.getY()) == Intersection.INTERSECTION &&
                e.getXHorizontalIntersection(point.getY()) < point.getX()) {
                intNum++;
            }

            /*
            * Якщо горизонтальна пряма перетинаяє многокутник в його вершині, то це вироджений випадок
            */
            else if(e.isHorizontalLineIntersect(point.getY()) == Intersection.VERTEX &&
                    e.getXHorizontalIntersection(point.getY()) <= point.getX()) {
                Point s = null; //точка перетину з вершиною
                Edge neighborEdge = null; //ребро, що перетинає дане ребро у вершині v


                //Шукаємо вершину перетину v та сусіднє ребро
                if(Math.abs(e.getA().getY()-point.getY()) < 0.001) {
                    s = e.getA();
                    neighborEdge = polygon.prevEdge(i);
                }
                else {
                    s = e.getB();
                    neighborEdge = polygon.nextEdge(i);
                }

                if(point.equals(s)) {
                    return 1;
                }
                else if(e.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01))) &&
                    neighborEdge.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01)))) {
                    intNum += 2;
                }
                else if(e.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01))) ||
                        neighborEdge.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01)))) {
                    intNum += 1;
                }
                neighborEdge.setAlreadyUsed(true);
                e.setAlreadyUsed(true);
            }
        }
        return intNum;
    }

    public static boolean pointIsInPolygon(Polygon polygon, Point point) {
        int intNum = countLeftIntersections(polygon, point);
        if(intNum == 0) {
            return false;
        }
        return intNum % 2 != 0;
    }

    public static Polygon createPolygon() {
        Polygon polygon = new Polygon();
        polygon.addEdge(new Edge(new Point(-100, 100), new Point(-50, 180)));
        polygon.addEdge(new Edge(new Point(-50, 180), new Point(-30, 140)));
        polygon.addEdge(new Edge(new Point(-30, 140), new Point(30, 140)));
        polygon.addEdge(new Edge(new Point(30, 140), new Point(230, 170)));
        polygon.addEdge(new Edge(new Point(230, 170), new Point(160, 110)));
        polygon.addEdge(new Edge(new Point(160, 110), new Point(240, -60)));
        polygon.addEdge(new Edge(new Point(240, -60), new Point(170, -80)));
        polygon.addEdge(new Edge(new Point(170, -80), new Point(120, -20)));
        polygon.addEdge(new Edge(new Point(120, -20), new Point(40, -20)));
        polygon.addEdge(new Edge(new Point(40, -20), new Point(40, -100)));
        polygon.addEdge(new Edge(new Point(40, -100), new Point(-70, -60)));
        polygon.addEdge(new Edge(new Point(-70, -60), new Point(-100, 100)));

        return polygon;
    }
}
