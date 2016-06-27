/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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
package kcl.waterloo.shell;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

/**
 *
 * @author ML
 */
class ShellWrapper {

    static final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    static final Binding context = new Binding(map);
    private static final ImportCustomizer importCustomizer = new ImportCustomizer();
    private static final ArrayList<String> importList = new ArrayList<String>();
    private static final CompilerConfiguration cc = new CompilerConfiguration();
    private static final GroovyShell shell = new GroovyShell(context, cc);
    private static final ArrayDeque<LinkedHashMap<String, Object>> deque = new ArrayDeque<LinkedHashMap<String, Object>>();

    private ShellWrapper() {
    }

    static GroovyShell getShell() {
        return shell;
    }

    static void addImport(String s) {
        s = s.replaceAll("/", ".");
        try {
            Class.forName(s);
        } catch (ClassNotFoundException ex) {
            System.err.format("Ignoring attemp to add import for %s. Class not found\n", s);
            return;
        }
        if (!importList.contains(s)) {
            importList.add(s);
            importCustomizer.addImports(s);
            cc.addCompilationCustomizers(importCustomizer);
        }
    }

    /**
     * @return the importList
     */
    static ArrayList<String> getImportList() {
        return new ArrayList<String>(importList);
    }

    /**
     * Pushes the present binding to the stack
     */
    static void push() {
        deque.add(new LinkedHashMap<String, Object>(map));
        context.getVariables().clear();
    }

    /**
     * Pulls the last pushed binding from the stack and makes it the current
     * binding
     */
    static void pull() {
        map.clear();
        LinkedHashMap<String, Object> m = deque.remove();
        map.putAll(m);
    }
}
