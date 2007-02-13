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

package net.sourceforge.jeuclid.element.helpers;

import java.awt.FontMetrics;

/**
 * Class contains utility methods for working with elements attributes.
 * 
 * @author Unknown
 * @author Max Berger
 */

public final class AttributesHelper {

    /**
     * Value of EM (vertical size).
     */
    public static final double EM = 0.8;

    /**
     * Value of EX (horisontal size).
     */
    public static final double EX = 0.6;

    /**
     * Width of math space.
     */
    public static final String VERYVERYTHINMATHSPACE = "0.0555556em";

    /**
     * Width of math space.
     */
    public static final String VERYTHINMATHSPACE = "0.111111em";

    /**
     * Width of math space.
     */
    public static final String THINMATHSPACE = "0.166667em";

    /**
     * Width of math space.
     */
    public static final String MEDIUMMATHSPACE = "0.222222em";

    /**
     * Width of math space.
     */
    public static final String THICKMATHSPACE = "0.277778em";

    /**
     * Width of math space.
     */
    public static final String VERYTHICKMATHSPACE = "0.333333em";

    /**
     * Width of math space.
     */
    public static final String VERYVERYTHICKMATHSPACE = "0.388889em";

    /**
     * Private constructor: it's forbidden to create instance of this utility
     * class.
     */
    private AttributesHelper() {
    }

