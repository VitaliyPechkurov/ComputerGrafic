package Labs.PechkurovV.datastructure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
    private Node root;

    public KdTree(List<Point> points, Region region) throws IOException {
        root = buildTree(new Rectangle(), new Line(Trend.VERTICAL, Direction.TOP), points);
        graphVisualization();
        List<Point> list = findRegion(root, region);
        if(list.size() == 0) {
            System.out.println("Нічого не знайдено");
        }
        else {
            System.out.println(list);
        }
    }

    private Node buildTree(Rectangle rect, Line line, List<Point> points) {
        if(points == null || points.size() == 0) {
            return new Node(rect, null); //листок
        }

        if(line.getTrend() == Trend.HORIZONTAL) {
            points.sort(Comparator.comparingDouble(Point::getY));
            int middleIndex = (points.size()-1)/2;
            Point middle = points.get(middleIndex);
            line.setPoint(middle);
            Node node = new Node(rect, line);
            if(line.getDirection() == Direction.RIGHT) {
                Rectangle leftRectangle = new Rectangle();
                leftRectangle.setTop(middle.getY());
                leftRectangle.setBottom(rect.getBottom());
                leftRectangle.setLeft(rect.getLeft());
                leftRectangle.setRight(rect.getRight());
                node.setLeft(buildTree(leftRectangle, new Line(Trend.VERTICAL, Direction.BOTTOM), points.subList(0, middleIndex)));

                Rectangle rightRectangle = new Rectangle();
                rightRectangle.setTop(rect.getTop());
                rightRectangle.setBottom(middle.getY());
                rightRectangle.setLeft(rect.getLeft());
                rightRectangle.setRight(rect.getRight());
                node.setRight(buildTree(rightRectangle, new Line(Trend.VERTICAL, Direction.TOP), points.subList(middleIndex+1, points.size())));

                return node;
            }
            else {
                Rectangle rightRectangle = new Rectangle();
                rightRectangle.setTop(middle.getY());
                rightRectangle.setBottom(rect.getBottom());
                rightRectangle.setLeft(rect.getLeft());
                rightRectangle.setRight(rect.getRight());
                node.setRight(buildTree(rightRectangle, new Line(Trend.VERTICAL, Direction.BOTTOM), points.subList(0, middleIndex)));

                Rectangle leftRectangle = new Rectangle();
                leftRectangle.setTop(rect.getTop());
                leftRectangle.setBottom(middle.getY());
                leftRectangle.setLeft(rect.getLeft());
                leftRectangle.setRight(rect.getRight());
                node.setLeft(buildTree(leftRectangle, new Line(Trend.VERTICAL, Direction.TOP), points.subList(middleIndex+1, points.size())));

                return node;
            }
        }
        else {
            points.sort(Comparator.comparingDouble(Point::getX));
            int middleIndex = (points.size()-1)/2;
            Point middle = points.get(middleIndex);
            line.setPoint(middle);
            Node node = new Node(rect, line);

            Rectangle leftRectangle = new Rectangle();
            leftRectangle.setTop(rect.getTop());
            leftRectangle.setBottom(rect.getBottom());
            leftRectangle.setLeft(rect.getLeft());
            leftRectangle.setRight(middle.getX());
            node.setLeft(buildTree(leftRectangle, new Line(Trend.HORIZONTAL, Direction.LEFT), points.subList(0, middleIndex)));

            Rectangle rightRectangle = new Rectangle();
            rightRectangle.setTop(rect.getTop());
            rightRectangle.setBottom(rect.getBottom());
            rightRectangle.setLeft(middle.getX());
            rightRectangle.setRight(rect.getRight());
            node.setRight(buildTree(rightRectangle, new Line(Trend.HORIZONTAL, Direction.RIGHT), points.subList(middleIndex+1, points.size())));

            return node;
        }
    }

//    public void print(Node n) {
//        if(n != null) {
//            print(n.getLeft());
//            System.out.println(n);
//            print(n.getRight());
//        }
//    }

    private void printTreeToFile(Node root, File file) {
        if(root != null) {
            printTreeToFile(root.getLeft(), file);

            if(root.getLeft() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getLeft() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if(root.getRight() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getRight() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            printTreeToFile(root.getRight(), file);
        }
    }

    private List<Point> findRegion(Node node, Region region) {
        List<Point> list = new ArrayList<>();
        if(node != null && node.getLine() != null) {
            if(node.getLine().getTrend() == Trend.VERTICAL) {
                if(Float.compare(node.getLine().getPoint().getX(), region.getBottomLeft().getX()) < 0) {
                    list.addAll(findRegion(node.getRight(), region));
                }
                else if(Float.compare(node.getLine().getPoint().getX(), region.getTopRight().getX()) > 0) {
                    list.addAll(findRegion(node.getLeft(), region));
                }
                else {
                    if(region.isPointInside(node.getLine().getPoint())) {
                        list.add(node.getLine().getPoint());
                    }
                    list.addAll(findRegion(node.getRight(), region));
                    list.addAll(findRegion(node.getLeft(), region));
                }
            }
            else if(node.getLine().getTrend() == Trend.HORIZONTAL){
                if(node.getLine().getDirection() == Direction.RIGHT) {
                    if(Float.compare(node.getLine().getPoint().getY(), region.getBottomLeft().getY()) < 0) {
                        list.addAll(findRegion(node.getLeft(), region));
                    }
                    else if(Float.compare(node.getLine().getPoint().getY(), region.getTopRight().getY()) > 0) {
                        list.addAll(findRegion(node.getRight(), region));
                    }
                    else {
                        if(region.isPointInside(node.getLine().getPoint())) {
                            list.add(node.getLine().getPoint());
                        }
                        list.addAll(findRegion(node.getRight(), region));
                        list.addAll(findRegion(node.getLeft(), region));
                    }
                }
                else if(node.getLine().getDirection() == Direction.LEFT){
                    if(Float.compare(node.getLine().getPoint().getY(), region.getBottomLeft().getY()) < 0) {
                        list.addAll(findRegion(node.getRight(), region));
                    }
                    else if(Float.compare(node.getLine().getPoint().getY(), region.getTopRight().getY()) > 0) {
                        list.addAll(findRegion(node.getLeft(), region));
                    }
                    else {
                        if(region.isPointInside(node.getLine().getPoint())) {
                            list.add(node.getLine().getPoint());
                        }
                        list.addAll(findRegion(node.getRight(), region));
                        list.addAll(findRegion(node.getLeft(), region));
                    }
                }
            }
        }
        return list;
    }

    private void graphVisualization() throws IOException {
        File file = new File("graphVisualization.txt");
        try(FileWriter writer = new FileWriter(file))
        {
            writer.write("digraph G {\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        printTreeToFile(root, file);
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write("}");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
