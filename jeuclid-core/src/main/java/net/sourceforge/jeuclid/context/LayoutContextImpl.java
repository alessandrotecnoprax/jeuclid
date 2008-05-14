/*
 * Copyright 2002 - 2008 JEuclid, http://jeuclid.sf.net
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

package net.sourceforge.jeuclid.context;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import net.sourceforge.jeuclid.Constants;
import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.MutableLayoutContext;

/**
 * @version $Revision$
 */
public class LayoutContextImpl implements MutableLayoutContext {

    private static final class SingletonHolder {
        private static LayoutContextImpl instance = new LayoutContextImpl();

        private SingletonHolder() {
        }
    }

    private final Map<Parameter, Object> context;

    // CHECKSTYLE:OFF
    private LayoutContextImpl() {
        this.context = new TreeMap<Parameter, Object>();
        this.context.put(Parameter.MATHSIZE, Constants.DEFAULT_FONTSIZE);
        this.context.put(Parameter.SCRIPTMINSIZE, 8f);
        this.context.put(Parameter.ANTIALIAS_MINSIZE, 10f);
        this.context.put(Parameter.SCRIPTSIZEMULTIPLIER,
                Constants.DEFAULT_SCIPTSIZEMULTIPLIER);
        this.context.put(Parameter.SCRIPTLEVEL, 0);
        this.context.put(Parameter.DISPLAY, Display.BLOCK);
        this.context.put(Parameter.DEBUG, false);
        this.context.put(Parameter.ANTIALIAS, true);
        this.context.put(Parameter.MATHCOLOR, Color.BLACK);
        this.context.put(Parameter.MATHBACKGROUND, null);

        final List<String> fontsSanserif = new Vector<String>();
        final List<String> fontsSerif = new Vector<String>();
        final List<String> fontsMonospaced = new Vector<String>();
        final List<String> fontsScript = new Vector<String>();
        final List<String> fontsFraktur = new Vector<String>();
        final List<String> fontsDoublestruck = new Vector<String>();

        fontsSanserif.add("Verdana");
        fontsSanserif.add("Helvetica");
        fontsSanserif.add("Arial");
        fontsSanserif.add("Arial Unicode MS");
        fontsSanserif.add("Lucida Sans Unicode");
        fontsSanserif.add("Lucida Sans");
        fontsSanserif.add("Lucida Grande");
        fontsSanserif.add("DejaVu Sans");
        fontsSanserif.add("Bitstream Vera Sans");
        fontsSanserif.add("Luxi Sans");
        fontsSanserif.add("FreeSans");
        fontsSanserif.add("sansserif");
        this.context.put(Parameter.FONTS_SANSSERIF, fontsSanserif);

        fontsSerif.add("Constantina");
        fontsSerif.add("Cambria");
        fontsSerif.add("Times");
        fontsSerif.add("Times New Roman");
        fontsSerif.add("Lucida Bright");
        fontsSerif.add("DejaVu Serif");
        fontsSerif.add("Bitstream Vera Serif");
        fontsSerif.add("Luxi Serif");
        fontsSerif.add("FreeSerif");
        fontsSerif.add("serif");
        this.context.put(Parameter.FONTS_SERIF, fontsSerif);

        fontsMonospaced.add("Andale Mono");
        fontsMonospaced.add("Courier");
        fontsMonospaced.add("Courier Mono");
        fontsMonospaced.add("Courier New");
        fontsMonospaced.add("Lucida Sans Typewriter");
        fontsMonospaced.add("DejaVu Sans Mono");
        fontsMonospaced.add("Bitstream Vera Sans Mono");
        fontsMonospaced.add("Luxi Mono");
        fontsMonospaced.add("FreeMono");
        fontsMonospaced.add("monospaced");
        this.context.put(Parameter.FONTS_MONOSPACED, fontsMonospaced);

        fontsScript.add("EUSM10");
        fontsScript.add("cmsy10");
        fontsScript.add("Math5");
        fontsScript.add("Mathematica5");
        fontsScript.add("Savoye LET");
        fontsScript.add("Brush Script MT");
        fontsScript.add("Zapfino");
        fontsScript.add("Apple Chancery");
        fontsScript.add("Edwardian Script ITC");
        fontsScript.add("Lucida Handwriting");
        fontsScript.add("Monotype Corsiva");
        fontsScript.add("Santa Fe LET");
        this.context.put(Parameter.FONTS_SCRIPT, fontsScript);

        fontsFraktur.add("EUFM10");
        fontsFraktur.add("Mathematica6");
        fontsFraktur.add("FetteFraktur");
        fontsFraktur.add("Fette Fraktur");
        fontsFraktur.add("Euclid Fraktur");
        fontsFraktur.add("Lucida Blackletter");
        fontsFraktur.add("Blackmoor LET");
        this.context.put(Parameter.FONTS_FRAKTUR, fontsFraktur);

        fontsDoublestruck.add("MSBM10");
        fontsDoublestruck.add("Mathematica7");
        fontsDoublestruck.add("Caslon Open Face");
        fontsDoublestruck.add("Caslon Openface");
        fontsDoublestruck.add("Cloister Open Face");
        fontsDoublestruck.add("Academy Engraved LET");
        fontsDoublestruck.add("Colonna MT");
        fontsDoublestruck.add("Imprint MT Shadow");
        this.context.put(Parameter.FONTS_DOUBLESTRUCK, fontsDoublestruck);

        this.context.put(Parameter.MFRAC_KEEP_SCRIPTLEVEL, Boolean.FALSE);
        // CHECKSTYLE:ON
    }

    /**
     * Default Constructor.
     * 
     * @param copyFromContext
     *            LayoutContext to use for initialization of values
     */
    public LayoutContextImpl(final LayoutContext copyFromContext) {

        if (copyFromContext instanceof LayoutContextImpl) {
            this.context = new TreeMap<Parameter, Object>(
                    ((LayoutContextImpl) copyFromContext).getParameters());
        } else {
            throw new UnsupportedOperationException("LayoutContextImpl("
                    + copyFromContext.getClass() + ") not supported.");
        }
    }

    /**
     * Retrieve the default layout context.
     * 
     * @return the default layout context.
     */
    public static LayoutContext getDefaultLayoutContext() {
        return LayoutContextImpl.SingletonHolder.instance;
    }

    /** {@inheritDoc} */
    public LayoutContext setParameter(final Parameter which,
            final Object newValue) {
        if (!which.valid(newValue)) {
            throw new IllegalArgumentException("Illegal value for " + which
                    + ": " + newValue);
        }
        this.context.put(which, newValue);
        return this;
    }

    /** {@inheritDoc} */
    public Object getParameter(final LayoutContext.Parameter which) {
        return this.context.get(which);
    }

    /**
     * Retrieve all parameters set for this LayoutContext. Please note: The
     * returned map should be treated as read-only.
     * 
     * @return all Parameters in this context.
     */
    private Map<LayoutContext.Parameter, Object> getParameters() {
        return this.context;
    }
}