    /**
     * Translates size into pixels.
     * 
     * @param value
     *            value of vunit.
     * @param fontmetrix
     *            FontMetrics
     * @return Translated value of the size attribute in pixels.
     */
    public static int getPixels(final String value, final FontMetrics fontmetrix) {
        final double fontheight = fontmetrix.getHeight();
        try {
            final double dpi = fontheight / fontmetrix.getFont().getSize() * 72;
            if (value.equals(OperatorDictionary.NAME_INFINITY)) {
                return 999999;
            } else if (value.equals("thin")) {
                return 1;
            } else if (value.equals("medium")) {
                return 2;
            } else if (value.equals("thick")) {
                return 3;
            } else if (value.endsWith("em")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * fontheight * AttributesHelper.EM);
            } else if (value.endsWith("ex")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * fontheight * AttributesHelper.EX);
            } else if (value.endsWith("px")) {
                return (new Integer(value.substring(0, value.length() - 2))
                        .intValue());
            } else if (value.endsWith("in")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * dpi);
            } else if (value.endsWith("cm")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * dpi / 2.54);
            } else if (value.endsWith("mm")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * dpi / 25.4);
            } else if (value.endsWith("pt")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * dpi / 72);
            } else if (value.endsWith("pc")) {
                return (int) Math.round(AttributesHelper.getNumber(value, 2) * dpi / 6);
            } else if (value.endsWith("%")) {
                return (int) Math
                        .round(AttributesHelper.getNumber(value, 1) / 100d * fontheight);
            } else if (value.equals(OperatorDictionary.NAME_MEDIUMMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.MEDIUMMATHSPACE, fontmetrix);
            } else if (value.equals(OperatorDictionary.NAME_THINMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.THINMATHSPACE, fontmetrix);
            } else if (value.equals(OperatorDictionary.NAME_VERYTHINMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.VERYTHINMATHSPACE, fontmetrix);
            } else if (value
                    .equals(OperatorDictionary.NAME_VERYVERYTHINMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.VERYVERYTHINMATHSPACE, fontmetrix);
            } else if (value.equals(OperatorDictionary.NAME_THICKMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.THICKMATHSPACE, fontmetrix);
            } else if (value.equals(OperatorDictionary.NAME_VERYTHICKMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.VERYTHICKMATHSPACE, fontmetrix);
            } else if (value
                    .equals(OperatorDictionary.NAME_VERYVERYTHICKMATHSPACE)) {
                return AttributesHelper.getPixels(AttributesHelper.VERYVERYTHICKMATHSPACE, fontmetrix);
            }
            return (int) Math.round(new Double(value).doubleValue()
                    * fontheight);
        } catch (final NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Translates size into pt.
     * 
     * @param mathSize
     *            Size of Math
     * @param fontsize
     *            size of the font this size is relative to.
     * @return Translated value of the size attribute in pixels.
     */
    public static float getFontSize(final String mathSize, final float fontsize) {
        try {
            if (mathSize.endsWith("em")) {
                return (AttributesHelper.getNumber(mathSize, 2) * fontsize);
            } else if (mathSize.endsWith("ex")) {
                return (AttributesHelper.getNumber(mathSize, 2) * fontsize);
            } else if (mathSize.endsWith("px")) {
                return (new Float(mathSize.substring(0, mathSize.length() - 2))
                        .floatValue());
            } else if (mathSize.endsWith("in")) {
                return (AttributesHelper.getNumber(mathSize, 2) * 72);
            } else if (mathSize.endsWith("cm")) {
                return (AttributesHelper.getNumber(mathSize, 2) * 72 / 2.54f);
            } else if (mathSize.endsWith("mm")) {
                return (AttributesHelper.getNumber(mathSize, 2) * 72 / 25.4f);
            } else if (mathSize.endsWith("pt")) {
                return (AttributesHelper.getNumber(mathSize, 2));
            } else if (mathSize.endsWith("pc")) {
                return (AttributesHelper.getNumber(mathSize, 2) * 12);
            } else if (mathSize.endsWith("%")) {
                return (AttributesHelper.getNumber(mathSize, 1) * fontsize / 100.0f);
            } else if (mathSize.equals(OperatorDictionary.NAME_MEDIUMMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.MEDIUMMATHSPACE, fontsize);
            } else if (mathSize.equals(OperatorDictionary.NAME_THINMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.THINMATHSPACE, fontsize);
            } else if (mathSize
                    .equals(OperatorDictionary.NAME_VERYTHINMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.VERYTHINMATHSPACE, fontsize);
            } else if (mathSize
                    .equals(OperatorDictionary.NAME_VERYVERYTHINMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.VERYVERYTHINMATHSPACE, fontsize);
            } else if (mathSize.equals(OperatorDictionary.NAME_THICKMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.THICKMATHSPACE, fontsize);
            } else if (mathSize
                    .equals(OperatorDictionary.NAME_VERYTHICKMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.VERYTHICKMATHSPACE, fontsize);
            } else if (mathSize
                    .equals(OperatorDictionary.NAME_VERYVERYTHICKMATHSPACE)) {
                return AttributesHelper.getFontSize(AttributesHelper.VERYVERYTHICKMATHSPACE, fontsize);
            } else if (mathSize.equalsIgnoreCase("small")) {
                return AttributesHelper.getFontSize("68%", fontsize);
            } else if (mathSize.equalsIgnoreCase("normal")) {
                return AttributesHelper.getFontSize("100%", fontsize);
            } else if (mathSize.equalsIgnoreCase("big")) {
                return AttributesHelper.getFontSize("147%", fontsize);
            }
            return (new Float(mathSize).floatValue() * fontsize);
        } catch (final NumberFormatException nfe) {
            return (fontsize);
        }
    }

    /**
     * @param value
     *            string with number at start
     * @param countABC
     *            count of letters at the and string
     * @return Number from string
     */
    private static float getNumber(final String value, final int countABC) {
        return new Float(value.substring(0, value.length() - countABC))
                .floatValue();
    }

    /**
     * Translates size into pixels.
     * 
     * @param value
     *            value of vunit.
     * @param mult
     *            multiplier of size
     * @return Translated value of the size attribute in pixels.
     */

    public static String multipleSize(final String value, final double mult) {
        try {
            if (value.endsWith("em")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "em";
            } else if (value.endsWith("ex")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "ex";
            } else if (value.endsWith("px")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "px";
            } else if (value.endsWith("in")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "in";
            } else if (value.endsWith("cm")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "cm";
            } else if (value.endsWith("mm")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "mm";
            } else if (value.endsWith("pt")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "pt";
            } else if (value.endsWith("pc")) {
                return String.valueOf(AttributesHelper.getNumber(value, 2) * mult) + "pc";
            } else if (value.endsWith("%")) {
                return String.valueOf(AttributesHelper.getNumber(value, 1) * mult) + "%";
            } else if (value.equals(OperatorDictionary.NAME_MEDIUMMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.MEDIUMMATHSPACE, mult);
            } else if (value.equals(OperatorDictionary.NAME_THINMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.THINMATHSPACE, mult);
            } else if (value.equals(OperatorDictionary.NAME_VERYTHINMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.VERYTHINMATHSPACE, mult);
            } else if (value
                    .equals(OperatorDictionary.NAME_VERYVERYTHINMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.VERYVERYTHINMATHSPACE, mult);
            } else if (value.equals(OperatorDictionary.NAME_THICKMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.THICKMATHSPACE, mult);
            } else if (value.equals(OperatorDictionary.NAME_VERYTHICKMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.VERYTHICKMATHSPACE, mult);
            } else if (value
                    .equals(OperatorDictionary.NAME_VERYVERYTHICKMATHSPACE)) {
                return AttributesHelper.multipleSize(AttributesHelper.VERYVERYTHICKMATHSPACE, mult);
            }
            return String.valueOf(new Integer(value).intValue() * mult);
        } catch (final NumberFormatException nfe) {
            return value;
        }
    }
}
