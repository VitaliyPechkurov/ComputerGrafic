package labs.PechkurovV.datastructure;

public class Line {
    private Point a;
    private Point b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public double lineEquation(Point point) {
        return ((point.getX()-a.getX())/(b.getX()-a.getX()))-((point.getY()-a.getY())/(b.getY()-a.getY()));
    }
}
