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

package cTree;

import java.util.ArrayList;

import cTree.adapter.ElementAdapter;
import cTree.adapter.PraefixAdapter;
import cTree.adapter.RolleAdapter;
import cTree.cAlter.AlterHandler;
import cTree.cCombine.CombineHandler;
import cTree.cDefence.DefenceHandler;
import cTree.cSplit.SplitHandler;

public abstract class CElement extends RolleAdapter {

    public CType getCType() {
        return CType.UNKNOWN;
    }

    // --- boolesche Tester default Verhalten ----------------------------
    public boolean hatGleichenBetrag(final CElement cE2) {
        return false;
    }

    public boolean is0() {
        return false;
    }

    public boolean istGleichartigesMonom(final CElement e2) {
        return false;
    }

    // --- Support f�r das Verbinden, Extrahieren und Aufspalten in dem CTree
    public CElement combineRight(final CElement active) {
        final CElement erstesElement = active;
        if (erstesElement.hasNextC()) {
            final CElement zweitesElement = erstesElement.getNextSibling();
            final CElement cEl = CombineHandler.getInstance().combine(this,
                    erstesElement, zweitesElement);
            System.out.println("Have combined" + cEl.getParent().getText());
            return cEl;
        } else {
            return active;
        }
    }

    public CElement extract(final ArrayList<CElement> active) {
        return cTree.cExtract.ExtractHandler.getInstance().extract(this,
                active, active.get(0).getFirstChild());
    };

    public CElement change(final String actionCommand) {
        System.out.println(this.getCType());
        return AlterHandler.getInstance().change(this, actionCommand);
    }

    public CElement split(final CElement zuZerlegen, final String s) {
        return SplitHandler.getInstance().split(this, zuZerlegen, s);
    }

    // --- Support f�r die Klammern in dem CTree
    // --- wird von der CPlusRow und der CTimesRow �berschrieben
    public CElement fence(final ArrayList<CElement> active) {
        if (active.size() == 1) {
            return this.standardFencing(active.get(0));
        } else {
            return active.get(0);
        }
    }

    public CElement standardFencing(final CElement active) {
        System.out.println("CElement fencing");
        active.removeCActiveProperty();
        final CElement fences = CFences.createFenced(active
                .cloneCElement(false));
        this.replaceChild(fences, active, true, true);
        fences.setCActiveProperty();
        return fences;
    }

    public final CElement defence(final CElement aFencePair) {
        if (aFencePair instanceof CFences && aFencePair.hasChildC()) {
            return DefenceHandler.getInstance().defence(this, aFencePair,
                    aFencePair.getFirstChild());
        }
        return aFencePair;
    }

    // --- Ausgaben ---------------------------------------
    public void show() {
        ElementAdapter.showElement(this.element);
        if (this.praefix != null) {
            System.out.print("Vorzeichen: ");
            PraefixAdapter.showPraefix(this.praefix);
        } else {
            System.out.println("kein extVZ");
        }
        System.out.println("cType: " + this.getCType());
        System.out.println("cRolle: " + this.cRolle);
        if (this.hasChildC()) {
            this.getFirstChild().showWithSiblings();
        }
    }

    private void showWithSiblings() {
        ElementAdapter.showElement(this.element);
        if (this.praefix != null) {
            System.out.print("Vorzeichen: ");
            PraefixAdapter.showPraefix(this.praefix);
        } else {
            System.out.println("kein intVZ");
        }
        System.out.println("cType: " + this.getCType());
        System.out.println("cRolle: " + this.cRolle);
        if (this.hasChildC()) {
            this.getFirstChild().showWithSiblings();
        }
        if (this.hasNextC()) {
            this.getNextSibling().showWithSiblings();
        }
    }
}
