package navalaction;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class Port {
    public final int id;
    public final String name;
    public final double x, y, z;
    public final int conquestFlagTimeSlot;
    public final boolean capital;
    private final SortedSet<Point2D> destinations;
    private Shape sectorShape;

    public Port(final int id, final String name, final double x, final double y, final double z, final int conquestFlagTimeSlot, final boolean capital) {
        this.id = id;
        this.name = name;

        this.x = x;
        this.y = y;
        this.z = z;

        this.conquestFlagTimeSlot = conquestFlagTimeSlot;
        this.capital = capital;

        this.destinations = new TreeSet<>((p1, p2) -> (int) (angleTo(p2) - angleTo(p1)));
    }

    public void add(final Point2D p) {
        destinations.add(p);
    }

    private double angleTo(final Point2D target) {
        double degrees = Math.toDegrees(Math.atan2(target.getY() - z, target.getX() - x));
        if (degrees < 0) degrees += 360;
        return degrees;
    }

    public String getName() {
        return name;
    }

    Shape getSectorShape() {
        if (sectorShape == null) {
            final Polygon polygon = new Polygon();
            final Point2D[] d = destinations.toArray(new Point2D[destinations.size()]);
            for (int i = 0; i < d.length; i++) {
                final double x = (this.x + d[i].getX() + d[(i + 1) % d.length].getX()) / 3;
                final double y = (this.z + d[i].getY() + d[(i + 1) % d.length].getY()) / 3;
                polygon.addPoint((int) x, (int) y);
            }
            sectorShape = polygon;
        }
        return sectorShape;
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", conquestFlagTimeSlot=" + conquestFlagTimeSlot +
                '}';
    }
}
