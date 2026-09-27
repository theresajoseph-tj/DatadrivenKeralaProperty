package Utility;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private static Workbook workbook;
	 private static Sheet sheet;
	 private static String path = "src/test/resources/PropertyRegistration.xlsx";
	 public static void openExcel(String sheetName) throws Exception {
	    	
	        FileInputStream fis = new FileInputStream(path);
	       
	        workbook = new XSSFWorkbook(fis);  
	        sheet = workbook.getSheet(sheetName);
	    }
	    
	    public static int getRowCount() {
	    	
	        return sheet.getLastRowNum();
	    }
	   
	
	    public static String getCellData(int rowNum, int colNum) {
	        
	        if (sheet == null) {
	            return "";
	        }
	        
	        Row row = sheet.getRow(rowNum);
	       
	        if (row == null) {
	            return "";
	        }
	        
	        Cell cell = row.getCell(colNum);
	        
	        if (cell == null) {
	            return "";
	        }
	        
	        DataFormatter formatter = new DataFormatter();
	      
	        return formatter.formatCellValue(cell);
	    }
	   
	 
	    public static void setCellData(int row, int col, String value) {
	        Row r = sheet.getRow(row);
	        if (r.getCell(col) == null) {
	            r.createCell(col);
	        }
	        r.getCell(col).setCellValue(value);
	    }
	    
	    public static void saveAndClose() throws Exception {
	    	
	        FileOutputStream fos = new FileOutputStream(path);
	         workbook.write(fos);
	        workbook.close();
	        fos.close();
	    }
}





