package de.haeckel.calendar.creator.excelprocessing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Uses ApachePOI for Excel to create a workbook and write it to a file.
 */
public class ExcelWriter {

    /**
     * Defaults.
     */
    private final static String DEF_FILE_NAME = "newfile.xlsx";
    private final static String DEF_SHEET_NAME = "Tabelle";
    private int sheetCount = 0;
    /**
     * The actual excel workbook.
     */
    private XSSFWorkbook workbook;

    public ExcelWriter() {
        workbook = new XSSFWorkbook();
    }

    /**
     * Returns the sheet with the given name if it already exists, else creates a new one.
     * @param sheetName the name of the sheet.
     * @return the sheet itself.
     */
    private XSSFSheet createSheet(String sheetName){
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if(sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    /**
     * Write the dataArray to a new sheet in rows and columns.
     * @param dataArray the data to be written.
     */
    public void writeToSheet(Object [][] dataArray){
        String sheetName = DEF_SHEET_NAME + sheetCount++;
        writeToSheet(sheetName,dataArray);
    }

    /**
     * Write the dataArray to the sheet in rows and columns.
     * @param sheetName name of the sheet.
     * @param dataArray data to be written.
     */
    public void writeToSheet(String sheetName, Object[][] dataArray){
        //get sheet, if it does not exist create it.
        XSSFSheet sheet = createSheet(sheetName);

        //write data to sheet
        int rowNum = 0;
        for (Object[] dataRow : dataArray) {
            //create row
            Row row = sheet.createRow(rowNum++);

            int colNum = 0;
            for (Object dataField : dataRow) {
                Cell cell = row.createCell(colNum++);
                if (dataField instanceof String) {
                    cell.setCellValue((String) dataField);
                } else if (dataField instanceof Integer) {
                    cell.setCellValue((Integer) dataField);
                } else if (dataField instanceof Boolean) {
                    cell.setCellValue((Boolean) dataField);
                } else if (dataField instanceof Calendar) {
                    cell.setCellValue((Calendar) dataField);
                } else if (dataField instanceof Date) {
                    cell.setCellValue((Date) dataField);
                } else if (dataField instanceof Double) {
                    cell.setCellValue((Double) dataField);
                } else if (dataField instanceof Byte) {
                    cell.setCellValue((Byte) dataField);
                } else {
                    cell.setCellValue(dataField.toString());
                }
            }
        }
    }

    /**
     * Export the data to a default filepath.
     */
    public void exportData(){
        exportData(DEF_FILE_NAME);
    }

    /**
     * Export the excel workbook to the filepath.
     * @param filepath
     */
    public void exportData(String filepath) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filepath);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}