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

import cTree.CElement;
import cTree.CFences;
import cTree.CRolle;
import cTree.CTimesRow;
import cTree.cDefence.CD_Event;

public class CA_Min1_InProdInSum extends CA_Base {

    @Override
    public CElement doIt(final CD_Event message) {
        final CElement old = this.getEvent().getFirst();
        final CTimesRow oldTimesRow = (CTimesRow) old.getParent();
        final CElement newChild = CTimesRow.foldOne((CTimesRow) oldTimesRow
                .cloneCElement(false));
        oldTimesRow.togglePlusMinus(false);
        oldTimesRow.getParent().replaceChild(newChild, oldTimesRow, true,
                true);
        return newChild;
    }

    @Override
    public String getText() {
        return "(-1) aufl�sen und VZ �ndern N";
    }

    @Override
    public boolean canDo() {
        if (this.getEvent() != null && this.getEvent().getFirst() != null) {
            final CElement first = this.getFirst();
            if (first instanceof CFences) {
                final CFences elF = (CFences) first;
                if (elF.isFencedMin1()
                        && elF.getCRolle().equals(CRolle.FAKTOR1)
                        && elF.getNextSibling().hasExtTimes()) {
                    if (first.hasParent()
                            && first.getParent() instanceof CTimesRow) {
                        final CElement elP = first.getParent();
                        if (elP.hasParent()
                                && elP.getCRolle().equals(CRolle.SUMMANDN1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
