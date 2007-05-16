/*
 * Copyright 2007 - 2007 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package net.sourceforge.jeuclid.converter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.ParameterKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Generic converter which uses the registry to do its conversions.
 * <p>
 * Please note: THIS API IS NOT TO BE CONSIDERED STABLE! IT IS STILL
 * EXPERIMENTAL.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public final class Converter {

    private static Converter converter;

    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(Converter.class);

    private Converter() {

    }

    /**
     * Retrieve an instance of the converter singleton class.
     * 
     * @return a Converter object.
     */
    public static Converter getConverter() {
        if (Converter.converter == null) {
            Converter.converter = new Converter();
        }
        return Converter.converter;
    }

    /**
     * Converts an existing file from MathML or ODF to the given type.
     * 
     * @param inFile
     *            input file.
     * @param outFile
     *            output file.
     * @param outFileType
     *            mimetype for the output file.
     * @return true if the conversion was successful.
     * @throws IOException
     *             if an I/O error occurred during read or write.
     */
    public boolean convert(final File inFile, final File outFile,
            final String outFileType) throws IOException {
        final Map<ParameterKey, String> params = MathBase
                .getDefaultParameters();
        params.put(ParameterKey.OutFileType, outFileType);
        return this.convert(inFile, outFile, params);
    }

    /**
     * Converts an existing file from MathML or ODF to the given type.
     * 
     * @param inFile
     *            input file.
     * @param outFile
     *            output file.
     * @param params
     *            rendering parameters.
     * @return true if the conversion was successful.
     * @throws IOException
     *             if an I/O error occurred during read or write.
     */
    public boolean convert(final File inFile, final File outFile,
            final Map<ParameterKey, String> params) throws IOException {
        Document doc;
        try {
            doc = MathMLParserSupport.parseFile(inFile);
            return this.convert(doc, outFile, params);
        } catch (final SAXException e) {
            Converter.LOGGER.error("Failed to parse file:" + inFile, e);
            return false;
        }
    }

    /**
     * Converts an existing file from MathML or ODF to the given type.
     * 
     * @param doc
     *            input document.
     * @param outFile
     *            output file.
     * @param params
     *            parameter set to use for conversion.
     * @return true if the conversion was successful.
     * @throws IOException
     *             if an I/O error occurred during read or write.
     */
    public boolean convert(final Document doc, final File outFile,
            final Map<ParameterKey, String> params) throws IOException {
        final ConverterPlugin plugin = ConverterRegistry.getRegisty()
                .getConverter(params.get(ParameterKey.OutFileType));
        boolean result;
        if (plugin != null) {
            try {
                plugin.convert(MathMLParserSupport
                        .createMathBaseFromDocument(doc, params), outFile);
                result = true;
            } catch (final SAXException ex) {
                Converter.LOGGER.fatal("Failed to process: "
                        + ex.getMessage(), ex);
                if (outFile != null) {
                    outFile.delete();
                }
                result = false;
            }
        } else {
            Converter.LOGGER.fatal("Unsupported output type: "
                    + params.get(ParameterKey.OutFileType));
            result = false;
        }
        return result;
    }

    /**
     * Renders a document into an image.
     * 
     * @param base
     *            MathBase containing the MathML document and its rendering
     *            parameters.
     * @return the rendered image
     * @throws IOException
     *             if an I/O error occurred.
     */
    public BufferedImage render(final MathBase base) throws IOException {

        final Image tempimage = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D tempg = (Graphics2D) tempimage.getGraphics();
        final int width = (int) Math.ceil(base.getWidth(tempg));
        final int height = (int) Math.ceil(base.getHeight(tempg));

        final BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        final Color transparency = new Color(255, 255, 255, 0);

        g.setColor(transparency);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);

        base.paint(g);
        return image;
    }
}