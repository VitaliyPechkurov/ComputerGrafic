package Labs.PechkurovV.datastructures;

public class GraphVertex implements  Comparable<GraphVertex>{
    private final Point point;

    public GraphVertex(Point point) {
        this.point = point;
    }

    public GraphVertex(float x, float y){
        this.point = new Point(x, y);
    }

    public float getX() {
        return point.getX();
    }

    public float getY() {
        return point.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex gVertex = (GraphVertex) o;
        return point.equals(gVertex.point);
    }

    @Override
    public int compareTo(GraphVertex gVertex) {
        if(this.equals(gVertex)) {
            return 0;
        }
        if(Float.compare(getY(), gVertex.getY()) < 0) {
            return -1;
        }
        if(Float.compare(getY(), gVertex.getY()) == 0 && Float.compare(getX(), gVertex.getX()) > 0) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "{" + point.getX() + ", " + point.getY() + "}";
    }
}
