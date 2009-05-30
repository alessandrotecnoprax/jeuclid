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

package cTree.cCombine;

import cTree.CElement;
import cTree.CMinTerm;
import cTree.CNum;
import cTree.CPlusRow;

public class CC_PunktFencesNum extends CC_Base {

    private CC_PunktFencedMinNum cmn;

    private CC_PunktFencedSumNum csn;

    @Override
    protected CElement createComb(final CElement parent, final CElement cE1,
            final CElement cE2) {
        System.out.println("Multipliziere geklammerte Summe/MinRow mit Num");
        if (cE1.getFirstChild() instanceof CMinTerm && cE2 instanceof CNum) {
            System.out.println("Found MinTerms");
            return this.getCmn().createComb(parent, cE1, cE2);
        } else if (cE1.getFirstChild() instanceof CPlusRow
                && cE2 instanceof CNum) {
            return this.getCsn().createComb(parent, cE1, cE2);
        }
        return cE1;
    }

    @Override
    public boolean canDo() {
        final CElement cE1 = this.getFirst();
        final CElement cE2 = this.getSec();
        System.out.println("Repell fenced sum/min mult num");
        if (cE1.getFirstChild() instanceof CMinTerm && cE2 instanceof CNum) {
            System.out.println("Found MinTerms");
            return this.getCmn().canDo();
        } else if (cE1.getFirstChild() instanceof CPlusRow
                && cE2 instanceof CNum) {
            return this.getCsn().canDo();
        }
        return false;
    }

    protected CC_PunktFencedMinNum getCmn() {
        if (this.cmn == null) {
            this.cmn = new CC_PunktFencedMinNum();
        }
        return this.cmn;
    }

    /**
     * Getter method for css.
     * 
     * @return the css
     */
    protected CC_PunktFencedSumNum getCsn() {
        if (this.csn == null) {
            this.csn = new CC_PunktFencedSumNum();
        }
        return this.csn;
    }

}
