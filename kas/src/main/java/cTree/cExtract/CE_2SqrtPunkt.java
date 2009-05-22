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

import org.w3c.dom.Element;

import cTree.CElement;
import cTree.CFences;
import cTree.CMessage;
import cTree.CRolle;
import cTree.CSqrt;
import cTree.CTimesRow;
import cTree.CType;
import cTree.adapter.C_Event;
import cTree.cDefence.DefHandler;

public class CE_2SqrtPunkt extends CE_1 {

    @Override
    public CElement doIt() {
        // // selection.get(0) ist das Produkt, Parent die Wurzel
        // System.out.println("Lazy extracting");
        // if (!this.canDo(parent, selection)) {
        // return selection.get(0);
        // }

        // Praefix sichern
        final CRolle rolle = this.getEvent().getParent().getCRolle();
        final CTimesRow newArg = this.createExtraction();
        newArg.correctInternalPraefixesAndRolle();
        final CMessage didIt = new CMessage(false);
        final CElement newChild = CFences.condCreateFenced(newArg, didIt);
        newArg.setCRolle(CRolle.GEKLAMMERT);
        this.insertOrReplace(newChild, true);
        newChild.setCRolle(rolle);
        return DefHandler.getInst().conDefence(newChild.getParent(),
                newChild, newChild.getFirstChild(), didIt.isMessage());
    }

    @Override
    protected CTimesRow createExtraction() {
        System.out.println("Extract sqrt punkt ");
        // Analyse des bisherigen Produkts
        final CTimesRow oldProduct = (CTimesRow) this.getEvent().getFirst();
        final CElement oldFirst = oldProduct.getFirstChild();
        final CElement newFirst = oldFirst.cloneCElement(false);
        final CSqrt firstSqrt = CSqrt.createSqrt(newFirst);
        newFirst.setCRolle(CRolle.RADIKANT);
        final boolean hasDiv = oldFirst.getNextSibling().hasExtDiv();
        final Element newOp = (Element) oldFirst.getNextSibling()
                .getExtPraefix().cloneNode(true);
        final boolean timesRowAgain = (oldFirst.getNextSibling().hasNextC());
        CElement newSecond = null;
        if (timesRowAgain) {
            final ArrayList<CElement> factorList = ((CTimesRow) oldProduct
                    .cloneCElement(false)).getMemberList();
            factorList.remove(0);
            newSecond = CTimesRow.createRow(factorList);
            ((CTimesRow) newSecond).correctInternalPraefixesAndRolle();
            if (hasDiv) {
                ((CTimesRow) newSecond).toggleAllVZButFirst(false);
            }
        } else {
            newSecond = oldFirst.getNextSibling().cloneCElement(false);
        }
        final CSqrt secSqrt = CSqrt.createSqrt(newSecond);
        newSecond.setCRolle(CRolle.RADIKANT);
        secSqrt.setExtPraefix(newOp);
        final CTimesRow newChild = CTimesRow.createRow(CTimesRow.createList(
                firstSqrt, secSqrt));
        newChild.correctInternalPraefixesAndRolle();
        return newChild;
    }

    @Override
    public boolean canDo(final C_Event e) {
        if (e == null) {
            return false;
        }
        if (e instanceof C_Event && !e.equals(this.getEvent())) {
            this.setEvent((CE_Event) e);
        }
        final ArrayList<CElement> selection = this.getEvent().getSelection();
        // Man kann nur die ganz linken Elemente extrahieren
        if (selection.size() != 1
                || !selection.get(0).getCType().equals(CType.TIMESROW)) {
            System.out.println("Wir extrahieren nur aus einem Produkt");
            return false;
        }
        return true;
    }
}
