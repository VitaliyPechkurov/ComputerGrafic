package Labs.PechkurovV.datastructures;

import java.util.ArrayList;
import java.util.List;

public class GraphVertex implements  Comparable<GraphVertex>{
    private final int i;
    private final Point point;
    private List<GraphVertex> in;
    private List<GraphVertex> out;
    private int inWeight;
    private int outWeight;

    public GraphVertex(int i, Point point) {
        this.i = i;
        this.point = point;
        this.in = new ArrayList<>();
        this.out = new ArrayList<>();
        this.inWeight = 1;
        this.outWeight = 1;
    }

    public void addIn(GraphVertex vertex) {
        in.add(vertex);
    }

    public void addOut(GraphVertex vertex) {
        out.add(vertex);
    }

    public float getX() {
        return point.getX();
    }

    public float getY() {
        return point.getY();
    }

    public int getInWeight() {
        return  this.inWeight;
    }
    public void setInWeight(int weight) {
        this.inWeight = weight;
    }
    public int getOutWeight() {
        return  this.outWeight;
    }
    public void setOutWeight(int weight) {
        this.outWeight = weight;
    }

    public List<GraphVertex> getIn() {
        return in;
    }

    public List<GraphVertex> getOut() {
        return out;
    }

    public int getI() {
        return this.i;
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
        StringBuilder s = new StringBuilder("Вершина графа {" +
                "i=" + i +
                ", точка=" + point.toString() +
                ", in=[");
        for(GraphVertex v : in) {
            s.append(v.i).append(" ");
        }
        s.append("], out=[");
        for(GraphVertex v : out) {
            s.append(v.i).append(" ");
        }
        s.append("]}");
        return s.toString();
    }
}
