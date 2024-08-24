package de.vipherian.octulus.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarFile;

public final class JarReflection {

    public static void listClassNames(File file, Consumer<String> forEach) throws IOException {
        listClassNamesAsList(file).forEach(forEach);
    }

    public static List<String> listClassNamesAsList(File file) throws IOException {
        return new ArrayList<>(Arrays.asList(listClassNames(file)));
    }

    public static String[] listClassNames(File file) throws IOException {
        var classNames = new HashSet<String>();
        try (var jarFile = new JarFile(file)) {
            var e = jarFile.entries();
            while (e.hasMoreElements()) {
                var jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames.toArray(new String[0]);
        }
    }

    public static void listClasses(File file, Consumer<Class<?>> forEach) throws IOException, ClassNotFoundException {
        listClassesAsList(file).forEach(forEach);
    }

    public static List<Class<?>> listClassesAsList(File file) throws IOException, ClassNotFoundException {
        return new ArrayList<>(Arrays.asList(listClasses(file)));
    }

    public static Class<?>[] listClasses(File file) throws IOException, ClassNotFoundException {
        var classNames = new HashSet<>(listClassNamesAsList(file));
        var classes = new HashSet<>(classNames.size());
        try (var cl = URLClassLoader.newInstance(
                new URL[] { URI.create("jar:file:" + file + "!/").toURL() })) {
            for (String name : classNames) {
                var clazz = cl.loadClass(name);
                classes.add(clazz);
            }
        }
        return (Class<?>[]) classes.toArray();
    }

}
