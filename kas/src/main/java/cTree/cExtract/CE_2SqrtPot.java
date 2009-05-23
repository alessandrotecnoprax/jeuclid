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

package cTree.cExtract;

import java.util.ArrayList;

import cTree.CElement;
import cTree.CFences;
import cTree.CMessage;
import cTree.CNum;
import cTree.CPot;
import cTree.CRolle;
import cTree.adapter.C_Event;
import cTree.cDefence.DefHandler;

public class CE_2SqrtPot extends CE_1 {

    @Override
    public CElement doIt() {

        System.out.println("SqrtPot - Can extract");
        // Praefix sichern
        final CElement parent = this.getEvent().getParent();
        final CRolle rolle = parent.getCRolle();
        final CElement newArg = this.createExtraction();
        final CMessage didIt = new CMessage(false);
        final CElement newChild = CFences.condCreateFenced(newArg, didIt);
        newArg.setCRolle(CRolle.GEKLAMMERT);
        this.insertOrReplace(newChild, true);
        newChild.setCRolle(rolle);
        return DefHandler.getInst().conDefence(newChild.getParent(),
                newChild, newChild.getFirstChild(), didIt.isMessage());
    }

    @Override
    protected CElement createExtraction() {
        final CPot oldPot = (CPot) this.getEvent().getFirst();
        final int oldExp = ((CNum) oldPot.getExponent()).getValue();
        CElement newChild = null;
        if (oldExp == 2) {
            newChild = oldPot.getBasis().cloneCElement(false);
        } else {
            newChild = oldPot.cloneCElement(false);
            ((CNum) ((CPot) newChild).getExponent()).setText(""
                    + (oldExp / 2));
        }
        return newChild;
    }

    @Override
    public boolean canDo(final C_Event e) {
        if (e == null || !(e instanceof CE_Event)) {
            return false;
        }
        this.setEvent(e);
        final ArrayList<CElement> selection = this.getEvent().getSelection();
        // Man kann nur die ganz linken Elemente extrahieren
        if (selection.size() != 1) {
            System.out.println("Wir extrahieren nur bei einer Potenz");
            return false;
        } else {
            final int exp = ((CNum) ((CPot) selection.get(0)).getExponent())
                    .getValue();
            if (exp == 0 || exp % 2 != 0) {
                System.out.println("Wir extrahieren nur gerade Potenzen");
                return false;
            }
        }
        return true;
    }
}
