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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Detects if FreeHep is in the class path and registers it if its available.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public final class FreeHepDetector {
    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(BatikDetector.class);

    private FreeHepDetector() {
        // Empty on purpose
    }

    /**
     * Detects if FreeHep is in the classpath.
     * 
     * @param registry
     *            ConverterRegisty to register with.
     */
    public static void detectConversionPlugins(
            final ConverterRegistry registry) {
        // TODO: This needs to be autodetected from FreeHEP!
        final String flash = "application/x-shockwave-flash";
        registry.registerMimeTypeAndSuffix(flash, "swf", true);
        try {
            final Class<?> swfos = Thread.currentThread()
                    .getContextClassLoader().loadClass(
                            "org.freehep.graphicsio.swf.SWFGraphics2D");
            registry.registerConverter(flash, new FreeHepConverter(swfos),
                    false);
        } catch (final NoSuchMethodException e) {
            FreeHepDetector.LOGGER.debug(e);
        } catch (final ClassNotFoundException e) {
            FreeHepDetector.LOGGER.debug(e);
        }
    }
}