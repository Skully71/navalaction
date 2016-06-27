package navalaction;

import javafx.scene.shape.Line;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class TimeSlotMap {

    public static void main(String[] args) {
        final Map<String, Port> ports = new HashMap<>();
        try (final Reader r = new FileReader("res/Ports_cleanopenworldprodeu1.json")) {
            final char[] ignore = new char["var Ports = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray portArray = reader.readArray();
            reader.close();
            portArray.stream().forEach(p -> {
                //System.out.println(p);
                final Port port = JsonPortBuilder.create((JsonObject) p);
                ports.put(port.name, port);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final List<MetaLine> mash = new ArrayList<>();
        final List<Port> portValues = new ArrayList(ports.values());
        for (int i = 0; i < portValues.size(); i++) {
            for (int j = i; j < portValues.size(); j++) {
                final Port p1 = portValues.get(i);
                final Port p2 = portValues.get(j);
                final MetaLine line = new MetaLine(p1, p2);
                mash.add(line);
            }
        }
        /*
        for (final Port p1 : ports.values()) {
            for (final Port p2 : ports.values()) {
                if (p1 == p2)
                    continue;
                final MetaLine line = new MetaLine(p1.x, p1.z, p2.x, p2.z);
                mash.add(line);
            }
        }
        */
        System.out.println(mash.size());
        // remove lines, starting with the longest
        Collections.sort(mash, (l1,  l2) -> { return (int) (l2.length() - l1.length()); });
        System.out.println(mash.get(0).length());
        int i = 0;
        for (final MetaLine l1 : mash) {
//            if ((l1.p1.name.equals("Somerset") || l1.p2.name.equals("Somerset")) &&
//                    (l1.p2.name.equals("North Inlet") || l1.p2.name.equals("North Inlet")))
//                System.out.println(l1);
            if (l1.isRedundant())
                continue;
            for (final MetaLine l2 : mash) {
                if (l1 == l2 || l2.isRedundant())
                    continue;
                if (l1.intersects(l2)) {
                    //System.out.println(l1 + " " + l2 + " " + l1.intersection(l2));
//                    if ((l1.p1.name.equals("Somerset") || l1.p2.name.equals("Somerset")))
//                        System.out.println(l1 + " " + l2 + " " + l1.intersection(l2));
                    if ((l1.p1.name.equals("Somerset") || l1.p2.name.equals("Somerset")) &&
                            (l1.p2.name.equals("North Inlet") || l1.p2.name.equals("North Inlet"))) {
                        System.out.println("l1=" + l1 + "\n  l2=" + l2 + "\n  i=" + l1.intersection(l2));
                        System.out.println("  " + l1.length() + " " + l2.length());
                    }
                    if ((l1.p1.name.equals("Barranquilla") || l1.p2.name.equals("Barranquilla")) &&
                            (l1.p2.name.equals("Bugle Cay") || l1.p2.name.equals("Bugle Cay"))) {
                        System.out.println("l1=" + l1 + "\n  l2=" + l2 + "\n  i=" + l1.intersection(l2));
                        System.out.println("  " + l1.length() + " " + l2.length());
                    }
                    l1.markRedundant();
                    //l2.markRedundant();
                    //mash.remove(l1);
                    break;
                }
            }
            //System.out.println(i++);
//            if (i > 40000)
//                break;
        }
        // add back lines, starting with the shortest
        Collections.sort(mash, (l1,  l2) -> { return (int) (l1.length() - l2.length()); });
        i = 0;
        for (final MetaLine l1 : mash) {
            if (!l1.isRedundant())
                continue;
            boolean addBack = true;
            for (final MetaLine l2 : mash) {
                if (l1 == l2)
                    continue;
                if (!l2.isRedundant() && l1.intersects(l2)) {
                    addBack = false;
                    break;
                }
            }
            if (addBack)
                l1.setRedundant(false);
        }

        mash.removeIf(l -> { return l.isRedundant(); });
        final JFrame frame = new JFrame("Time Slot Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.getContentPane().add(new JPortMapComponent(ports.values(), mash));
        //frame.getContentPane().add(new JLabel("Hello world"));
        frame.setVisible(true);
    }
}
