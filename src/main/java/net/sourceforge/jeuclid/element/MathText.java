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

package net.sourceforge.jeuclid.element;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractMathElement;
import net.sourceforge.jeuclid.util.StringUtil;

import org.w3c.dom.mathml.MathMLPresentationToken;

/**
 * This class presents text in a equation and contains some utility methods.
 * 
 */
public class MathText extends AbstractMathElement implements
        MathMLPresentationToken {
    /**
     * Logger for this class. Unused.
     */
    // private static final Log logger = LogFactory.getLog(MathText.class);
    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mtext";

    /**
     * Last used layout instance.
     */
    private TextLayout layout;

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathText(final MathBase base) {
        super(base);
    }

    /**
     * Paints this element.
     * 
     * @param g
     *            The graphics context to use for painting.
     * @param posX
     *            The first left position for painting.
     * @param posY
     *            The position of the baseline.
     */
    @Override
    public void paint(final Graphics2D g, final float posX, final float posY) {
        super.paint(g, posX, posY);
        if (this.getText().length() > 0) {
            this.produceTextLayout(g).draw(g, posX, posY);
            // g.draw(this.produceTextLayout(g).getOutline(
            // AffineTransform.getTranslateInstance(posX, posY)));
        }
    }

    private TextLayout produceTextLayout(final Graphics2D g2d) {
        final TextLayout theLayout = new TextLayout(StringUtil
                .convertStringtoAttributedString(this.getText(),
                        this.getMathvariantAsVariant(),
                        this.getFontsizeInPoint(), this.mbase).getIterator(),
                g2d.getFontRenderContext());
        this.layout = theLayout;
        return theLayout;
    }

    /**
     * Returns TextLayout used to paint text of this element.
     * 
     * @return TextLayout instance or null, if layout was not computed yet
     */
    public TextLayout getLayout() {
        return this.layout;
    }

    /** {@inheritDoc} */
    @Override
    public float calculateWidth(final Graphics2D g) {
        if (this.getText().equals("")) {
            return 0;
        } else {
            final Rectangle2D r2d = this.produceTextLayout(g).getBounds();
            return (float) (r2d.getWidth() + r2d.getX());
        }
    }

    /** {@inheritDoc} */
    @Override
    public float calculateAscentHeight(final Graphics2D g) {
        if (this.getText().equals("")) {
            return g.getFontMetrics().getAscent();
        } else {
            // TextLayout.getAscent returns the max ascent for this font,
            // not the one for the actual content!
            final Rectangle2D textBounds = this.produceTextLayout(g)
                    .getBounds();
            return (float) (-textBounds.getY());
        }
    }

    /** {@inheritDoc} */
    @Override
    public float calculateDescentHeight(final Graphics2D g) {
        if (this.getText().equals("")) {
            return g.getFontMetrics().getDescent();
        } else {
            // TextLayout.getDescent returns the max descent for this font,
            // not the one for the actual content!
            final Rectangle2D textBounds = this.produceTextLayout(g)
                    .getBounds();
            return (float) (textBounds.getY() + textBounds.getHeight());
        }
    }

    /**
     * Utility method calculates descent height of the line.
     * 
     * @param font
     *            Current font.
     * @param chars
     *            Array of characrets.
     * @return Value of descent height.
     * @param g
     *            Graphics2D context to use.
     */
    public static float getCharsMaxDescentHeight(final Graphics2D g,
            final Font font, final char[] chars) {
        float result = 0;

        final GlyphVector gv = font.createGlyphVector(new FontRenderContext(
                new AffineTransform(), true, false), chars);
        float descHeight = 0;
        Rectangle2D gr = null;
        for (int i = 0; i < chars.length; i++) {
            gr = gv.getGlyphMetrics(i).getBounds2D();
            descHeight = (float) (gr.getHeight() + gr.getY());
            if (descHeight < 0) {
                descHeight = 0;
            }
            result = Math.max(result, descHeight);
        }

        return result;
    }

    /**
     * Utility method calculates ascent height of the line.
     * 
     * @param font
     *            Current font.
     * @param chars
     *            Array of characrets.
     * @return Value of ascent height.
     * @param g
     *            Graphics2D context to use.
     */
    public static float getCharsMaxAscentHeight(final Graphics2D g,
            final Font font, final char[] chars) {
        float result = 0;

        final GlyphVector gv = font.createGlyphVector(new FontRenderContext(
                new AffineTransform(), true, false), chars);
        float ascHeight = 0;
        Rectangle2D gr = null;
        for (int i = 0; i < chars.length; i++) {
            gr = gv.getGlyphMetrics(i).getBounds2D();
            if (gr.getY() < 0) {
                ascHeight = (float) (-gr.getY());
            } else {
                ascHeight = 0;
            }
            result = Math.max(result, ascHeight);
        }
        return result;
    }

    /**
     * Utility method converts String object to the array of characters.
     * 
     * @param text
     *            String with text.
     * @return Array of characters.
     */
    public static char[] getChars(final String text) {
        final char[] result = new char[text.length()];
        text.getChars(0, text.length(), result, 0);
        return result;
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return MathText.ELEMENT;
    }

}
