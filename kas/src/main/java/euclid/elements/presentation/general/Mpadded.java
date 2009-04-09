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

/* $Id: Mpadded.java 752 2008-05-19 12:40:13Z maxberger $ */

package euclid.elements.presentation.general;


import org.w3c.dom.Node;
import org.w3c.dom.mathml.MathMLPaddedElement;

import euclid.elements.presentation.AbstractContainer;

/**
 * This class implemented the mpadded element.
 * 
 * @todo none of the attributes are actually implemented yet.
 * @version $Revision: 752 $
 */
public final class Mpadded extends AbstractContainer implements
        MathMLPaddedElement {

    /** constant for depth attribute. */
    public static final String ATTR_DEPTH = "depth";

    /** constant for height attribute. */
    public static final String ATTR_HEIGHT = "height";

    /** constant for lspace attribute. */
    public static final String ATTR_LSPACE = "lspace";

    /** constant for width attribute. */
    public static final String ATTR_WIDTH = "width";

    /**
     * The MathML element name for this class.
     */
    public static final String ELEMENT = "mpadded";

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public Mpadded() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new Mpadded();
    }

    /** {@inheritDoc} */
    public String getDepth() {
        return this.getMathAttribute(Mpadded.ATTR_DEPTH);
    }

    /** {@inheritDoc} */
    public String getHeight() {
        return this.getMathAttribute(Mpadded.ATTR_HEIGHT);
    }

    /** {@inheritDoc} */
    public String getLspace() {
        return this.getMathAttribute(Mpadded.ATTR_LSPACE);
    }

    /** {@inheritDoc} */
    public String getWidth() {
        return this.getMathAttribute(Mpadded.ATTR_WIDTH);
    }

    /** {@inheritDoc} */
    public void setDepth(final String depth) {
        this.setAttribute(Mpadded.ATTR_DEPTH, depth);
    }

    /** {@inheritDoc} */
    public void setHeight(final String height) {
        this.setAttribute(Mpadded.ATTR_HEIGHT, height);
    }

    /** {@inheritDoc} */
    public void setLspace(final String lspace) {
        this.setAttribute(Mpadded.ATTR_LSPACE, lspace);
    }

    /** {@inheritDoc} */
    public void setWidth(final String width) {
        this.setAttribute(Mpadded.ATTR_WIDTH, width);
    }

}