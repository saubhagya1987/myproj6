/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package vn.com.unit.fe_credit.utils.excel;

import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOTTED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOUBLE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_HAIR;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_NONE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_SLANTED_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN;

import java.util.Formatter;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

import vn.com.unit.fe_credit.utils.ExcelUtils;

/**
 * This interface is used where code wants to be independent of the workbook
 * formats.  If you are writing such code, you can add a method to this
 * interface, and then implement it for both HSSF and XSSF workbooks, letting
 * the driving code stay independent of format.
 *
 * @author Ken Arnold, Industrious Media LLC
 */
public interface HtmlHelper {
	public static final String AUTO_COLOR = "#000";
	public static final Map<Short, String> BORDER = ExcelUtils.mapFor(
			BORDER_DASH_DOT, "%s dashed 1pt", BORDER_DASH_DOT_DOT, "%s dashed 1pt", 
			BORDER_DASHED, "%s dashed 1pt", BORDER_DOTTED, "%s dotted 1pt", 
			BORDER_DOUBLE, "%s double 3pt", BORDER_HAIR, "%s solid 1px", 
			BORDER_MEDIUM, "%s solid 2pt", BORDER_MEDIUM_DASH_DOT, "%s dashed 2pt", 
			BORDER_MEDIUM_DASH_DOT_DOT, "%s dashed 2pt", BORDER_MEDIUM_DASHED, "%s dashed 2pt", 
			BORDER_NONE, "none", BORDER_SLANTED_DASH_DOT, "%s dashed 2pt", 
			BORDER_THICK, "%s solid 3pt", BORDER_THIN, "%s solid 1pt");
	
    /**
     * Outputs the appropriate CSS style for the given cell style.
     *
     * @param style The cell style.
     * @param out   The place to write the output.
     */
    void colorStyles(CellStyle style, Formatter out);
    
    void borderStyles(CellStyle style, Formatter out);
    
}
