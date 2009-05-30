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

import java.util.HashMap;

import cTree.CElement;
import cTree.CFences;
import cTree.CFrac;
import cTree.CTimesRow;
import cTree.adapter.C_Event;

public class CA_Times_Frac extends CAlter {

    private CTimesRow parent;

    private CElement first;

    private CElement sec;

    @Override
    public CElement doIt() {
        final CFrac newEl = CFrac.createFraction(this.first
                .cloneCElement(false), this.sec.cloneCElement(false));
        final CElement gParent = this.parent.getParent();
        if (gParent instanceof CFences) {
            final CElement ggParent = gParent.getParent();
            ggParent.replaceChild(CFences.createFenced(newEl), gParent, true,
                    true);
        } else {
            gParent.replaceChild(newEl, this.parent, true, true);
        }
        return newEl;
    }

    @Override
    public String getText() {
        return "Quotient in Bruch";
    }

    @Override
    public boolean canDo() {
        final C_Event event = this.getEvent();
        this.first = event.getFirst();
        if (this.first.getParent() instanceof CTimesRow
                && this.first.hasNextC()) {
            this.parent = (CTimesRow) this.first.getParent();
            this.sec = this.first.getNextSibling();
            if (!this.first.hasPrevC() && !this.sec.hasNextC()) {
                return this.sec.hasExtDiv();
            }
        }
        return false;
    }

    @Override
    public void register(final HashMap<String, CAlter> hashMap) {
        hashMap.put(this.getText(), this);
    }
}
