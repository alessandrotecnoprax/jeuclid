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

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractUnderOverElement;
import net.sourceforge.jeuclid.element.generic.MathElement;

import org.w3c.dom.DOMException;
import org.w3c.dom.mathml.MathMLElement;

/**
 * This class arranges an element under an other element.
 * 
 * @author Unknown
 * @author Max Berger
 */
public class MathUnder extends AbstractUnderOverElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "munder";

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathUnder(final MathBase base) {
        super(base);
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return MathUnder.ELEMENT;
    }

    /** {@inheritDoc} */
    public MathElement getOverscript() {
        return null;
    }

    /** {@inheritDoc} */
    public MathElement getUnderscript() {
        return this.getMathElement(1);
    }

    /** {@inheritDoc} */
    public void setOverscript(final MathMLElement overscript) {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "munder does not have overscript");
    }

    /** {@inheritDoc} */
    public void setUnderscript(final MathMLElement underscript) {
        this.setMathElement(1, underscript);
    }

}
