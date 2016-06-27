package navalaction;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 *
 */
public class JPortMapComponent extends JComponent {
    private final Collection<Port> ports;
    private final Collection<MetaLine> mash;

    public JPortMapComponent(final Collection<Port> ports, final Collection<MetaLine> mash) {
        this.ports = ports;
        this.mash = mash;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        //g2.scale(0.001, 0.001);
        g2.scale(0.0008, 0.0008);
        g2.translate(1000000, 1000000);
        g2.setColor(Color.BLACK);
        //g2.drawLine(0, 0, 10000, 10000);
        for (final Port port : ports) {
            //System.out.println(port);
            g2.fillOval((int) -port.x -3750, (int) port.z - 3750, 7500, 7500);
        }
        mash.forEach(l -> {
            //System.out.println(l);
            g2.drawLine((int) -l.getX1(), (int) l.getY1(), (int) -l.getX2(), (int) l.getY2());
        } );
    }
}
