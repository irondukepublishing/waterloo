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
package kcl.waterloo.winstaller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 *
 * @author ML
 */
public class installer {

    public static boolean exists() {
        String installFolder = System.getProperty("user.home");
        if (System.getProperty("os.name").contains("windows")) {
            installFolder = installFolder.concat(File.separator);
            installFolder = installFolder.concat("My%20Documents");
        } else {
            installFolder = installFolder.concat(File.separator);
            installFolder = installFolder.concat("Documents");
        }
        return new File(installFolder.concat(File.separator).concat("waterloo")).exists();
    }

    public static void install() throws InterruptedException, InvocationTargetException {
        if (!exists()) {
            new localThread().run();
        }
    }

    static public String extractFolder(String zipFile) throws ZipException, IOException {
        System.out.println(zipFile);
        int BUFFER = 2048;
        File file = new File(zipFile);

        ZipFile zip = new ZipFile(file);
        String newPath = zipFile.substring(0, zipFile.length() - 4);

        new File(newPath).mkdir();
        Enumeration zipFileEntries = zip.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(newPath, currentEntry);
            //destFile = new File(newPath, destFile.getName());
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            if (!entry.isDirectory()) {
                BufferedInputStream is = new BufferedInputStream(zip
                        .getInputStream(entry));
                int currentByte;
                // establish buffer for writing file
                byte data[] = new byte[BUFFER];

                // write the current file to disk
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);

                // read and write until last byte is encountered
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }

            if (currentEntry.endsWith(".zip")) {
                // found a zip file, try to open
                extractFolder(destFile.getAbsolutePath());
            }
        }
        return newPath;
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    static class localThread implements Runnable {

        long minPrime;

        localThread() {
        }

        public void run() {

            if (exists()) {
                return;
            } else {
                String installFolder = System.getProperty("user.home");
                if (System.getProperty("os.name").contains("windows")) {
                    installFolder = installFolder.concat(File.separator);
                    installFolder = installFolder.concat("My%20Documents");
                } else {
                    installFolder = installFolder.concat(File.separator);
                    installFolder = installFolder.concat("Documents");
                }
                // Maker downloadFolder
                File zipFile;

                // Create temp folder in documents folder so easy to
                // rename at end.
                File downloadFolder;
                downloadFolder = new File(installFolder.concat(File.separator)
                        .concat("waterloo_tmp_").concat(String.format("%x", installFolder.hashCode())));
                downloadFolder.mkdir();
                
                System.out.format("Downloading to %s\n", downloadFolder.getPath());
                
                URL url;
                try {
                    url = new URL("http://sourceforge.net/projects/waterloo/files/latest/download");
                } catch (MalformedURLException ex) {
                    System.out.println("The requested URL is not available");
                    return;
                }
                InputStream inStream = null;
                try {
                    inStream = url.openStream();
                } catch (IOException ex) {
                    System.out.println("Failed to open  URL as stream");
                    return;
                }
                int n = 0;
                try {
                    n = inStream.available();
                } catch (IOException ex) {
                    System.out.println("IO Exception on stream");
                    return;
                }
                if (n <= 0) {
                    System.out.println("No bytes available from URL");
                    return;
                }


                zipFile = new File(downloadFolder, "waterloo.zip");
                try {
                    zipFile.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Zip file creation failed.");
                    System.out.println(ex);
                    return;
                }

                FileOutputStream outStream;
                try {
                    outStream = new FileOutputStream(zipFile);
                } catch (FileNotFoundException ex) {
                    System.out.println("Zip file FileOutputStream creation failed");
                    return;
                }

                System.out.println("Downloading zip file from http://sourceforge.net/projects/waterloo/files/latest/download");
                System.out.println("... this may take a while");

                try {
                    byte[] b = new byte[8192];
                    int nn = 1;
                    while (nn > 0) {
                        nn = inStream.read(b, 0, b.length);
                        if (nn > 0) {
                            outStream.write(b, 0, nn);
                            outStream.flush();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    return;
                }

                try {
                    inStream.close();
                } catch (IOException ex) {
                    System.out.println("Exception closing inStream");
                }
                try {
                    outStream.close();
                } catch (IOException ex) {
                    System.out.println("Exception closing outStream");
                }



                System.out.println("Extracting project files");
                String s;
                try {
                    s = extractFolder(zipFile.getPath());
                } catch (ZipException ex) {
                    System.out.println("Zip extraction failed");
                    return;
                } catch (IOException ex) {
                    System.out.println("Zip extraction failed");
                    return;
                }

                System.out.format("Extracted to %s\n", s);

                File newFile = new File(s.concat(File.separator).concat("waterloo"));
                s = s.concat(File.separator).concat("..").concat(File.separator)
                        .concat("..").concat(File.separator).concat("waterloo");
                System.out.format("Moving to %s\n", s);
                File finalDest = new File(s);
                if (finalDest.exists()) {
                    System.out.println("That folder already exists. Not overwriting.");
                } else {
                    newFile.renameTo(finalDest);
                }

                deleteFolder(downloadFolder);
            }
        }
    }
}
