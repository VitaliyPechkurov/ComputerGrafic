package Labs.PechkurovV.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    private List<GraphEdge> chain;

    public Chain () {
        chain = new ArrayList<>();
    }

    public void addEdge(GraphEdge e) {
        chain.add(e);
    }

    public GraphEdge getEdge(int i) {
        return chain.get(i);
    }

    public int getSize() {
        return chain.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Ланцюг {");
        for(GraphEdge e : chain) {
            s.append("(").append(e.getA().getI() + 1).append(",").append(e.getB().getI() + 1).append(") ");
        }
        s.append("}");
        return s.toString();
    }
}
