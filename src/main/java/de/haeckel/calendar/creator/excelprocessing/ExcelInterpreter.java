package de.haeckel.calendar.creator.excelprocessing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TimoHäckel on 19.05.2017.
 */
public class ExcelInterpreter  {

    private ExcelReader reader;

    private Object [][] data;

    public ExcelInterpreter(ExcelReader reader) {
        this.reader = reader;
        data = reader.readFromSheet();
    }

    public <T> List<T>getColumnAsType (int columnIndex, int firstRowIndex, int lastRowIndex, Class<T> cls){
        if(!reader.isInitialized()){
            return null;
        }

        List<T> column = new ArrayList<T>();

        for (int rowCount = firstRowIndex; (rowCount <= lastRowIndex) && (rowCount < data.length); rowCount++){


            T cellValue = cls.cast( data[rowCount][columnIndex]);
            column.add(cellValue);

        }

        return column;
    }

    //all data from outside
    public List<Boolean> getColumnAsBoolean (int columnIndex, int firstRowIndex, int lastRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, lastRowIndex, Boolean.class);
    }

    public List<Double> getColumnAsDouble (int columnIndex, int firstRowIndex, int lastRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, lastRowIndex, Double.class);
    }

    public List<String> getColumnAsString (int columnIndex, int firstRowIndex, int lastRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, lastRowIndex, String.class);
    }

    public List<Date> getColumnAsDate (int columnIndex, int firstRowIndex, int lastRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, lastRowIndex, Date.class);
    }

    //dynamic last row index
    public List<Boolean> getColumnAsBoolean (int columnIndex, int firstRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, data.length, Boolean.class);
    }

    public List<Double> getColumnAsDouble (int columnIndex, int firstRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, data.length, Double.class);
    }

    public List<String> getColumnAsString (int columnIndex, int firstRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, data.length, String.class);
    }

    public List<Date> getColumnAsDate (int columnIndex, int firstRowIndex){
        return getColumnAsType(columnIndex, firstRowIndex, data.length, Date.class);
    }

    //dynamic first and last row index
    public List<Boolean> getColumnAsBoolean (int columnIndex){
        return getColumnAsType(columnIndex, 0, data.length, Boolean.class);
    }

    public List<Double> getColumnAsDouble (int columnIndex){
        return getColumnAsType(columnIndex, 0, data.length, Double.class);
    }

    public List<String> getColumnAsString (int columnIndex){
        return getColumnAsType(columnIndex, 0, data.length, String.class);
    }

    public List<Date> getColumnAsDate (int columnIndex){
        return getColumnAsType(columnIndex, 0, data.length, Date.class);
    }


}
