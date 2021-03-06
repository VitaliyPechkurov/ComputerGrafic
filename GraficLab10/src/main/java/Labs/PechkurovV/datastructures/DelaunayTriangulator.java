package Labs.PechkurovV.datastructures;

import java.util.List;

/**
 * Incremental 2D Delaunay triangulation algorithm implementation.
 */
public class DelaunayTriangulator {

    private List<Vector2D> pointSet;
    private TriangleSoup triangleSoup;

    public DelaunayTriangulator(List<Vector2D> pointSet) {
        this.pointSet = pointSet;
        this.triangleSoup = new TriangleSoup();
    }

    public void triangulate() throws Exception {
        triangleSoup = new TriangleSoup();
        if (pointSet == null || pointSet.size() < 3) {
            throw new Exception("Less than three points in point set.");
        }

        /**
         * In order for the in circumcircle test to not consider the vertices of
         * the super triangle we have to start out with a big triangle
         * containing the whole point set. We have to scale the super triangle
         * to be very large. Otherwise the triangulation is not convex.
         */
        double maxOfAnyCoordinate = 0.0d;

        for (Vector2D vector : getPointSet()) {
            maxOfAnyCoordinate = Math.max(Math.max(vector.x, vector.y), maxOfAnyCoordinate);
        }

        maxOfAnyCoordinate *= 16.0d;
        Vector2D p1 = new Vector2D(0.0d, 3.0d * maxOfAnyCoordinate);
        Vector2D p2 = new Vector2D(3.0d * maxOfAnyCoordinate, 0.0d);
        Vector2D p3 = new Vector2D(-3.0d * maxOfAnyCoordinate, -3.0d * maxOfAnyCoordinate);
        Triangle2D superTriangle = new Triangle2D(p1, p2, p3);
        triangleSoup.add(superTriangle);

        /**
         * If no containing triangle exists, then the vertex is not
         * inside a triangle (this can also happen due to numerical
         * errors) and lies on an edge. In order to find this edge we
         * search all edges of the triangle soup and select the one
         * which is nearest to the point we try to add. This edge is
         * removed and four new edges are added.
         */
        for (Vector2D vector2D : pointSet) {
            Triangle2D triangle = triangleSoup.findContainingTriangle(vector2D);

            if (triangle == null) {
                /**
                 * If no containing triangle exists, then the vertex is not
                 * inside a triangle (this can also happen due to numerical
                 * errors) and lies on an edge. In order to find this edge we
                 * search all edges of the triangle soup and select the one
                 * which is nearest to the point we try to add. This edge is
                 * removed and four new edges are added.
                 */
                Edge2D edge = triangleSoup.findNearestEdge(vector2D);
                Triangle2D first = triangleSoup.findOneTriangleSharing(edge);
                Triangle2D second = triangleSoup.findNeighbour(first, edge);
                Vector2D firstNoneEdgeVertex = first.getNoneEdgeVertex(edge);
                Vector2D secondNoneEdgeVertex = second.getNoneEdgeVertex(edge);
                triangleSoup.remove(first);
                triangleSoup.remove(second);
                Triangle2D triangle1 = new Triangle2D(edge.a, firstNoneEdgeVertex, vector2D);
                Triangle2D triangle2 = new Triangle2D(edge.b, firstNoneEdgeVertex, vector2D);
                Triangle2D triangle3 = new Triangle2D(edge.a, secondNoneEdgeVertex, vector2D);
                Triangle2D triangle4 = new Triangle2D(edge.b, secondNoneEdgeVertex, vector2D);
                triangleSoup.add(triangle1);
                triangleSoup.add(triangle2);
                triangleSoup.add(triangle3);
                triangleSoup.add(triangle4);
                legalizeEdge(triangle1, new Edge2D(edge.a, firstNoneEdgeVertex), vector2D);
                legalizeEdge(triangle2, new Edge2D(edge.b, firstNoneEdgeVertex), vector2D);
                legalizeEdge(triangle3, new Edge2D(edge.a, secondNoneEdgeVertex), vector2D);
                legalizeEdge(triangle4, new Edge2D(edge.b, secondNoneEdgeVertex), vector2D);
            } else {
                Vector2D a = triangle.a;
                Vector2D b = triangle.b;
                Vector2D c = triangle.c;

                triangleSoup.remove(triangle);

                Triangle2D first = new Triangle2D(a, b, vector2D);
                Triangle2D second = new Triangle2D(b, c, vector2D);
                Triangle2D third = new Triangle2D(c, a, vector2D);

                triangleSoup.add(first);
                triangleSoup.add(second);
                triangleSoup.add(third);

                legalizeEdge(first, new Edge2D(a, b), vector2D);
                legalizeEdge(second, new Edge2D(b, c), vector2D);
                legalizeEdge(third, new Edge2D(c, a), vector2D);
            }
        }

        //remove all the triangles to have these fictive vertices
        triangleSoup.removeTrianglesUsing(superTriangle.a);
        triangleSoup.removeTrianglesUsing(superTriangle.b);
        triangleSoup.removeTrianglesUsing(superTriangle.c);
    }

    /**
     * This method legalizes edges by recursively flipping all illegal edges.
     * @param newVertex - new vertex
     */
    private void legalizeEdge(Triangle2D triangle, Edge2D edge, Vector2D newVertex) {
        Triangle2D neighbourTriangle = triangleSoup.findNeighbour(triangle, edge);

        /**
         * If the triangle has a neighbor, then legalize the edge
         */
        if (neighbourTriangle != null) {
            if (neighbourTriangle.isPointInCircumcircle(newVertex)) {
                triangleSoup.remove(triangle);
                triangleSoup.remove(neighbourTriangle);

                Vector2D noneEdgeVertex = neighbourTriangle.getNoneEdgeVertex(edge);

                Triangle2D firstTriangle = new Triangle2D(noneEdgeVertex, edge.a, newVertex);
                Triangle2D secondTriangle = new Triangle2D(noneEdgeVertex, edge.b, newVertex);

                triangleSoup.add(firstTriangle);
                triangleSoup.add(secondTriangle);

                legalizeEdge(firstTriangle, new Edge2D(noneEdgeVertex, edge.a), newVertex);
                legalizeEdge(secondTriangle, new Edge2D(noneEdgeVertex, edge.b), newVertex);
            }
        }
    }

    private List<Vector2D> getPointSet() {
        return pointSet;
    }

    public List<Triangle2D> getTriangles() {
        return triangleSoup.getTriangles();
    }
}