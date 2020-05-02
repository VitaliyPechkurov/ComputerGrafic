package labs.PechkurovV.datastructure;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge> edges;

    public Polygon() {
        edges = new ArrayList<>();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    private int prevEdgeIndex(int currEdgeIndex) {
        if(currEdgeIndex == 0) {
            return edges.size() - 1;
        }
        return currEdgeIndex - 1;
    }
    private int nextEdgeIndex(int curEdgeIndex) {
        return (curEdgeIndex + 1) % edges.size();
    }

    public Edge nextEdge(int curEdgeIndex) {
        return edges.get(nextEdgeIndex(curEdgeIndex));
    }

    public Edge prevEdge(int curEdgeIndex) {
        return edges.get(prevEdgeIndex(curEdgeIndex));
    }
}
