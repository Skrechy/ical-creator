package de.haeckel.calendar.creator.textprocessing;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by TimoHäckel on 10.05.2017.
 */
public class ExcelReader {

    private static final String DEF_FILE_NAME = "newfile.xlsx";
    private XSSFWorkbook workbook;

    /**
     * Import the data from a default filepath.
     */
    public void importData (){
        importData(DEF_FILE_NAME);
    }

    /**
     * Import the excel workbook from the filepath.
     * @param filepath
     */
    public void importData(String filepath) {
        try {
            FileInputStream excelFile = new FileInputStream(new File(filepath));
            workbook = new XSSFWorkbook(excelFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNumberOfRowsInSheet() {
        return getNumberOfRowsInSheet(0);
    }

    private int getNumberOfRowsInSheet(String sheetName) {
        return getNumberOfRowsInSheet(workbook.getSheetIndex(sheetName));
    }

    private int getNumberOfRowsInSheet(int sheetIndex){
        if(!workbookHasSheets()){
            return 0;
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
    }

    private int getNumberOfColumnsInSheet() {
        return getNumberOfRowsInSheet(0);
    }

    private int getNumberOfColumnsInSheet(String sheetName) {
        return getNumberOfRowsInSheet(workbook.getSheetIndex(sheetName));
    }

    private int getNumberOfColumnsInSheet(int sheetIndex){
        if(!workbookHasSheets()){
            return 0;
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        int begin =  sheet.getRow(sheet.getFirstRowNum()).getFirstCellNum();
        int end = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
        return end - begin;
    }

    private boolean workbookHasSheets () {
        if(workbook != null || workbook.getNumberOfSheets() != 0){
            return true;
        }
        return false;
    }

    public Object [][] readFromSheet () {
        return readFromSheet(0);
    }

    private Object[][] readFromSheet(int i) {
        if(!workbookHasSheets()){
            return null;
        }

        Sheet sheet = workbook.getSheetAt(i);
        //Prepare data return array

        Object [][] data = new Object [getNumberOfRowsInSheet(i)][getNumberOfColumnsInSheet(i)];


        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {

            Row currentRow = iterator.next();
            int currentRowIndex = currentRow.getRowNum() - sheet.getFirstRowNum();
            Iterator<Cell> cellIterator = currentRow.iterator();
            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next();
                int currentCellIndex = currentCell.getColumnIndex() - currentRow.getFirstCellNum();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0

                //export cell
                Object ret = null;
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    ret = currentCell.getStringCellValue();
                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    ret = currentCell.getNumericCellValue();
                    if(HSSFDateUtil.isCellDateFormatted(currentCell)){
                        ret = currentCell.getDateCellValue();
                    }
                } else if (currentCell.getCellTypeEnum() == CellType.BOOLEAN) {
                    ret = currentCell.getBooleanCellValue();
                } else if (currentCell.getCellTypeEnum() == CellType.FORMULA) {
                    ret = currentCell.getNumericCellValue();
                }
                data [currentRowIndex][currentCellIndex] = ret;

            }
        }

        return data;
    }

    public boolean isInitialized(){
        return workbookHasSheets();
    }

    @Override
    public String toString() {
        if(!workbookHasSheets()) {
            return "ExcelReader{}";
        }

        Object [][] obj = readFromSheet();
        String ret = "ExcelReader{";

        for (int i = 0; i < obj.length; i++){
            ret += "\n";
            for (int j = 0; j < obj[i].length; j++){
                if(j != 0){
                    ret += " | ";
                }
                ret += obj[i][j].toString();
            }
        }

        ret += "\n}";

        return ret;
    }
}