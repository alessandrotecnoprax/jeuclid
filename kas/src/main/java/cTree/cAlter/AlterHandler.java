/*
 * Copyright 2009 Erhard Kuenzel
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

package cTree.cAlter;

import java.util.ArrayList;
import java.util.HashMap;

import cTree.CElement;
import cTree.adapter.DOMElementMap;

public class AlterHandler {
    private volatile static AlterHandler uniqueInstance;

    public HashMap<String, CAlter> getAlters;

    private AlterHandler() {
        this.getAlters = new HashMap<String, CAlter>();
        new CA_Minrow().register(this.getAlters);
        new CA_DivA_Frac().register(this.getAlters);
        new CA_DivA_Pot().register(this.getAlters);
        new CA_PrimeDecomposition().register(this.getAlters);
    }

    public static AlterHandler getInstance() {
        if (AlterHandler.uniqueInstance == null) {
            synchronized (DOMElementMap.class) {
                if (AlterHandler.uniqueInstance == null) {
                    AlterHandler.uniqueInstance = new AlterHandler();
                }
            }
        }
        return AlterHandler.uniqueInstance;
    }

    public ArrayList<String> getOptions(final CElement el) {
        final ArrayList<String> options = new ArrayList<String>();
        for (final CAlter ca : this.getAlters.values()) {
            if (ca.check(el)) {
                options.add(ca.getText());
            }
        }
        return options;
    }

    public CElement change(final CElement el, final String actionCommand) {
        System.out.println("Change " + el.getCType());
        if (this.getAlters.containsKey(actionCommand)) {
            return this.getAlters.get(actionCommand).change(el);
        } else {
            return el;
        }
    }

}
