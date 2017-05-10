package de.haeckel.calendar.creator.textprocessing;

import org.junit.Test;

/**
 * Created by skrec on 09.05.2017.
 */
public class ExcelWriterTest {

    @Test
    public void stdWrite() throws Exception {
        ExcelWriter writer = new ExcelWriter();

        Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
        };

        writer.writeToSheet(datatypes);

        writer.exportData();

    }
}