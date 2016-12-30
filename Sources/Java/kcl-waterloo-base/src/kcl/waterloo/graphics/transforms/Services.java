 /*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.transforms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Services {

    private final static ArrayList<Class> clzz = getClasses();

    /**
     * Adds a user specified class to the list of available transforms. The
     * class must implement the GJDataTransformInterface or be an immediate
     * subclass of a class that does.
     *
     * Note: classes should be added before any calls to getAvailable as
     * programmer's can, and are likely to , call that just once in any JVM
     * session and maintain the result in a static final property.
     *
     * @param clz the class to add
     */
    public static void addClass(Class clz) {
        Class[] arr = clz.getInterfaces();
        boolean ok = false;
        for (Class arr1 : arr) {
            if (arr1.equals(GJDataTransformInterface.class)) {
                ok = true;
            }
        }
        if (!ok) {
            arr = clz.getSuperclass().getInterfaces();
        }
        for (Class arr1 : arr) {
            if (arr1.equals(GJDataTransformInterface.class)) {
                ok = true;
            }
        }
        if (ok) {
            clzz.add(clz);
        } else {
        }
    }

    /**
     * Returns a list of the available data transforms
     *
     * @return a list of transforms classes in the
     * kcl.waterloo.graphics.transforms package together with any transforms
     * added with a call to addTransform
     *
     */
    public static LinkedHashMap<String, GJDataTransformInterface> getAvailable() {
        LinkedHashMap<String, GJDataTransformInterface> names = new LinkedHashMap<String, GJDataTransformInterface>();
        if (clzz == null) {
            return names;
        }
        for (Iterator<Class> it = clzz.iterator(); it.hasNext();) {
            Class<?> clz = it.next();
            Method m = null;
            try {
                m = clz.getMethod("getInstance");
            } catch (NoSuchMethodException ex) {
            } catch (SecurityException ex) {
                //.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (m != null) {
                try {
                    GJDataTransformInterface tr;
                    tr = (GJDataTransformInterface) m.invoke(null, (Object[]) null);
                    names.put(tr.getName(), tr);
                } catch (IllegalAccessException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return names;
    }

    /**
     * Returns an instance of the named transform
     *
     * @param name
     * @return the GJDataTransformInterface
     */
    @SuppressWarnings(value = "unchecked")
    public static GJDataTransformInterface getInstanceForName(String name) {
        for (Iterator<Class> it = clzz.iterator(); it.hasNext();) {
            Class<?> clz = it.next();
            Method m = null;

            try {
                m = clz.getMethod("getInstance");
            } catch (NoSuchMethodException ex) {
                //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (m != null) {
                GJDataTransformInterface tr = null;
                try {
                    tr = (GJDataTransformInterface) m.invoke(null, (Object[]) null);
                } catch (IllegalAccessException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (tr != null && tr.getName().equals(name)) {
                    return tr;
                }

            }
        }
        return NOPTransform.getInstance();
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages. Adapted from
     * http://snippets.dzone.com/posts/show/4831 and extended to support use of
     * JAR files
     *
     * @return The classes
     */
    static ArrayList<Class> getClasses() {
        ArrayList<Class> classList = new ArrayList<Class>();

        try {
            Pattern regex;
            String packageName = Services.class.getPackage().getName();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = Services.class.getClassLoader().getResources(path);
            List<String> dirs = new ArrayList<String>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(resource.getFile());
            }
            TreeSet<String> classes = new TreeSet<String>();
            for (String directory : dirs) {
                TreeSet<String> tree = findClasses(directory, packageName, null);
                classes.addAll(tree);
            }
            for (String clazz : classes) {
                try {
                    if (GJDataTransformInterface.class.isAssignableFrom(Class.forName(clazz))
                            && !Class.forName(clazz).isInterface()
                            && !Class.forName(clazz).equals(GJAbstractDataTransform.class)) {
                        classList.add(Class.forName(clazz));
                    }
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classList;
    }

    /**
     * Recursive method used to find all classes in a given path (directory or
     * zip file url). Directories are searched recursively. (zip files are
     * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to
     * support use of JAR files)
     *
     * @param path The base directory or url from which to search.
     * @param packageName The package name for classes found inside the base
     * directory
     * @param regex an optional class name pattern. e.g. .*Test
     * @return The classes
     */
    private static TreeSet<String> findClasses(String path, String packageName, Pattern regex) {
        TreeSet<String> classes = new TreeSet<String>();
        ZipInputStream zip = null;
        String[] split = path.split("!");
        InputStream in = null;
        try {
            if (path.startsWith("file:") && path.contains("!")) {
                URL jar;
                try {
                    jar = new URL(split[0]);
                } catch (MalformedURLException ex) {
                    return classes;
                }
                try {
                    in = jar.openStream();
                } catch (IOException ex) {
                    return classes;
                }
                zip = new ZipInputStream(in);
                ZipEntry entry;
                try {
                    while ((entry = zip.getNextEntry()) != null) {
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
                            if (className.startsWith(packageName) && (regex == null || regex.matcher(className).matches())) {
                                classes.add(className);
                            }
                        }
                    }
                } catch (IOException ex) {
                    return classes;
                }
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (IOException ex) {
                return classes;
            }
        }
        File dir = new File(path);
        if (!dir.exists()) {
            return classes;
        } else {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file.getAbsolutePath(), packageName + "." + file.getName(), regex));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    if (regex == null || regex.matcher(className).matches()) {
                        classes.add(className);
                    }
                }
            }
        }
        return classes;
    }
}
