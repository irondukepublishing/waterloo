 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
package kcl.waterloo.xml;

/**
 * Checking XML files.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLChecker {

    /**
     * Checks the integrity of the specified XML file (which may be gzip
     * compressed). Note that just an XML file may be well-formed but still fail
     * to load. This checks the XML structure only.
     *
     * @param xmlFile
     */
    public static void check(String xmlFile) {
        System.out.println("Checking XML document...");
        try {
            File file = new File(xmlFile);
            if (file.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(GJDecoder.createInputStream(xmlFile));
                NodeList list = doc.getElementsByTagName("*");
                System.out.println("\"" + xmlFile + "\" is well-formed with " + list.getLength() + " xml nodes EDT="
                        + Thread.currentThread());
            } else {
                System.out.println("\"" + xmlFile + "\"  not found." + Thread.currentThread());
            }
        } catch (SAXParseException e) {
            System.out.println("%nError Type" + ": " + e.getMessage() + "%n");
            System.out.println("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
        } catch (SAXException e) {
            System.err.println(e);
        } catch (ParserConfigurationException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
