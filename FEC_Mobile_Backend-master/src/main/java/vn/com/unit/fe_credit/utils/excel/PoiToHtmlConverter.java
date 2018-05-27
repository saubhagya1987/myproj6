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

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER_SELECTION;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_FILL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_GENERAL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_JUSTIFY;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_LEFT;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_RIGHT;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_BOTTOM;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_TOP;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.utils.ExcelUtils;

/**
 * This example shows how to display a spreadsheet in HTML using the classes for
 * spreadsheet display.
 *
 * @author Ken Arnold, Industrious Media LLC
 */
public class PoiToHtmlConverter {

	/**
	 * Run this class as a program
	 *
	 * @param args
	 *            The command line arguments.
	 *
	 * @throws Exception
	 *             Exception we don't recover from.
	 */
	public static void main(String[] args) throws Exception {
		//String a1 = "D:\\data.xlsx";
		//String a1 = "D:\\data1.xlsx";
		String a1 = "D:\\data2.xlsx";
		String a2 = "D:\\data-out.html";
		args = new String[] { a1, a2 };

		if (args.length < 2) {
			System.err.println("usage: ToHtml inputWorkbook outputHtmlFile");
			return;
		}

		PoiToHtmlConverter toHtml = create(args[0], new PrintWriter(new FileWriter(args[1])));
		toHtml.setCompleteHTML(true);
		toHtml.printPage();
		System.out.println("OK DONE");
	}
	
	private final Workbook wb;
	private final Appendable output;
	private boolean completeHTML;
	private Formatter out;
	private boolean gotBounds;
	private int firstColumn;
	private int endColumn;
	private HtmlHelper helper;

	private static final String DEFAULTS_CLASS = "excelDefaults";
	private static final String COL_HEAD_CLASS = "colHeader";
	private static final String ROW_HEAD_CLASS = "rowHeader";

	private static final Map<Short, String> ALIGN = ExcelUtils.mapFor(ALIGN_LEFT, "left",
			ALIGN_CENTER, "center", ALIGN_RIGHT, "right", ALIGN_FILL, "left",
			ALIGN_JUSTIFY, "left", ALIGN_CENTER_SELECTION, "center");

	private static final Map<Short, String> VERTICAL_ALIGN = ExcelUtils.mapFor(
			VERTICAL_BOTTOM, "bottom", VERTICAL_CENTER, "middle", VERTICAL_TOP,
			"top");

	/**
	 * Creates a new converter to HTML for the given workbook.
	 *
	 * @param wb
	 *            The workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static PoiToHtmlConverter create(Workbook wb, Appendable output) {
		return new PoiToHtmlConverter(wb, output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. If the path ends
	 * with "<tt>.xlsx</tt>" an {@link XSSFWorkbook} will be used; otherwise
	 * this will use an {@link HSSFWorkbook}.
	 *
	 * @param path
	 *            The file that has the workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static PoiToHtmlConverter create(String path, Appendable output)
			throws IOException {
		return create(new FileInputStream(path), output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. This attempts to
	 * detect whether the input is XML (so it should create an
	 * {@link XSSFWorkbook} or not (so it should create an {@link HSSFWorkbook}
	 * ).
	 *
	 * @param in
	 *            The input stream that has the workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 *
	 * @return An object for converting the workbook to HTML.
	 */
	public static PoiToHtmlConverter create(InputStream in, Appendable output)
			throws IOException {
		try {
			Workbook wb = WorkbookFactory.create(in);
			return create(wb, output);
		} catch (InvalidFormatException e) {
			throw new IllegalArgumentException("Cannot create workbook from stream", e);
		}
	}

	private PoiToHtmlConverter(Workbook wb, Appendable output) {
		if (wb == null)
			throw new NullPointerException("wb");
		if (output == null)
			throw new NullPointerException("output");
		this.wb = wb;
		this.output = output;
		setupColorMap();
	}

	private void setupColorMap() {
		if (wb instanceof HSSFWorkbook)
			helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
		else if (wb instanceof XSSFWorkbook)
			helper = new XSSFHtmlHelper((XSSFWorkbook) wb);
		else
			throw new IllegalArgumentException(
					"unknown workbook type: " + wb.getClass().getSimpleName());
	}

	public void setCompleteHTML(boolean completeHTML) {
		this.completeHTML = completeHTML;
	}

