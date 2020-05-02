package Labs.PechkurovV.datastructures;

public class GraphEdge {
    private final GraphVertex a;
    private final GraphVertex b;
    private final Point middle;
    private String name;

    public GraphEdge(GraphVertex a, GraphVertex b) {
        this.a = a;
        this.b = b;
        middle = new Point((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphVertex getA() {
        return a;
    }
    public GraphVertex getB() {
        return b;
    }
    public float getAx() {
        return a.getX();
    }
    public float getBx() {
        return b.getX();
    }
    public float getAy() {
        return a.getY();
    }
    public float getBy() {
        return  b.getY();
    }

    private double equation(Point point) {
        if(Float.compare(a.getX(), b.getX()) == 0) {
            return point.getX() - a.getX();
        }
        if(Float.compare(a.getY(), b.getY()) == 0) {
            return point.getY() - a.getY();
        }
        return ((point.getX()-a.getX())/(b.getX() - a.getX())) - ((point.getY()-a.getY())/(b.getY()-a.getY()));
    }

    private double getXCoordinate(float y) {
        return ((y-a.getY())*(b.getX()-a.getX()))/(b.getY()-a.getY()) + a.getX();
    }

    public double middleInIntervalX(float yMin, float yMax) {
        GraphVertex top;
        GraphVertex bottom;
        if(Float.compare(a.getY(), b.getY()) > 0) {
            top = a;
            bottom = b;
        }
        else {
            top = b;
            bottom = a;
        }
        if(Float.compare(top.getY(), yMin) <= 0 || Float.compare(bottom.getY(), yMax) >= 0) {
            return Double.MAX_VALUE;
        }

        float xTop, xBottom;
        if(Float.compare(top.getY(), yMax) > 0) {
            xTop = (float)getXCoordinate(yMax);
        }
        else {
            xTop = top.getX();
        }

        if(Float.compare(bottom.getY(), yMin) < 0) {
            xBottom =(float)getXCoordinate(yMin);
        }
        else {
            xBottom = bottom.getX();
        }
        return (xTop+xBottom)/2;
    }


    //-1 - зліва
    //0 - перетин
    // 1 - справа
    public int getSide(Point point) {
        double equation = equation(point);
        if(Double.compare(equation, 0) == 0) {
            //ребро горизонтальне
            if(Float.compare(getAy(), getBy()) == 0) {
                if(Float.compare(point.getX(), getAx()) < 0 && Float.compare(point.getX(), getBx()) < 0) {
                    return -1;
                }
                if(Float.compare(point.getX(), getAx()) > 0 && Float.compare(point.getX(), getBx()) > 0) {
                    return 1;
                }
            }
            return 0;
        }
        else if(Double.compare(equation, 0) > 0) {
            //(1)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) >= 0) {
                return 1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) >= 0) {
                return 1;
            }
            //(2)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) <= 0) {
                return -1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) <= 0) {
                return -1;
            }
        }
        else {
            //(1)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) >= 0) {
                return -1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) >= 0) {
                return -1;
            }
            //(2)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) <= 0) {
                return 1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) <= 0) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return " {" + name + "} ";
    }
}
