package navalaction;

/**
 *
 */
public class Port {
    public final String name;
    public final double x, y, z;

    public Port(final String name, final double x, final double y, final double z) {
        this.name = name;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
