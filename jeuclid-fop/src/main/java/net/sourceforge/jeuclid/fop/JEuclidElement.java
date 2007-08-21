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

/*
 * Please note: This file was originally taken from the Apache FOP project,
 * available at http://xmlgraphics.apache.org/fop/ It is therefore
 * partially copyright (c) 1999-2007 The Apache Software Foundation.
 * 
 * Parts of the contents are heavily inspired by work done for Barcode4J by
 * Jeremias Maerki, available at http://barcode4j.sf.net/
 */

package net.sourceforge.jeuclid.fop;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.MathMLParserSupport;

import org.apache.fop.apps.FOPException;
import org.apache.fop.datatypes.Length;
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.PropertyList;
import org.apache.fop.fo.properties.FixedLength;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Defines the top-level element for MathML.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public class JEuclidElement extends JEuclidObj {

    private Point2D size;

    private Length baseline;

    /**
     * Default constructor.
     * 
     * @param parent
     *            Parent Node in the FO tree.
     */
    public JEuclidElement(final FONode parent) {
        super(parent);
    }

    /** {@inheritDoc} */
    @Override
    public void processNode(final String elementName, final Locator locator,
            final Attributes attlist, final PropertyList propertyList)
            throws FOPException {
        super.processNode(elementName, locator, attlist, propertyList);
        this.createBasicDocument();
    }

    private void calculate() {
        try {
            final MathBase base = MathMLParserSupport
                    .createMathBaseFromDocument(this.doc, MathBase
                            .getDefaultParameters());
            final Image tempimage = new BufferedImage(1, 1,
                    BufferedImage.TYPE_INT_ARGB);
            final Graphics2D tempg = (Graphics2D) tempimage.getGraphics();
            this.size = new Point2D.Float(base.getWidth(tempg), base
                    .getHeight(tempg));
            this.baseline = new FixedLength(
                    (int) (-base.getDescender(tempg) * 1000));
        } catch (final SAXException x) {
            this.size = new Point(1, 1);
            this.baseline = new FixedLength(0);
        } catch (final IOException x) {
            this.size = new Point(1, 1);
            this.baseline = new FixedLength(0);
        }

    }

    /** {@inheritDoc} */
    @Override
    public Point2D getDimension(final Point2D view) {
        if (this.size == null) {
            this.calculate();
        }
        return this.size;
    }

    /** {@inheritDoc} */
    @Override
    public Length getIntrinsicAlignmentAdjust() {
        if (this.baseline == null) {
            this.calculate();
        }
        return this.baseline;
    }
}
