package swd2014.projekt1.xls;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import swd2014.projekt1.models.Matrix;


public class UniParser {
	/**
	 * Parsuje plik i zwraca wynik operacji jako listę list zawierających obiekty 
	 * których typ można sprawdzić uzywając getClass.
	 * Pierwsza lista zawiera nagłówki kolumn
	 * @param file
	 * @return
	 */
	public XlsParseContainer parse(String file)
	{
		InputStream in = null;
		Workbook wb = null;
		
		wb = loadWorkbook(file, in);
		
		Sheet sheet=wb.getSheetAt(0);
		
		XlsParseContainer result = new XlsParseContainer();
		
		result.setColumnNames(readHeaders(sheet));
		
		result.setValues(readValues(sheet));
		
		return result;
	}

	private Matrix readValues(Sheet sheet) {
		
		Row row=sheet.getRow(sheet.getFirstRowNum());
		
		Matrix result = new Matrix( sheet.getLastRowNum() - sheet.getFirstRowNum() -1, row.getLastCellNum() - row.getFirstCellNum());
		
		if((sheet.getFirstRowNum()+1)<sheet.getLastRowNum())
		{
			for(int i=sheet.getFirstRowNum()+1; i<sheet.getLastRowNum(); i++)
			{
				row = sheet.getRow(i);
				String[] parsedRow = new String[row.getLastCellNum() - row.getFirstCellNum()];
				
				for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++)
				{
					Cell cell = row.getCell(j);				
					if(cell.getCellType() == Cell.CELL_TYPE_STRING)
					{
						parsedRow[j]=cell.getStringCellValue();
					}
					else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
					{
						parsedRow[j]=Double.toString((cell.getNumericCellValue()));
					}
				}
				result.replaceRow(parsedRow, i-1);
			}
		}
		return result;
	}

	private List<String> readHeaders(Sheet sheet) {
		Row row=sheet.getRow(sheet.getFirstRowNum());
		List<String> headers=new ArrayList<String>(row.getLastCellNum() - row.getFirstCellNum() + 1);
		for(int i=row.getFirstCellNum(); i<row.getLastCellNum(); i++)
		{
			headers.add(row.getCell(i).getStringCellValue());
		}
		return headers;
	}

	private Workbook loadWorkbook(String file, InputStream in) {
		Workbook wb = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
}
