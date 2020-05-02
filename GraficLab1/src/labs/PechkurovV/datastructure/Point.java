package labs.PechkurovV.datastructure;

import static java.lang.Math.*;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        return (abs(x-other.x) < 0.001) && (abs(y-other.y) < 0.001);
    }

    @Override
    public String toString() {
        return "Точка {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
