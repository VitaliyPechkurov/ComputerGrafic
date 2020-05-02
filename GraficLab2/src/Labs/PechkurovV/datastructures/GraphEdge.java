package Labs.PechkurovV.datastructures;

import static java.lang.Math.*;

public class GraphEdge {
    private final GraphVertex a;
    private final GraphVertex b;
    private int weight;

    public GraphEdge(GraphVertex a, GraphVertex b) {
        this.a = a;
        this.b = b;
        weight = 1;
    }
    public GraphVertex getA() {
        return a;
    }
    public GraphVertex getB() {
        return b;
    }
    public float getAx() {
        return a.getX();
    }
    public float getBx() {
        return b.getX();
    }
    public float getAy() {
        return a.getY();
    }
    public float getBy() {
        return  b.getY();
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    private double length() {
        return sqrt(pow(getBx()-getAx(), 2)+(pow(getBy()-getAy(), 2)));
    }

    private double xProjectionLength() {
        return abs(getBx() - getAx());
    }

    public double cos() {
        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) >= 0) {
            return -xProjectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) > 0) {
            return (xProjectionLength() / length());
        }

        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) < 0) {
            return -xProjectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) <= 0) {
            return (xProjectionLength() / length());
        }
        return 0;
    }

    public boolean pointIsBetweenY(Point point) {
        if(Float.compare(point.getY(), a.getY()) <= 0 && Float.compare(point.getY(), b.getY()) >= 0
                && Float.compare(a.getY(), b.getY()) >= 0) {
            return true;
        }
        return Float.compare(point.getY(), a.getY()) >= 0 && Float.compare(point.getY(), b.getY()) <= 0
                && Float.compare(a.getY(), b.getY()) <= 0;
    }

    public double equation(Point point) {
        if(Float.compare(a.getX(), b.getX()) == 0) {
            return point.getX() - a.getX();
        }
        if(Float.compare(a.getY(), b.getY()) == 0) {
            return point.getY() - a.getY();
        }
        return ((point.getX()-a.getX())/(b.getX() - a.getX())) - ((point.getY()-a.getY())/(b.getY()-a.getY()));
    }

    @Override
    public String toString() {
        return "Ребро графа {" +
                "a=" + a +
                ", b=" + b +
                ", вага=" + weight +
                '}';
    }
}
