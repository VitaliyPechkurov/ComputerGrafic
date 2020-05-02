package labs.PechkurovV.datastructure;

import static java.lang.Math.*;

public class Edge {
    private Point a;
    private Point b;
    private boolean alreadyUsed;

    public Edge(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return this.a;
    }
    public Point getB() {
        return  this.b;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    /*
    * Перевірка чи перетинається ребро з горизонтальною прямою
    * проведеною через точку з координатою у
    */
    public Intersection isHorizontalLineIntersect(double y) {
        if(abs(y - a.getY()) < 0.001 || abs(y - b.getY()) < 0.001) {
            return Intersection.VERTEX;
        }
        if(a.getY() < y && b.getY() < y) {
            return Intersection.NO_INTERSECTION;
        }
        if(a.getY() > y && b.getY() > y) {
            return Intersection.NO_INTERSECTION;
        }
        return Intersection.INTERSECTION;
    }

    /*
    * Повертає координату х, у якій дане ребро перетинається з горизонтальною
    * прямою, що проходить через точку з ординатою у
    *
    * Формула
    * (x-x1)/(x2-x1) = (y-y1)/(y2-y1)  ==>
    * x = (((x2-x1)*(y-y1)) / (y2-y1)) + x1
    */
    public double getXHorizontalIntersection(double y) {
        return (((b.getX()-a.getX())*(y - a.getY())/(b.getY()-a.getY()))+a.getX());
    }

    public boolean pointBelongsToEdge(Point point) {
        if (abs(point.getX() - (((b.getX()-a.getX())*(point.getY() - a.getY())
                /(b.getY()-a.getY()))+a.getX())) < 0.001) {
            if((point.getX() <= b.getX() && point.getX() >= a.getX()) ||
                    (point.getX() >= b.getX() && point.getX() <= a.getX())) {
                return (point.getY() <= b.getY() && point.getY() >= a.getY()) ||
                        (point.getY() >= b.getY() && point.getY() <= a.getY());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ребро {" +
                "a=" + a.toString() +
                ", b=" + b.toString() +
                '}';
    }

    /*
    * Перевіряє чи дана пряма перетинає ребро
    *
    * Пряма розбиває площину на дві півплощини
    * У всіх точках першої півплощини рівняння прямої буде > 0
    * У всіх точках іншої півплощини рівняння прямої буде < 0
    */
    public boolean intersectsLine(Line line) {
        double first = line.lineEquation(a);
        double second = line.lineEquation(b);
        return first * second <= 0;
    }
}
