 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class FileWrapper {

    private final File file;
    private Object contents;
    private final ArrayList<File> supplements;
    private ArrayList<String> errorLog;

    public FileWrapper(File file, Object contents, ArrayList<File> supplements) {
        this.file = file;
        this.contents = contents;
        this.supplements = supplements;
    }

    public FileWrapper(File file, Object contents, File[] supplements) {
        this.file = file;
        this.contents = contents;
        this.supplements = new ArrayList<File>(Arrays.asList(supplements));
    }

    public FileWrapper(File file, Object contents) {
        this(file, contents, (ArrayList<File>) null);
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the contents
     */
    public Object getContents() {
        return contents;
    }

    /**
     * @return the supplements
     */
    public ArrayList<File> getSupplements() {
        return supplements;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(Object contents) {
        this.contents = contents;
    }

    public boolean hasContents() {
        return contents != null;
    }

    public boolean hasSupplements() {
        return supplements != null && supplements.size() > 0;
    }

    /**
     * @return the errorLog
     */
    public ArrayList<String> getErrorLog() {
        return errorLog;
    }

    /**
     * @param errorLog the errorLog to set
     */
    public void setErrorLog(ArrayList<String> errorLog) {
        this.errorLog = errorLog;
    }
}
