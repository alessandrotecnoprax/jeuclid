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

import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractMathElement;

/**
 * This class arrange an element lower to an other element.
 * 
 */
public class MathSub extends AbstractMathElement {

    private static final double DY = 0.43 / 2;

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "msub";

    /**
     * Value of subscriptshift property.
     */
    private int m_subscriptshift = 0;

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathSub(MathBase base) {
        super(base);
    }

    /**
     * Sets value of subscriptshift.
     * 
     * @param subscriptshift
     *            Value of subscriptshift property.
     */
    public void setSubScriptShift(int subscriptshift) {
        m_subscriptshift = subscriptshift;
    }

    /**
     * Gets value of subscriptshift.
     * 
     * @return Value of subscriptshift property.
     */
    public int getSubScriptShift() {
        return m_subscriptshift;
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
    public void paint(Graphics2D g, int posX, int posY) {
        super.paint(g, posX, posY);
        AbstractMathElement e1 = getMathElement(0);
        AbstractMathElement e2 = getMathElement(1);

        int middleshift = (int) (e1.getHeight(g) * DY);

        int e1DescentHeight = e1.getDescentHeight(g);
        if (e1DescentHeight == 0) {
            e1DescentHeight = getFontMetrics(g).getDescent();
        }
        int e1AscentHeight = e1.getAscentHeight(g);
        if (e1AscentHeight == 0) {
            e1AscentHeight = getFontMetrics(g).getAscent();
        }

        int posY1 = posY + e1DescentHeight + e2.getAscentHeight(g)
                - middleshift - 1;

        e1.paint(g, posX, posY);
        e2.paint(g, posX + e1.getWidth(g), posY1);
    }

    /** {@inheritDoc} */
    public int calculateWidth(Graphics2D g) {
        return getMathElement(0).getWidth(g) + getMathElement(1).getWidth(g);
    }

    /** {@inheritDoc} */
    public int calculateAscentHeight(Graphics2D g) {
        return getMathElement(0).getAscentHeight(g);
    }

    /** {@inheritDoc} */
    public int calculateDescentHeight(Graphics2D g) {
        int e2h = Math.max(getMathElement(1).getHeight(g)
                - (int) (getMathElement(0).getHeight(g) * DY), 0);
        return getMathElement(0).getDescentHeight(g) + e2h;
    }

    /** {@inheritDoc} */
    protected int getScriptlevelForChild(AbstractMathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return this.getAbsoluteScriptLevel();
        } else {
            return this.getAbsoluteScriptLevel() + 1;
        }
    }

    /** {@inheritDoc} */
    protected boolean isChildBlock(AbstractMathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return super.isChildBlock(child);
        } else {
            return false;
        }
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return ELEMENT;
    }

}
