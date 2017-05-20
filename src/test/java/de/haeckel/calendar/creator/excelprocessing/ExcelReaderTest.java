package de.haeckel.calendar.creator.excelprocessing;

import org.junit.Test;

/**
 * Created by TimoHäckel on 19.05.2017.
 */
public class ExcelReaderTest {

    @Test
    public void stdRead() throws Exception {
        ExcelReader reader = new ExcelReader();

        reader.importData();

        Object o = reader.readFromSheet();

        System.out.println(reader.toString());

    }
}