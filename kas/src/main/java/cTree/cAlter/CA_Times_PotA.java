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

import org.w3c.dom.Element;

import cTree.CElement;
import cTree.CPot;
import cTree.CTimesRow;
import cTree.adapter.EElementHelper;

public class CA_Times_PotA extends CAlter {

    private CTimesRow parent;

    private CElement first;

    private int exp;

    @Override
    public CElement change(final ArrayList<CElement> els) {
        System.out.println("Changer Exp to Times");
        final CPot newEl = CPot.createPot(this.first.cloneCElement(false),
                this.exp);
        this.parent.replaceChild(newEl, this.first, true, true);
        for (int i = 1; i < this.exp; i++) {
            this.parent.removeChild(els.get(i), true, true, false);
        }
        return newEl;
    }

    @Override
    public String getText() {
        return "Produkt in Potenz";
    }

    private boolean gleicheDiv(final Element op1, final Element op2) {
        if (op1 == null && EElementHelper.isTimesOp(op2)) {
            return true;
        } else if ((EElementHelper.isTimesOp(op1) && EElementHelper
                .isTimesOp(op2))) {
            return true;
        } else {
            return (":".equals(op1.getTextContent()) && ":".equals(op2
                    .getTextContent()));
        }
    }

    @Override
    public boolean check(final ArrayList<CElement> els) {
        if (els.size() > 1) {
            this.exp = els.size();
            if (els.get(0).hasParent()
                    && els.get(0).getParent() instanceof CTimesRow) {
                this.parent = (CTimesRow) els.get(0).getParent();
                this.first = els.get(0);
                for (int i = 1; i < this.exp; i++) {
                    if (!this.first.getText().equals(els.get(i).getText())
                            || !this.gleicheDiv(this.first.getExtPraefix(),
                                    els.get(i).getExtPraefix())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void register(final HashMap<String, CAlter> hashMap) {
        hashMap.put(this.getText(), this);
    }
}