	public void printPage() throws IOException {
		try {
			ensureOut();
			if (completeHTML) {
				out.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n");
				out.format("<html>%n");
				out.format("<head>%n");
				out.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">%n");
				out.format("</head>%n");
				out.format("<body>%n");
			}

			print();

			if (completeHTML) {
				out.format("</body>%n");
				out.format("</html>%n");
			}
		} finally {
			if (out != null)
				out.close();
			if (output instanceof Closeable) {
				Closeable closeable = (Closeable) output;
				closeable.close();
			}
		}
	}

	public void print() {
		printInlineStyle();
		printSheets();
	}

	private void printInlineStyle() {
		// out.format("<link href=\"excelStyle.css\" rel=\"stylesheet\" type=\"text/css\">%n");
		out.format("<style type=\"text/css\">%n");
		printStyles();
		out.format("</style>%n");
	}

	private void ensureOut() {
		if (out == null)
			out = new Formatter(output);
	}

	public void printStyles() {
		ensureOut();

		// First, copy the base css
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream("excelStyle.css")));
			String line;
			while ((line = in.readLine()) != null) {
				out.format("%s%n", line);
			}
		} catch (IOException e) {
			throw new IllegalStateException("Reading standard css", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Reading standard css", e);
				}
			}
		}

		// now add css for each used style
		Set<Short> seen = new HashSet<Short>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				for (Cell cell : row) {
					CellStyle style = cell.getCellStyle();
					
					//System.out.println("#" + cell.getRowIndex() + "@" + cell.getColumnIndex() + "@" + style.getIndex());
					if (!seen.contains(style.getIndex())) {
						printStyle(style);
						seen.add(style.getIndex());
					}
				}
			}
			//System.out.println("#------------");
		}
	}

	private void printStyle(CellStyle style) {
		out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(style));
		styleContents(style);
		out.format("}%n");
	}

	private void styleContents(CellStyle style) {
		styleOut("text-align", style.getAlignment(), ALIGN);
		styleOut("vertical-align", style.getVerticalAlignment(), VERTICAL_ALIGN);
		fontStyle(style);
		helper.borderStyles(style, out);
		helper.colorStyles(style, out);
	}

	private void fontStyle(CellStyle style) {
		Font font = wb.getFontAt(style.getFontIndex());

		if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_BOLD)
			out.format("  font-weight: bold;%n");
		if (font.getItalic())
			out.format("  font-style: italic;%n");

		int fontheight = font.getFontHeightInPoints();
		if (fontheight == 9) {
			// fix for stupid ol Windows
			fontheight = 10;
		}
		out.format("  font-size: %dpt;%n", fontheight);
		
		if (!StringUtils.isEmpty(font.getFontName())) {
			out.format("  font-family: %s;%n", font.getFontName());
		}
	}

	private String styleName(CellStyle style) {
		if (style == null)
			style = wb.getCellStyleAt((short) 0);
		//System.out.println("###" + style.getIndex());
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		try {
			fmt.format("style_%02d", style.getIndex());
			//System.out.println("###" + fmt.toString());
			return fmt.toString();
		} finally {
			fmt.close();
		}
	}

	private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
		String value = mapping.get(key);
		if (value != null) {
			out.format("  %s: %s;%n", attr, value);
		}
	}

	private static int ultimateCellType(Cell c) {
		int type = c.getCellType();
		if (type == Cell.CELL_TYPE_FORMULA)
			type = c.getCachedFormulaResultType();
		return type;
	}

	private void printSheets() {
		ensureOut();
		Sheet sheet = wb.getSheetAt(0);
		printSheet(sheet);
	}

	public void printSheet(Sheet sheet) {
		ensureOut();
		out.format("<table class=%s>%n", DEFAULTS_CLASS);
		printCols(sheet);
		printSheetContent(sheet);
		out.format("</table>%n");
	}

	private void printCols(Sheet sheet) {
		out.format("<col/>%n");
		ensureColumnBounds(sheet);
		for (int i = firstColumn; i < endColumn; i++) {
			out.format("<col/>%n");
		}
	}

	private void ensureColumnBounds(Sheet sheet) {
		if (gotBounds)
			return;

		Iterator<Row> iter = sheet.rowIterator();
		firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
		endColumn = 0;
		while (iter.hasNext()) {
			Row row = iter.next();
			short firstCell = row.getFirstCellNum();
			if (firstCell >= 0) {
				firstColumn = Math.min(firstColumn, firstCell);
				endColumn = Math.max(endColumn, row.getLastCellNum());
			}
		}
		gotBounds = true;
	}

	private void printColumnHeads() {
		out.format("<thead>%n");
		out.format("  <tr class=%s>%n", COL_HEAD_CLASS);
		out.format("    <th class=%s>&#x25CA;</th>%n", COL_HEAD_CLASS);
		// noinspection UnusedDeclaration
		StringBuilder colName = new StringBuilder();
		for (int i = firstColumn; i < endColumn; i++) {
			colName.setLength(0);
			int cnum = i;
			do {
				colName.insert(0, (char) ('A' + cnum % 26));
				cnum /= 26;
			} while (cnum > 0);
			out.format("    <th class=%s>%s</th>%n", COL_HEAD_CLASS, colName);
		}
		out.format("  </tr>%n");
		out.format("</thead>%n");
	}

	public static CellRangeAddress getMergedRegion(Sheet sheet, int rowNum, int cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	private void printSheetContent(Sheet sheet) {
		//Print column header
		//printColumnHeads();
		
		int splitTopRow = -1;
		boolean isSplit = false;
		PaneInformation pi = sheet.getPaneInformation();
		if (pi != null && pi.getActivePane() > 1 && pi.isFreezePane()) {
			splitTopRow = pi.getHorizontalSplitTopRow();
		}

		if (splitTopRow < 0) {
			out.format("<tbody>%n");
		} else {
			out.format("<thead>%n");
			isSplit = true;
		}
		
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			int rowNum = row.getRowNum();

			out.format("  <tr>%n");
			
			//Print rows header
			//out.format("    <td class=%s>%d</td>%n", ROW_HEAD_CLASS, rowNum + 1);

			for (int i = firstColumn; i < endColumn; i++) {
				// check cell merge
				CellRangeAddress mergeRegion = getMergedRegion(sheet, rowNum, i);
				String spanCol = "";
				String spanRow = "";
				if (mergeRegion != null) {
					int mergeCol = mergeRegion.getLastColumn() - mergeRegion.getFirstColumn();
					int mergeRow = mergeRegion.getLastRow() - mergeRegion.getFirstRow();
					if (mergeCol > 0) {
						int colSpan = mergeCol + 1;
						spanCol = " colspan=\"" + colSpan + "\"";
					}
					if (mergeRow > 0 && rowNum == mergeRegion.getFirstRow()) {
						int rowSpan = mergeRow + 1;
						spanRow = " rowspan=\"" + rowSpan + "\"";
					} else if (mergeRow > 0) {
						continue;
					}
				}

				String content = "&nbsp;";
				String attrs = "";
				CellStyle style = null;
				if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
					Cell cell = row.getCell(i);
					if (cell != null) {
						style = cell.getCellStyle();
						attrs = tagStyle(cell, style);
						// Set the value that is rendered for the cell
						// also applies the format
						CellFormat cf = CellFormat.getInstance(style.getDataFormatString());
						CellFormatResult result = cf.apply(cell);
						
						if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
							FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
							CellValue cellValue = evaluator.evaluate(cell);
							DecimalFormat df2 = new DecimalFormat(SystemConfig.NUMBER_PATTERN);
							
							content = String.valueOf( df2.format(cellValue.getNumberValue()) );
						}else
							content = result.text;
						
						if(!StringUtils.isEmpty(content)) {
							content = StringUtils.stripEnd(content, null);
						}
						
						if (content.equals(""))
							content = "&nbsp;";
						if (!StringUtils.isEmpty(content) && content.trim().equals("- 0"))
							content = "-";
					}
				}
				
				if (style!=null && style.getRotation() == 90) {
					out.format("    <td class=\"%s rotate\" %s %s><div><span>%s</span></div></td>%n", styleName(style),
							attrs, spanCol + spanRow, content);
				} else
					out.format("    <td class=%s %s %s>%s</td>%n", styleName(style),
						attrs, spanCol + spanRow, content);

				if (mergeRegion != null) {
					int col = mergeRegion.getLastColumn() - mergeRegion.getFirstColumn();
					i += col;
				}
				
				//For test
				if (style!=null) {
					
				}
			}
			out.format("  </tr>%n");
			
			if (isSplit && rowNum == splitTopRow - 1) {
				out.format("</thead>%n");
				out.format("<tbody>%n");
			}
			
		}
		out.format("</tbody>%n");
	}

	private String tagStyle(Cell cell, CellStyle style) {
		if (style.getAlignment() == ALIGN_GENERAL) {
			switch (ultimateCellType(cell)) {
				case HSSFCell.CELL_TYPE_STRING:
					return "style=\"text-align: left;\"";
				case HSSFCell.CELL_TYPE_BOOLEAN:
				case HSSFCell.CELL_TYPE_ERROR:
					return "style=\"text-align: center;\"";
				case HSSFCell.CELL_TYPE_NUMERIC:
				default:
					// "right" is the default
					break;
			}
		}
		return "";
	}
}