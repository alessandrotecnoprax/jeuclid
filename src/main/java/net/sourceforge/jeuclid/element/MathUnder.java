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
import net.sourceforge.jeuclid.element.helpers.AttributesHelper;

/**
 * This class arrange an element under an other element.
 * 
 */
public class MathUnder extends AbstractMathElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "munder";

    /**
     * Accentunder property.
     */
    private boolean m_accentunder = false;

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathUnder(MathBase base) {
        super(base);
    }

    /**
     * Sets accentunder.
     * 
     * @param accentunder
     *            accentunder
     */
    public final void setAccentUnder(boolean accentunder) {
        m_accentunder = accentunder;
    }

    /**
     * Getter for accentunder property.
     * 
     * @return accentunder
     */
    public final boolean getAccentUnder() {
        return m_accentunder;
    }

    /**
     * Space between base and under in pixels
     */
    private int getUnderSpace(Graphics2D g) {
        return AttributesHelper.getPixels(MathUnderOver.UNDER_OVER_SPACE,
                getFontMetrics(g));
    };

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
    public final void paint(Graphics2D g, int posX, int posY) {
        super.paint(g, posX, posY);
        AbstractMathElement e1 = getMathElement(0);
        AbstractMathElement e2 = getMathElement(1);

        if ((getMathElement(0) instanceof MathOperator)
                && ((MathOperator) getMathElement(0)).getMoveableLimits()) {
            int middleshift = (int) (e1.getHeight(g) * MathSubSup.DY);
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
        } else {
            int width = getWidth(g);
            e1.paint(g, posX + (width - e1.getWidth(g)) / 2, posY);
            posY = posY + e1.getDescentHeight(g) + e2.getAscentHeight(g)
                    + getUnderSpace(g) - 1;
            if (getAccentUnder()) {
                posY = posY + getUnderSpace(g);
            }
            e2.paint(g, posX + (width - e2.getWidth(g)) / 2, posY);
        }
    }

    /** {@inheritDoc} */
    public final int calculateWidth(Graphics2D g) {
        if ((getMathElement(0) instanceof MathOperator)) {
            if (((MathOperator) getMathElement(0)).getMoveableLimits()) {
                return getMathElement(0).getWidth(g)
                        + getMathElement(1).getWidth(g);
            }
        }
        return Math.max(getMathElement(0).getWidth(g), getMathElement(1)
                .getWidth(g));
    }

    /** {@inheritDoc} */
    public final int calculateAscentHeight(Graphics2D g) {
        return getMathElement(0).getAscentHeight(g);
    }

    /** {@inheritDoc} */
    public final int calculateDescentHeight(Graphics2D g) {
        int res;
        if ((getMathElement(0) instanceof MathOperator)
                && ((MathOperator) getMathElement(0)).getMoveableLimits()) {
            res = Math.max(getMathElement(0).getDescentHeight(g),
                    getMathElement(1).getHeight(g) - getMiddleShift(g));
        } else {
            res = getMathElement(0).getDescentHeight(g)
                    + getMathElement(1).getHeight(g) + getUnderSpace(g);
        }
        if (getAccentUnder()) {
            res = res + getUnderSpace(g);
        }
        return res;
    }

    /** {@inheritDoc} */
    protected int getScriptlevelForChild(AbstractMathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return this.getAbsoluteScriptLevel();
        } else {
            // TODO: Should depend on type and accent
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
