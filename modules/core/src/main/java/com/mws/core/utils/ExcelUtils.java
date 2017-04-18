package com.mws.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	/**
	 * log4j 记录器
	 */
	private static final Logger log = Logger.getLogger(ExcelUtils.class);

	private ExcelUtils() {
	}

	/**
	 * 创建单列ExcelUtils处理类的延迟加载
	 * 
	 * @author hfren
	 * 
	 */
	private static class SingletonLoader {
		private static final ExcelUtils INSTANCE = new ExcelUtils();
	}

	public static ExcelUtils getInstance() {
		return SingletonLoader.INSTANCE;
	}

	public Sheet parseExcelToSheet(InputStream is, Object... values) throws InvalidFormatException, IOException {
		Workbook wb = WorkbookFactory.create(is);
		int sheetIndex = values.length > 0 ? Integer.parseInt(values[0].toString()) : 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet;
	}

	public List<Row> parseExcelToRows(InputStream is, Object... values) {
		List<Row> rowsList = new ArrayList<Row>();
		try {
			Sheet sheet = parseExcelToSheet(is);
			int firstRows = sheet.getFirstRowNum();
			int lastRows = sheet.getLastRowNum();
			for (int i = firstRows; i <= lastRows; i++) {
				Row rows = sheet.getRow(i);
				rowsList.add(rows);
			}
		} catch (FileNotFoundException e) {
			log.error("The file doesn't exist.");
			throw new RuntimeException(e);
		} catch (InvalidFormatException e) {
			log.error("The file's format is invalid.");
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error("Happened some mistakes when in parse excel files", e);
			throw new RuntimeException(e);
		}
		return rowsList;
	}

	public Object getCellValueByType(Cell cell) {
		Object value = null;
		if (null == cell)
			return "";
		int typeId = cell.getCellType();
		switch (typeId) {
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_ERROR:
			value = "";
			break;
		default:
			value = "";
			break;

		}
		return value;
	}

	/**
	 * parse excel test
	 */
	public static void main(String[] args) {
		try {
			List<Row> rowsList = ExcelUtils.getInstance().parseExcelToRows(new FileInputStream("D:\\特约商户目录表.xls"));
			if (rowsList.size() > 0) {
				for (Row row : rowsList) {
					Iterator<Cell> it = row.cellIterator();
					while (it.hasNext()) {
						Cell cell = it.next();
						try {
							System.out.println(cell.getStringCellValue());
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
