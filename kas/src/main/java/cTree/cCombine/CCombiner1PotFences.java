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

import java.util.HashMap;

import cTree.CElement;
import cTree.CType;

public class CCombiner1PotFences extends CCombiner1 {
    public CCombiner1PotFences() {
        super();
    }

    @Override
    public HashMap<CType, CC_> getOp2Comb() {
        if (this.op2Combiner == null) {
            this.op2Combiner = super.getOp2Comb();
            this.op2Combiner.put(CType.NUM, new CC_PotFencesNum());
        }
        return this.op2Combiner;
    }

    @Override
    public boolean canCombine(final CElement potenz, final CElement basis,
            final CElement exp) {
        return this.getOp2Comb().get(exp.getCType()).canCombine(potenz,
                basis.getFirstChild(), exp);
    }
}
