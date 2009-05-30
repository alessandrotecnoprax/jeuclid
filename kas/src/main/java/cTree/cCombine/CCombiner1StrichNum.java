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

import cTree.CType;

public class CCombiner1StrichNum extends CCombiner1 {
    public CCombiner1StrichNum() {
        super();

    }

    @Override
    public HashMap<CType, CC_Base> getOp2Comb() {
        if (this.op2Combiner == null) {
            this.op2Combiner = super.getOp2Comb();
            this.op2Combiner.put(CType.NUM, new CC_StrichNumNum());
            this.op2Combiner.put(CType.FRAC, new CC_StrichNumFrac());
            this.op2Combiner.put(CType.MIXEDN, new CC_StrichNumMixedNum());
        }
        return this.op2Combiner;
    }

    // @Override
    // public CElement combine(final CElement parent, final CElement cE1,
    // final CElement cE2) {
    // System.out.println("Add Num");
    // return this.op2Combiner.get(cE2.getCType()).combine(parent, cE1, cE2);
    // }
    //
    // @Override
    // public boolean canCombine(final CElement parent, final CElement cE1,
    // final CElement cE2) {
    // return this.op2Combiner.get(cE2.getCType()).canCombine(parent, cE1,
    // cE2);
    // }
}
