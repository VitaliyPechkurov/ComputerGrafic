package labs.PechkurovV.view;

import labs.PechkurovV.datastructure.Edge;
import labs.PechkurovV.datastructure.Polygon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class PolygonView extends JPanel {
    public PolygonView(labs.PechkurovV.datastructure.Polygon polygon, labs.PechkurovV.datastructure.Point point) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int taskBarHeight = 40;
        int screenHeight = screenSize.height - taskBarHeight;
        int screenWidth = screenSize.width;
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(){
            Graphics2D g2;

            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.translate(screenWidth / 2,screenHeight / 2);
                g2.scale(1.0, -1.0);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g.setColor(Color.GRAY);
                g2.draw(new Line2D.Double(-1000,0,1000,0));
                g2.draw(new Line2D.Double(0,-1000,0,1000));

                ArrayList<Line2D> edges = transformEdges(polygon);
                g2.setColor(Color.BLUE);
                for (Line2D l : edges){
                    g2.draw(l);
                }
                g2.setColor(Color.RED);
                g2.fill(new Ellipse2D.Double(point.getX()*1, point.getY()*1, 2, 2));
            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private ArrayList<Line2D> transformEdges(Polygon polygon) {
        ArrayList<Line2D> edges = new ArrayList<>();
        for(Edge e : polygon.getEdges()) {
            edges.add(new Line2D.Double(e.getA().getX()*1, e.getA().getY()*1, e.getB().getX()*1, e.getB().getY()*1));
        }
        return edges;
    }
}
