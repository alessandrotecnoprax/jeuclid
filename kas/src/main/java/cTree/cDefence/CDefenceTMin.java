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

package cTree.cDefence;

import java.util.HashMap;

import cTree.CType;

public class CDefenceTMin extends CDefenceTyp {
    public CDefenceTMin() {
    }

    @Override
    protected HashMap<CType, CD_Base> getOp1Def() {
        if (this.op1Defencer == null) {
            super.getOp1Def();
            this.op1Defencer.put(CType.PLUSTERM, new CD_1StrichPlusterm());
            this.op1Defencer.put(CType.MINROW, new CD_1MinrowMinrow());
            this.op1Defencer.put(CType.PLUSROW, new CD_1MinrowStrich());
        }
        return this.op1Defencer;
    }
}
