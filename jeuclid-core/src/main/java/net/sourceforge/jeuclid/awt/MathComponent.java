/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
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

package net.sourceforge.jeuclid.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

import net.sourceforge.jeuclid.DOMBuilder;
import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.ParameterKey;

import org.w3c.dom.Document;

/**
 * A class for displaying MathML content in a AWT Component.
 * 
 * @author Unknown, Max Berger
 * @see net.sourceforge.jeuclid.swing.JMathComponent
 * @version $Revision$
 */
public class MathComponent extends Component {
    /**
     * Logger for this class
     */
    // currently unused
    // private static final Logger LOGGER =
    // Logger.getLogger(MathComponent.class
    // .getName());
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Reference to the MathBase class.
     */
    private MathBase base;

    private boolean debug;

    private Document document;

    private Map<ParameterKey, String> parameters = MathBase
            .getDefaultParameters();

    /**
     * Default constructor.
     */
    public MathComponent() {
        // do nothing.
    }

    /**
     * Sets the rendering parameters.
     * 
     * @param newParameters
     *            the set of parameters.
     */
    public final void setParameters(
            final Map<ParameterKey, String> newParameters) {
        this.parameters = newParameters;
    }

    /**
     * @return the document
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * Gets the mininimum size of this component.
     * 
     * @return A dimension object indicating this component's minimum size.
     */
    @Override
    public Dimension getMinimumSize() {
        if (this.base == null) {
            return new Dimension(1, 1);
        } else {
            final Graphics2D g2d = (Graphics2D) this.getGraphics();
            return new Dimension((int) Math.ceil(this.base.getWidth(g2d)),
                    (int) Math.ceil(this.base.getHeight(g2d)));
        }
    }

    /**
     * Gets the preferred size of this component.
     * 
     * @return A dimension object indicating this component's preferred size.
     */
    @Override
    public Dimension getPreferredSize() {
        return this.getMinimumSize();
    }

    /**
     * Paints this component.
     * 
     * @param g
     *            The graphics context to use for painting.
     */
    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        if (this.base != null) {
            this.base.paint((Graphics2D) g);
        }
    }

    private void redo() {
        if (this.document != null) {
            this.base = new MathBase(this.parameters);
            new DOMBuilder(this.document, this.base);
            this.base.setDebug(this.debug);
        } else {
            this.base = null;
        }
        this.repaint();
    }

    /**
     * Enables, or disables the debug mode.
     * 
     * @param debugMode
     *            Debug mode.
     */
    public void setDebug(final boolean debugMode) {
        this.debug = debugMode;
        this.redo();
    }

    /**
     * @param newDocument
     *            the document to set
     */
    public void setDocument(final Document newDocument) {
        this.document = newDocument;
        this.redo();
    }

}