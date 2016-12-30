/**
 * Original code from http://elliot.kroo.net/software/java/
 * 
* Modified.
 */
package kcl.waterloo.deploy.gif;

// 
//  GifSequenceWriter.java
//  
//  Created by Elliot Kroo on 2009-04-25.
//
// This work is licensed under the Creative Commons Attribution 3.0 Unported
// License. To view a copy of this license, visit
// http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
// Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

public class GifSequenceWriter {

    protected ImageWriter gifWriter;
    protected ImageWriteParam imageWriteParam = null;
    protected IIOMetadata imageMetaData = null;
    // Added ML: lets us close the outputStream when the gifWriter is closed
    //protected ImageOutputStream outputStream = null;

    /**
     * Creates a new GifSequenceWriter
     *
     *
     * @param outputStream the ImageOutputStream to be written to
     * @param timeBetweenFramesMS the time between frames in milliseconds
     * @param loopContinuously wether the gif should loop repeatedly
     * @throws IIOException if no gif ImageWriters are found
     *
     * @author Elliot Kroo (elliot[at]kroo[dot]net)
     */
    public GifSequenceWriter(
            ImageOutputStream outputStream,
            int timeBetweenFramesMS,
            boolean loopContinuously) throws IOException {

        //this.outputStream = outputStream;
        ImageTypeSpecifier imageTypeSpecifier;
        imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB);

        /*
         * ML: This iterates thtough the available GIF writers till we find
         * one that works for the imageTypeSpecifier
         */
        Iterator<ImageWriter> gifWriters = ImageIO.getImageWritersBySuffix("gif");
        while (gifWriters.hasNext()) {
            gifWriter = gifWriters.next();
            try {
                imageWriteParam = gifWriter.getDefaultWriteParam();
                imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
            } catch (UnsatisfiedLinkError ex) {
                // May be thrown e.g. when a GIF writer looks for missing X11 services
                continue;
            }
            if (imageWriteParam != null && imageMetaData != null) {
                break;
            }
        }

        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionType("LZW");
        imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);

        if (imageMetaData != null) {
            String metaFormatName = imageMetaData.getNativeMetadataFormatName();

            IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

            IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

            graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
            graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
            graphicsControlExtensionNode.setAttribute("transparentColorFlag", "TRUE");
            graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(timeBetweenFramesMS / 10));
            graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

            IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
            commentsNode.setAttribute("CommentExtension", "Created by MAH");

            IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");

            IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

            child.setAttribute("applicationID", "NETSCAPE");
            child.setAttribute("authenticationCode", "2.0");

            int loop = loopContinuously ? 0 : 1;

            child.setUserObject(new byte[]{0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
            appEntensionsNode.appendChild(child);

            imageMetaData.setFromTree(metaFormatName, root);

            gifWriter.setOutput(outputStream);

            gifWriter.prepareWriteSequence(imageMetaData);
        }

    }

    public void writeToSequence(RenderedImage img) throws IOException {
        gifWriter.writeToSequence(
                new IIOImage(
                        img,
                        null,
                        imageMetaData),
                imageWriteParam);
    }

    /**
     * Close this GifSequenceWriter object. This does not close the underlying
     * stream, just finishes off the GIF.
     *
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        gifWriter.endWriteSequence();
    }

    /**
     * Returns an existing child node, or creates and returns a new child node
     * (if the requested node does not exist).
     *
     * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child
     * node.
     * @param nodeName the name of the child node.
     *
     * @return the child node, if found or a new node created with the given
     * name.
     */
    private static IIOMetadataNode getNode(
            IIOMetadataNode rootNode,
            String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
                    == 0) {
                return ((IIOMetadataNode) rootNode.item(i));
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return (node);
    }
}
