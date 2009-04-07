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

import cTree.CType;

public class CDefenceTStrich extends CDefenceTyp {
	public CDefenceTStrich(){
//		super();
		for (CType cType : CType.values()){
			op1Defencer.put(cType, new CD_1StrichDefault());
		}
		this.op1Defencer.put(CType.MINROW, new CD_1StrichMinrow());
		this.op1Defencer.put(CType.PLUSROW, new CD_1StrichStrich());
	}
}
