package de.haeckel.calendar.creator.filedependent;

import de.haeckel.calendar.creator.excelprocessing.ExcelInterpreter;
import de.haeckel.calendar.creator.excelprocessing.ExcelReader;
import de.haeckel.calendar.creator.ical4jwrapper.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TimoHäckel on 20.05.2017.
 */
public class MixedTableToCalendar {

    Calendar calendar;

    ExcelReader reader;

    ExcelInterpreter interpreter;

    public MixedTableToCalendar() {

        calendar = new Calendar("Mixed");

        reader = new ExcelReader("mixed.xlsx");

        interpreter = new ExcelInterpreter(reader);

    }

    private static Date dateTime(Date date, Date time) {
        return new Date(
                date.getYear(), date.getMonth(), date.getDate(),
                time.getHours(), time.getMinutes(), time.getSeconds()
        );
    }

    public void execute (){

        System.out.println(reader);

        //get the date from column 2 and 3
        List<Date> dates = interpreter.getColumnAsDate(2, 1, 5);
        List<Date> times = interpreter.getColumnAsDate(3, 1, 5);
        //parse to one date element each
        for (int i = 0; i< dates.size(); i++){
            dates.get(i).setTime(dateTime(dates.get(i), times.get(i)).getTime());
        }

        //Parse the title from column 4
        List<String> titles = new ArrayList<>();
        List<String> enemies = interpreter.getColumnAsString(4,1, 5);
        for (int i = 0; i < enemies.size(); i++) {
            titles.add(("Mixed vs " + enemies.get(i)));
        }

        //get the location from column 5
        List<String> locations = interpreter.getColumnAsString(5, 1, 5);

        //get the info from column 6
        List<String> infos = interpreter.getColumnAsString(6,1, 5);

        for (int i = 0; i < dates.size(); i++) {
            if(dates.size() != titles.size() || titles.size() != locations.size() || titles.size() != infos.size() ){
                System.err.println("FUCK");
            }
            else {
                System.out.println(titles.get(i) + " | " + dates.get(i) + " | " + locations.get(i) +  " | " + infos.get(i));
                calendar.createEvent(titles.get(i), dates.get(i), 3, locations.get(i), infos.get(i));
            }
        }

        calendar.exportToICS("mixedListe.ics");

    }

    public static void main (String ... args){
        new MixedTableToCalendar().execute();
    }




}
