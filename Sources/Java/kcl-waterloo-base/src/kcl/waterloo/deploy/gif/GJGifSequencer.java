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
package kcl.waterloo.deploy.gif;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import kcl.waterloo.common.deploy.GJSequencerInterface;
import kcl.waterloo.logging.CommonLogger;

/**
 * GJGifSequencer class for creating animated GIFs.
 *
 *
 *
 * @author ML
 */
public class GJGifSequencer extends GifSequenceWriter implements GJSequencerInterface, ActionListener {

    private final Component component;
    private final BufferedImage img;
    private final ByteArrayOutputStream stream;
    private final ImageOutputStream cachedStream;

    private static final CommonLogger logger = new CommonLogger(GJGifSequencer.class);

    /**
     * Private constructor.
     */
    private GJGifSequencer(File f, ByteArrayOutputStream stream, ImageOutputStream cachedStream, Component c, int interval, boolean loopFlag)
            throws IOException {
        super(cachedStream,
                interval,
                loopFlag
        );
        //File f1 = f;
        this.stream = stream;
        this.cachedStream = cachedStream;
        component = c;
        img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Factory method to create a GIF writer writing to a file output stream.
     *
     * @param fileName String description of the file to write
     * @param comp the Component to animate
     * @param interval interval between frames (ms)
     * @param loopFlag true to loop continuously
     * @return a GIF writer
     * @throws FileNotFoundException
     * @throws IIOException
     * @throws IOException
     */
    public static GJGifSequencer createInstance(String fileName, Component comp, int interval, boolean loopFlag) throws FileNotFoundException, IIOException, IOException {
        File f = new File(fileName);
        ImageOutputStream cachedStream = new FileImageOutputStream(f);
        return new GJGifSequencer(f,
                null,
                cachedStream,
                comp,
                interval,
                loopFlag);
    }

    /**
     * Factory method to create a GIF writer using a memory cache for output.
     *
     * @param comp the Component to animate
     * @param interval interval between frames (ms)
     * @param loopFlag true to loop continuously
     * @return a GIF writer
     * @throws FileNotFoundException
     * @throws IIOException
     * @throws IOException
     */
    public static GJGifSequencer createInstance(Component comp, int interval, boolean loopFlag) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream cachedStream = new MemoryCacheImageOutputStream(stream);
        return new GJGifSequencer(null,
                stream,
                cachedStream,
                comp,
                interval,
                loopFlag);
    }

    /**
     * Add the graphics of the component as a new frame to the GIF.
     *
     * @throws IOException
     */
    @Override
    public void add() throws IOException {
        try {
            component.paint(img.getGraphics());
            this.writeToSequence(img);
        } catch (IllegalStateException ex) {
            logger.error("IllegalStateException: has the writer been closed before stopping a timer?");
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException: no image data available?");
        }
    }

    /**
     * Closes the GIF writer and associated output streams.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (cachedStream instanceof FileImageOutputStream) {
            super.close();
            cachedStream.close();
        } else {
            logger.error("Using a MemoryCacheImageOutputStream: use close(filename)");
        }
    }

    /**
     * Close call used when a memory cached output stream is used. Closes the
     * GIF writer and writes the contents to the named file.
     *
     * @param filename String descriptor of the file to write.
     * @throws IOException
     */
    public void close(String filename) throws IOException {
        FileOutputStream newStream;
        if (filename != null && !filename.isEmpty()) {
            if (stream != null) {
                super.close();
                cachedStream.close();
                newStream = new FileOutputStream(new File(filename));
                stream.writeTo(newStream);
                stream.close();
                newStream.close();
            }
        } else {
            logger.debug("Filename must be specified");
        }
    }

    /**
     * Returns true is this GIF writer users a memory
     * MemoryCacheImageOutputStream, false otherwise.
     *
     * @return true is this GIF writer users a memory
     * MemoryCacheImageOutputStream
     */
    public boolean isMemoryCached() {
        return cachedStream instanceof MemoryCacheImageOutputStream;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            add();
        } catch (IOException ex) {
        }
    }
}
