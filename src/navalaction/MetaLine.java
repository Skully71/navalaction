package navalaction;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 */
public class MetaLine extends Line2D.Double {
    private boolean redundant;
    final Port p1;
    final Port p2;

    public MetaLine(final Port p1, final Port p2) {
        super(p1.x, p1.z, p2.x, p2.z);
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point2D.Double intersection(final Line2D.Double other) {
        /*
0021    int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
0022    if (d == 0) return null;
0023
0024    int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
0025    int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
0026
0027    return new Point(xi,yi);
         */
        final double d = (x1 - x2) * (other.y1 - other.y2) - (y1 - y2) * (other.x1 - other.x2);
        if (d == 0) return null;

        final double xi = ((other.x1 - other.x2) * (x1 * y2 - y1 * x2) - (x1 - x2) * (other.x1 * other.y2 - other.y1 * other.x2)) / d;
        final double yi = ((other.y1 - other.y2) * (x1 * y2 - y1 * x2) - (y1 - y2) * (other.x1 * other.y2 - other.y1 * other.x2)) / d;

        return new Point2D.Double(xi, yi);
    }

    // assert a true intersection, ignoring the end points
    public boolean intersects(final MetaLine other) {
        if (getP1().equals(other.getP1()) || getP1().equals(other.getP2()) ||
                getP2().equals(other.getP1()) || getP2().equals(other.getP2()))
            return false;
        return super.intersectsLine(other);
    }

    public boolean isRedundant() {
        return redundant;
    }

    public double length() {
        return Math.hypot(x1 - x2, y1 - y2);
    }

    @Deprecated
    public void markRedundant() {
        setRedundant(true);
    }

    public void setRedundant(final boolean redundant) {
        this.redundant = redundant;
    }

    @Override
    public String toString() {
        return "MetaLine{" +
                "redundant=" + redundant +
                ", p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
