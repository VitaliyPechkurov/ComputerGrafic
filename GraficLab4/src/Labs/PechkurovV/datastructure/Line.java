package Labs.PechkurovV.datastructure;

public class Line {
    private final Trend trend;
    private final Direction direction;
    private Point point;

    public Line(Trend trend, Direction direction) {
        this.trend = trend;
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Trend getTrend() {
        return trend;
    }

    public Direction getDirection() {
        return direction;
    }
}
