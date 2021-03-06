package Labs.PechkurovV.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graph {
    private GraphVertex[] vertexes;
    private GraphEdge[][] edges;
    private final int N;
    private List<Chain> chains;

    public class Result {
        public GraphEdge edge;
        public Side side;

        @Override
        public String toString() {
            return "Результат {" +
                    "edge=" + edge +
                    ", side=" + side +
                    '}';
        }

        public Result(GraphEdge edge, Side side) {
            this.edge = edge;
            this.side = side;
        }
    }

    public enum Side {
        LEFT, RIGHT, INTERSECT;
    }

    public Graph(Point[] points, boolean[][] matrix) {
        N = points.length;
        vertexes = new GraphVertex[N];
        edges = new GraphEdge[N][N];
        chains = new ArrayList<>();

        for(int i = 0; i < N; ++i) {
            vertexes[i] = new GraphVertex(i, points[i]);
        }

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                if(matrix[i][j]) {
                    edges[i][j] = new GraphEdge(vertexes[i], vertexes[j]);
                }
            }
        }
    }

    public List<GraphEdge> execAlgorithm(Point point) throws Exception {
        sortVertexes();
        balance();
        splitIntoChains();
        if(!pointIsInGraph(point)) {
            throw new Exception();
        }
        return getArea(0, chains.size()-1, point);
    }

    private void sortVertexes() {
        for(int i = 0; i < N; ++i) {
            List<GraphEdge> out = new ArrayList<>();
            List<GraphEdge> in = new ArrayList<>();
            for(int j = 0; j < N; ++j) {
                if(edges[i][j]!= null) {
                    if (vertexes[i].compareTo(vertexes[j]) < 0) {
                        out.add(edges[i][j]);
                    } else if (vertexes[i].compareTo(vertexes[j]) > 0) {
                        in.add(edges[i][j]);
                    }
                }
            }
            out.sort(Comparator.comparingDouble(GraphEdge::cos));
            in.sort(Comparator.comparingDouble(GraphEdge::cos));

            for(GraphEdge e : out) {
                vertexes[i].addOut(e.getB());
            }
            for(GraphEdge e : in) {
                vertexes[i].addIn(e.getB());
            }
        }
    }

    private void balance() {
        for(int i = 1; i < N-1; ++i) {
            int inWeight = 0;
            for (GraphVertex v : vertexes[i].getIn()) {
                inWeight += edges[v.getI()][i].getWeight();
            }
            vertexes[i].setInWeight(inWeight);
            GraphEdge d = edges[i][vertexes[i].getOut().get(0).getI()];
            if(vertexes[i].getInWeight() > vertexes[i].getOut().size()) {
                d.setWeight(vertexes[i].getInWeight() - vertexes[i].getOut().size() + 1);
                d = edges[vertexes[i].getOut().get(0).getI()][i];
                d.setWeight(vertexes[i].getInWeight() - vertexes[i].getOut().size() + 1);
            }
        }

        for(int i = N-2; i > 0; --i) {
            int outWeight = 0;
            for (GraphVertex v : vertexes[i].getOut()) {
                outWeight += edges[v.getI()][i].getWeight();
            }
            vertexes[i].setOutWeight(outWeight);
            GraphEdge d = edges[i][vertexes[i].getIn().get(0).getI()];
            if(vertexes[i].getOutWeight() > vertexes[i].getInWeight()) {
                d.setWeight(vertexes[i].getOutWeight() - vertexes[i].getInWeight() + d.getWeight());
                d = edges[vertexes[i].getIn().get(0).getI()][i];
                d.setWeight(vertexes[i].getOutWeight() - vertexes[i].getInWeight() + d.getWeight());
            }
        }
    }

    private void splitIntoChains() {
        for(GraphVertex vNext : vertexes[0].getOut()) {
            while(edges[0][vNext.getI()].getWeight() > 0) {
                GraphVertex v = vertexes[0];
                chains.add(new Chain());
                while (v.getOut().size() > 0) {
                    for (GraphVertex out : v.getOut()) {
                        int edgeWeight = edges[v.getI()][out.getI()].getWeight();
                        if (edgeWeight > 0) {
                            edgeWeight--;
                            edges[v.getI()][out.getI()].setWeight(edgeWeight);
                            edges[out.getI()][v.getI()].setWeight(edgeWeight);
                            chains.get(chains.size() - 1).addEdge(edges[v.getI()][out.getI()]);
                            v = out;
                            break;
                        }
                    }
                }
            }
        }
    }

    private Result getSide(Chain chain, int i, int j, Point point) {
        if(i == j && chain.getEdge(i).pointIsBetweenY(point)) {
            GraphEdge e = chain.getEdge(i);
            double equation = e.equation(point);
            if(Double.compare(equation, 0) == 0) {

                //ребро горизонтальне
                if(Float.compare(e.getAy(), e.getBy()) == 0) {
                    if(Float.compare(point.getX(), e.getAx()) < 0 && Float.compare(point.getX(), e.getBx()) < 0) {
                        return new Result(chain.getEdge(i), Side.LEFT);
                    }
                    if(Float.compare(point.getX(), e.getAx()) > 0 && Float.compare(point.getX(), e.getBx()) > 0) {
                        return new Result(chain.getEdge(i), Side.RIGHT);
                    }
                }
                return new Result(chain.getEdge(i), Side.INTERSECT);
            }
            else if(Double.compare(equation, 0) > 0) {
                //(1)
                if(Float.compare(e.getBy(), e.getAy()) > 0 && Float.compare(e.getBx(), e.getAx()) >= 0) {
                    return new Result(e, Side.RIGHT);
                }
                if(Float.compare(e.getAy(), e.getBy()) > 0 && Float.compare(e.getAx(), e.getBx()) >= 0) {
                    return new Result(e, Side.RIGHT);
                }
                //(2)
                if(Float.compare(e.getBy(), e.getAy()) > 0 && Float.compare(e.getBx(), e.getAx()) <= 0) {
                    return new Result(e, Side.LEFT);
                }
                if(Float.compare(e.getAy(), e.getBy()) > 0 && Float.compare(e.getAx(), e.getBx()) <= 0) {
                    return new Result(e, Side.LEFT);
                }
            }
            else {
                //(1)
                if(Float.compare(e.getBy(), e.getAy()) > 0 && Float.compare(e.getBx(), e.getAx()) >= 0) {
                    return new Result(e, Side.LEFT);
                }
                if(Float.compare(e.getAy(), e.getBy()) > 0 && Float.compare(e.getAx(), e.getBx()) >= 0) {
                    return new Result(e, Side.LEFT);
                }
                //(2)
                if(Float.compare(e.getBy(), e.getAy()) > 0 && Float.compare(e.getBx(), e.getAx()) <= 0) {
                    return new Result(e, Side.RIGHT);
                }
                if(Float.compare(e.getAy(), e.getBy()) > 0 && Float.compare(e.getAx(), e.getBx()) <= 0) {
                    return new Result(e, Side.RIGHT);
                }
            }
        }

        int k = (i+j)/2;
        GraphEdge e = chain.getEdge(k);
        if(Float.compare(e.getAy(), e.getBy()) < 0) {
            if(Float.compare(point.getY(), e.getBy()) >= 0) {
                //нижче
                return getSide(chain, k+1, j, point);
            }
            else {
                //вище
                return getSide(chain, i, k, point);
            }
        }
        else {
            if(Float.compare(point.getY(), e.getAy()) >= 0) {
                //нижче
                return getSide(chain, k+1, j, point);
            }
            else {
                //вище
                return getSide(chain, i, k, point);
            }
        }
    }

    private List<GraphEdge> getArea(int i, int j, Point point) {
        Result r1 = getSide(chains.get(i), 0, chains.get(i).getSize()-1, point);
        Result r2 = getSide(chains.get(j), 0, chains.get(i).getSize()-1, point);

        if(r2.side == Side.INTERSECT) {
            List<GraphEdge> result = new ArrayList<>();
            result.add(r2.edge);
            return result;
        }
        else if(r1.side == Side.INTERSECT) {
            List<GraphEdge> result = new ArrayList<>();
            result.add(r1.edge);
            return result;
        }
        if(Math.abs(i - j) == 1) {
            if(r1.side != r2.side) {
                List<GraphEdge> result = new ArrayList<>();
                result.add(r1.edge);
                result.add(r2.edge);
                return result;
            }
        }
        int k = (i+j)/2;
        if(r2.side == Side.LEFT) {
            return getArea(i, k, point);
        }
        else{
            return getArea(j, chains.size()-1, point);
        }
    }

    private boolean pointIsInGraph(Point point) {
        if(Float.compare(vertexes[N-1].getY(), point.getY()) < 0) {
            return false;
        }
        if(Float.compare(vertexes[0].getY(), point.getY()) > 0) {
            return false;
        }
        Side s1 = getSide(chains.get(0), 0, chains.get(0).getSize()-1, point).side;
        Side s2 = getSide(chains.get(chains.size()-1), 0, chains.get(chains.size()-1).getSize()-1, point).side;

        return s1 != Side.LEFT && s2 != Side.RIGHT;
    }

    public void print() {
        for(GraphVertex v : vertexes) {
            System.out.println(v);
        }
    }

    public void printChains() {
        for(Chain c: chains) {
            System.out.println(c);
        }
    }
}
