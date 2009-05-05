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
import cTree.CMixedNumber;
import cTree.CNum;
import cTree.CPlusRow;

public class CA_PlusRow_InGemZ extends CAlter {

    private CPlusRow cP;

    private CElement w;

    private CElement f;

    @Override
    public CElement change(final ArrayList<CElement> els) {
        final CMixedNumber cEl = CMixedNumber.createMixedNumber(this.w,
                this.f);
        this.cP.getParent().replaceChild(cEl, this.cP, true, true);
        return cEl;
    }

    @Override
    public String getText() {
        return "Summe -> gemZ";
    }

    @Override
    public boolean check(final ArrayList<CElement> els) {
        System.out.println("Check Summe -> gemZ");
        if (els.size() > 0 && els.get(0) instanceof CPlusRow) {
            this.cP = (CPlusRow) els.get(0);
            this.w = this.cP.getFirstChild();
            if (this.w instanceof CNum && this.w.hasNextC()) {
                this.f = this.w.getNextSibling();
                if (this.f instanceof CFrac && !this.f.hasNextC()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void register(final HashMap<String, CAlter> hashMap) {
        hashMap.put(this.getText(), this);
    }
}