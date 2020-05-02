package Labs.PechkurovV.datastructures;

public class Trapezium {
    private GraphEdge left = null;
    private GraphEdge right = null;
    private float minY;
    private float maxY;

    public Trapezium() {}

    public Trapezium(GraphEdge left, GraphEdge right, float minY, float maxY) {
        this.left = left;
        this.right = right;
        this.minY = minY;
        this.maxY = maxY;
    }

    public GraphEdge getLeft() {
        return left;
    }

    public void setLeft(GraphEdge left) {
        this.left = left;
    }

    public GraphEdge getRight() {
        return right;
    }

    public void setRight(GraphEdge right) {
        this.right = right;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /*
    * 1 - належить
    * -1 - не належить
    * 0 - перетин
    */
    public int edgeBelongs(GraphEdge e) {
        if(Float.compare(e.getAy(), minY) > 0 && Float.compare(e.getAy(), maxY) < 0 ||
                Float.compare(e.getBy(), minY) > 0  && Float.compare(e.getBy(), maxY) < 0) {
            return 1; //(1), (2), (7), (8)
        }

        if(Float.compare(e.getAy(), minY) == 0 && Float.compare(e.getBy(), maxY) == 0 ||
                Float.compare(e.getBy(), minY) == 0  && Float.compare(e.getAy(), maxY) == 0) {
            return 0; //(4)
        }
        if(Float.compare(e.getAy(), minY) == 0 && Float.compare(e.getBy(), maxY) > 0 ||
                Float.compare(e.getBy(), minY) == 0 && Float.compare(e.getAy(), maxY) > 0) {
            return 0; //(6)
        }
        if(Float.compare(e.getAy(), maxY) == 0 && Float.compare(e.getBy(), minY) < 0 ||
                Float.compare(e.getBy(), maxY) == 0 && Float.compare(e.getAy(), minY) < 0) {
            return 0; //(10)
        }
        if(Float.compare(e.getAy(), maxY) > 0 && Float.compare(e.getBy(), minY) < 0 ||
                Float.compare(e.getBy(), maxY) > 0 && Float.compare(e.getAy(), minY) < 0) {
            return 0; //(9)
        }
        return -1;
    }

    public boolean vertexBelongs(GraphVertex v) {
        return Float.compare(v.getY(), minY) > 0 && Float.compare(v.getY(), maxY) < 0;
    }

    @Override
    public String toString() {
        return "{" +
                "зліва=" + left +
                "справа=" + right +
                "minY=" + minY +
                ", maxY=" + maxY +
                "}";
    }
}
