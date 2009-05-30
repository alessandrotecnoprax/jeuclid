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
import cTree.CFrac;
import cTree.adapter.C_Event;

public class CA_Frac_Kehrbruch extends CAlter {

    @Override
    public CElement doIt() {
        final CFrac old = (CFrac) this.getEvent().getFirst();
        final CElement newNum = old.getNenner().cloneCElement(true);
        final CElement newDen = old.getZaehler().cloneCElement(true);
        final CFrac newFrac = CFrac.createFraction(newNum, newDen);
        newFrac.correctInternalRolles();
        old.toggleTimesDiv(false);
        old.getParent().replaceChild(newFrac, old, true, true);
        return newFrac;
    }

    @Override
    public String getText() {
        return "a/b in b/a umwandeln";
    }

    @Override
    public boolean canDo() {
        final C_Event event = this.getEvent();
        final ArrayList<CElement> els = event.getSelection();
        return (els.get(0).hasExtDiv() || els.get(0).hasExtTimes())
                && (els.get(0) instanceof CFrac);
    }

    @Override
    public void register(final HashMap<String, CAlter> hashMap) {
        hashMap.put(this.getText(), this);
    }
}
