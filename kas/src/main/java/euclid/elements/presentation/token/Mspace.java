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

/* $Id: Mspace.java 718 2008-04-28 18:46:38Z maxberger $ */

package euclid.elements.presentation.token;

import euclid.Constants;
import euclid.LayoutContext;

import org.w3c.dom.Node;
import org.w3c.dom.mathml.MathMLSpaceElement;

import euclid.elements.AbstractJEuclidElement;
import euclid.elements.support.attributes.AttributesHelper;
import euclid.layout.LayoutInfo;
import euclid.layout.LayoutStage;
import euclid.layout.LayoutView;

/**
 * This class presents a mspace.
 * 
 * @todo linebreak is unimplemented
 * @version $Revision: 718 $
 */
public final class Mspace extends AbstractJEuclidElement implements
        MathMLSpaceElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mspace";

    /** Attribute for width. */
    public static final String ATTR_WIDTH = "width";

    /** Attribute for height. */
    public static final String ATTR_HEIGHT = "height";

    /** Attribute for depth. */
    public static final String ATTR_DEPTH = "depth";

    /** Attribute for linebreak. */
    public static final String ATTR_LINEBREAK = "linebreak";

    private static final long serialVersionUID = 1L;

    /**
     * Creates a math element.
     */
    public Mspace() {
        super();
        this.setDefaultMathAttribute(Mspace.ATTR_DEPTH, Constants.ZERO);
        this.setDefaultMathAttribute(Mspace.ATTR_HEIGHT, Constants.ZERO);
        this.setDefaultMathAttribute(Mspace.ATTR_WIDTH, Constants.ZERO);
        this.setDefaultMathAttribute(Mspace.ATTR_LINEBREAK, "auto");
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new Mspace();
    }

    /**
     * @return Space width
     */
    public String getWidth() {
        return this.getMathAttribute(Mspace.ATTR_WIDTH);
    }

    /**
     * @param width
     *            Space width
     */
    public void setWidth(final String width) {
        this.setAttribute(Mspace.ATTR_WIDTH, width);
    }

    /**
     * @return Space height
     */
    public String getHeight() {
        return this.getMathAttribute(Mspace.ATTR_HEIGHT);
    }

    /**
     * @param height
     *            Space height
     */
    public void setHeight(final String height) {
        this.setAttribute(Mspace.ATTR_HEIGHT, height);
    }

    /**
     * @return Space depth
     */
    public String getDepth() {
        return this.getMathAttribute(Mspace.ATTR_DEPTH);
    }

    /**
     * @param depth
     *            Space depth
     */
    public void setDepth(final String depth) {
        this.setAttribute(Mspace.ATTR_DEPTH, depth);
    }

    /** {@inheritDoc} */
    @Override
    protected void layoutStageInvariant(final LayoutView view,
            final LayoutInfo info, final LayoutStage stage,
            final LayoutContext context) {
        final LayoutContext now = this.applyLocalAttributesToContext(context);
        info.setAscentHeight(AttributesHelper.convertSizeToPt(this
                .getHeight(), now, AttributesHelper.PT), stage);
        info.setDescentHeight(AttributesHelper.convertSizeToPt(this
                .getDepth(), now, AttributesHelper.PT), stage);
        info.setWidth(AttributesHelper.convertSizeToPt(this.getWidth(), now,
                AttributesHelper.PT), stage);
    }

    /** {@inheritDoc} */
    public String getLinebreak() {
        return this.getMathAttribute(Mspace.ATTR_LINEBREAK);
    }

    /** {@inheritDoc} */
    public void setLinebreak(final String linebreak) {
        this.setAttribute(Mspace.ATTR_LINEBREAK, linebreak);
    }
}
