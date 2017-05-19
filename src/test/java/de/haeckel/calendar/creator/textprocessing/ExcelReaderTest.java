package de.haeckel.calendar.creator.textprocessing;

import org.junit.Test;

import static org.junit.Assert.*;

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