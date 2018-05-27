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

import java.util.Formatter;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Implementation of {@link HtmlHelper} for XSSF files.
 *
 * @author Ken Arnold, Industrious Media LLC
 */
public class XSSFHtmlHelper implements HtmlHelper {
    private final XSSFWorkbook wb;

    private static final Map<Integer,HSSFColor> colors = HSSFColor.getIndexHash();

    public XSSFHtmlHelper(XSSFWorkbook wb) {
        this.wb = wb;
    }

    public void colorStyles(CellStyle style, Formatter out) {
        XSSFCellStyle cs = (XSSFCellStyle) style;
        styleColor(out, "background-color", cs.getFillForegroundXSSFColor());
        styleColor(out, "text-color", cs.getFont().getXSSFColor());
        styleColor(out, "color", cs.getFont().getXSSFColor());
    }

    private void styleColor(Formatter out, String attr, XSSFColor color) {
        if (color == null || color.isAuto())
            return;

        byte[] rgb = color.getRGBWithTint();
        if (rgb == null) {
        	if (rgb == null) {
        	     rgb = color.getRGB();
        	}
        	if (rgb == null) {
        		return;
        	}
        }

        // This is done twice -- rgba is new with CSS 3, and browser that don't
        // support it will ignore the rgba specification and stick with the
        // solid color, which is declared first
        out.format("  %s: #%02x%02x%02x;%n", attr, rgb[0], rgb[1], rgb[2]);
        byte[] argb = color.getARGB();
        if (argb == null) {
            return;
        }
        out.format("  %s: rgba(0x%02x, 0x%02x, 0x%02x, 0x%02x);%n", attr,
                argb[3], argb[0], argb[1], argb[2]);
    }

    public void borderStyles(CellStyle style, Formatter out) {
    	XSSFCellStyle xstyle = (XSSFCellStyle) style;
		styleOut("border-left", style.getBorderLeft(), BORDER, xstyle.getLeftBorderXSSFColor(), out);
		styleOut("border-right", style.getBorderRight(), BORDER, xstyle.getRightBorderXSSFColor(), out);
		styleOut("border-top", style.getBorderTop(), BORDER, xstyle.getTopBorderXSSFColor(), out);
		styleOut("border-bottom", style.getBorderBottom(), BORDER, xstyle.getBottomBorderXSSFColor(), out);
	}

	private <K> void styleOut(String attr, K key, Map<K, String> mapping, Color color, Formatter out) {
		String value = mapping.get(key);
		String c = borderStyle(color);
		value = String.format(value, c);
		if (value != null) {
			out.format("  %s: %s;%n", attr, value);
		}
	}
    
	private String borderStyle(Color color) {
    	XSSFColor xcolor = (XSSFColor)color;
    	if (xcolor != null && !xcolor.isAuto() && xcolor.getARGBHex() != null) {
			 return "#" + xcolor.getARGBHex().substring(2);
		}
    	//TODO return for color indexed
    	if (xcolor != null && xcolor.getIndexed() > 0) {
    		return AUTO_COLOR;
    	}
    	return AUTO_COLOR;
    }
}