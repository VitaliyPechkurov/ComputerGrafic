package Labs.PechkurovV.datastructure;

public class Rectangle {
    private Float left, right, top, bottom;

    public Rectangle() {
        left = bottom = Float.NEGATIVE_INFINITY;
        right = top = Float.POSITIVE_INFINITY;
    }

    public Float getLeft() {
        return left;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public Float getRight() {
        return right;
    }

    public void setRight(Float right) {
        this.right = right;
    }

    public Float getTop() {
        return top;
    }

    public void setTop(Float top) {
        this.top = top;
    }

    public Float getBottom() {
        return bottom;
    }

    public void setBottom(Float bottom) {
        this.bottom = bottom;
    }
}
