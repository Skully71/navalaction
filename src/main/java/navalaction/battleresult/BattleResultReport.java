package navalaction.battleresult;

import navalaction.util.ClassHelper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 */
public class BattleResultReport {
    final static Pattern CUSTOM_LOG = Pattern.compile("custom_[0-9]*\\.log");

    // [2016-Jul-14 15:43:39.445311] Log: [7/14/2016 1:43:39 PM] [RABBIT] Received BattleResults
    final static Pattern RECEIVED_BATTLERESULTS = Pattern.compile(".* Log: .* Received BattleResults");

    private static Method find(final Map<String, Method> methods, final Object obj, final String name) {
        Method method = methods.get("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
        if (method == null)
            method = methods.get("add" + Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length() - 1));
        if (method == null) {
            //throw new IllegalStateException("Can't find method for " + name + " on " + obj.getClass());
            System.err.println("Warning: Can't find method for " + name + " on " + obj.getClass());
        }
        return method;
    }

    public static void main(final String[] args) throws IOException {
        final Path logs = FileSystems.getDefault().getPath(args[0]);
        Files.find(logs, 1,
                (path, attr) -> {
                    //System.out.println(path.getName(path.getNameCount() - 1));
                    return CUSTOM_LOG.matcher(path.getName(path.getNameCount() - 1).toString()).matches();
                    //return true;
                })
                .forEach(file -> {
                            System.out.println(file);
                            process(file);
                        }
                );
    }

    private static void process(final Path file) {
        try {
            final LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (RECEIVED_BATTLERESULTS.matcher(line).matches()) {
                    //System.out.println(line);
                    reader.readLine(); // skip empty line
                    final BattleResult battleResult = new BattleResult();
                    read(reader, battleResult);
                    //readBattleResult(reader);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void read(final LineNumberReader reader, final Object obj) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        final Map<String, Method> methods = Arrays.stream(obj.getClass().getMethods()).collect(Collectors.toMap(Method::getName, m -> m, (m1, m2) -> m1));
        String line;
        while((line = reader.readLine()) != null) {
            //System.out.println(line);
            int i;
            if (line.charAt(0) == '[')
                return;
            else if ((i = line.indexOf(':')) > 0) {
                final String name = line.substring(0, i).trim();
                String value = line.substring(i + 2);
                if (value.charAt(0) == '"')
                    value = value.substring(1, value.length() - 1);
                final Method method = find(methods, obj, name);
                if (method != null) {
                    try {
                        method.invoke(obj, valueOf(method.getParameterTypes()[0], value));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if ((i = line.indexOf('{')) >= 0) {
                final String name = line.substring(0, i - 1).trim();
                final String className = Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length() - 1);
                final Object subObj = Class.forName("navalaction.battleresult." + className).newInstance();
                read(reader, subObj);
                final Method method = find(methods, obj, name);
                if (method != null) {
                    try {
                        method.invoke(obj, subObj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (line.indexOf('}') >= 0) {
                return;
            } else {
                throw new IllegalArgumentException("Unable to process #" + reader.getLineNumber() + ": " + line);
            }
        }
    }

    private static Object valueOf(Class<?> cls, final String s) {
        try {
            if (cls.isPrimitive()) cls = ClassHelper.primitiveMap.get(cls);
            Method m;
            try {
                m = cls.getMethod("valueOf", String.class);
            } catch (NoSuchMethodException e) {
                m = cls.getMethod("valueOf", Object.class);
            }
            return m.invoke(null, s);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
