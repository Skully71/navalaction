package navalaction;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class Port {
    public final String name;
    public final double x, y, z;
    public final int conquestFlagTimeSlot;
    private final SortedSet<Point2D.Double> sector;
    private Shape sectorShape;

    public Port(final String name, final double x, final double y, final double z, final int conquestFlagTimeSlot) {
        this.name = name;

        this.x = x;
        this.y = y;
        this.z = z;

        this.conquestFlagTimeSlot = conquestFlagTimeSlot;

        this.sector = new TreeSet<>((p1, p2) -> (int) (angleTo(p2) - angleTo(p1)));
    }

    public void add(final Point2D.Double p) {
        sector.add(p);
    }

    private double angleTo(final Point2D.Double target) {
        double degrees = Math.toDegrees(Math.atan2(target.y - z, target.x - x));
        if (degrees < 0) degrees += 360;
        return degrees;
    }

    Shape getSectorShape() {
        if (sectorShape == null) {
            final Polygon polygon = new Polygon();
            sector.forEach(p -> { polygon.addPoint((int) p.x, (int) p.y); });
            sectorShape = polygon;
        }
        return sectorShape;
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", conquestFlagTimeSlot=" + conquestFlagTimeSlot +
                '}';
    }
}
