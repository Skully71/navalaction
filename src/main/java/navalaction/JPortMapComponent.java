package navalaction;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
        AffineTransform tx = AffineTransform.getScaleInstance(-0.0008, 0.0008);
        tx.concatenate(AffineTransform.getTranslateInstance(-1000000, 1000000));
        g2.transform(tx);
        for (final Port port : ports) {
            //System.out.println(port);
            float h = ((port.conquestFlagTimeSlot + 14) % 24) / 20.0f;
            System.out.println(h);
            g2.setColor(Color.getHSBColor(h, 1, 0.5f));
            g2.fill(port.getSectorShape());
            g2.setColor(Color.BLACK);
            g2.draw(port.getSectorShape());
        }
        /*
        g2.setColor(Color.RED);
        mash.forEach(l -> {
            //System.out.println(l);
            g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
        } );
        */
        g2.setColor(Color.BLACK);
        for (final Port port : ports) {
            g2.fillOval((int) port.x -3750, (int) port.z - 3750, 7500, 7500);
        }
    }
}
