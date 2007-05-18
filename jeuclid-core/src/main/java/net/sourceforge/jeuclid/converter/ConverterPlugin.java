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

package net.sourceforge.jeuclid.converter;

import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.jeuclid.MathBase;

/**
 * Describes an Image converter.
 * <p>
 * Please note: THIS API IS NOT TO BE CONSIDERED STABLE! IT IS STILL
 * EXPERIMENTAL.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public interface ConverterPlugin {
    /**
     * Write the given MathBase object with its rendering parameters into the
     * given output stream.
     * 
     * @param base
     *            MathBase containing the MathML tree and the rendering
     *            parameters.
     * @param outStream
     *            Target output stream.
     * @throws IOException
     *             if an I/O error occurred during write.
     */
    void convert(MathBase base, OutputStream outStream) throws IOException;

}
