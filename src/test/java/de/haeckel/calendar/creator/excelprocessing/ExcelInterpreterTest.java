package de.haeckel.calendar.creator.excelprocessing;

import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by TimoHäckel on 20.05.2017.
 */
public class ExcelInterpreterTest {
    @Test
    public void stdInterpret() throws Exception {
        ExcelReader reader = new ExcelReader();

        System.out.println(reader.toString());

        ExcelInterpreter interpreter = new ExcelInterpreter(reader);

        List<String> column1 = interpreter.getColumnAsString(0, 1);
        System.out.println(column1);

        List<String> column2 = interpreter.getColumnAsString(1, 1);
        System.out.println(column2);

        List<Double> column3 = interpreter.getColumnAsDouble(2, 1, 4);
        System.out.println(column3);

        double i = column3.get(0) + column3.get(1);
        System.out.println(column3.get(0).toString() + " + " + column3.get(1).toString() + " = " + i);

        List<Date> column4 = interpreter.getColumnAsDate(3, 1);
        System.out.println(column4);

        List<Date> column5 = interpreter.getColumnAsDate(4, 1);
        System.out.println(column5);

    }
}