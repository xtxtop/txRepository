package cn.com.shopec.common.utils;

import java.io.File;
import java.util.List;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JExcelUtil {

	public static String exportExcel(List<String[]> list, String filename) throws Exception {

		WritableFont fontTitle = new WritableFont(WritableFont.TIMES, 9, WritableFont.NO_BOLD);

		WritableCellFormat formatTitle = new WritableCellFormat(fontTitle);
		formatTitle.setWrap(true);
		formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
		formatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		File file = new File(filename);

		Label label = null;
		WritableWorkbook book = Workbook.createWorkbook(file);
		WritableSheet sheet = book.createSheet("第一页 ", 0);
		sheet.setColumnView(0, 15);
		sheet.setColumnView(0, 10);
		sheet.setRowView(0, 500);
		SheetSettings sset1 = sheet.getSettings();
		sset1.setAutomaticFormulaCalculation(true);
		int i = 0;
		for (String[] contents : list) {
			int j = 0;
			for (String content : contents) {
				label = new Label(j, i, content, formatTitle);
				sheet.addCell(label);
				j++;
			}
			i++;
		}
		// 写入数据并关闭文件
		book.write();
		book.close();

		return filename;
	}
	public static String exportExcelForDevice(List<String[]> list, String filename,String memo) throws Exception {

		WritableFont fontTitle = new WritableFont(WritableFont.TIMES, 9, WritableFont.NO_BOLD);

		WritableCellFormat formatTitle = new WritableCellFormat(fontTitle);
		formatTitle.setWrap(true);
		formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
		formatTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		File file = new File(filename);

		Label label = null;
		WritableWorkbook book = Workbook.createWorkbook(file);
		WritableSheet sheet1 = book.createSheet("说明 ", 0);
		WritableSheet sheet = book.createSheet("第一页 ", 0);
		sheet1.setColumnView(0, 400);
		sheet.setColumnView(0, 20);
		sheet.setRowView(0, 500);
		SheetSettings sset1 = sheet.getSettings();
		sset1.setAutomaticFormulaCalculation(true);
		int i = 0;
		for (String[] contents : list) {
			int j = 0;
			for (String content : contents) {
				label = new Label(j, i, content, formatTitle);
				if(j==4){
				    jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());
				    sheet.addCell(labelDT); 
				}
				sheet.addCell(label);
				j++;
			}
			i++;
		}
		label = new Label(0, 0, memo, formatTitle);
		sheet1.addCell(label);
		// 写入数据并关闭文件
		book.write();
		book.close();

		return filename;
	}
}
