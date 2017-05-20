package de.haeckel.calendar.creator.filedependent;

import de.haeckel.calendar.creator.excelprocessing.ExcelInterpreter;
import de.haeckel.calendar.creator.excelprocessing.ExcelReader;
import de.haeckel.calendar.creator.ical4jwrapper.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TimoHäckel on 20.05.2017.
 */
public class TTTableToCalendar {

    /**
     * Default Column index
     */
    private final static int DEF_COL_IDX__DAY = 1;
    private final static int DEF_COL_IDX__DATE = 2;
    private final static int DEF_COL_IDX__TIME = 3;
    private final static int DEF_COL_IDX__ENEMY = 4;
    private final static int DEF_COL_IDX__LOCATION = 5;
    private final static int DEF_COL_IDX__INFO = 6;
    private final static int DEF_DURATION = 3;
    private static final boolean DEF_SKIP_FIRST_ROW = true;
    private final String name;

    Calendar calendar;

    ExcelReader reader;

    ExcelInterpreter interpreter;

    public TTTableToCalendar(String name, String excelFilePath) {

        this.name = name;
        calendar = new Calendar(name);

        reader = new ExcelReader(excelFilePath);

        interpreter = new ExcelInterpreter(reader);

    }

    private static Date dateTime(Date date, Date time) {
        return new Date(
                date.getYear(), date.getMonth(), date.getDate(),
                time.getHours(), time.getMinutes(), time.getSeconds()
        );
    }

    public void execute(int dateIdx, int timeIdx, int enemyIdx, int locationIdx, int infoIdx, int duration, boolean skipFirstRow){

        int firstRowIdx = 0;
        if(skipFirstRow){
            firstRowIdx = 1;
        }

        System.out.println(reader);

        //get the date from column 2 and 3
        List<Date> dates = interpreter.getColumnAsDate(dateIdx, firstRowIdx);
        List<Date> times = interpreter.getColumnAsDate(timeIdx, firstRowIdx);
        //parse to one date element each
        for (int i = 0; i< dates.size(); i++){
            dates.get(i).setTime(dateTime(dates.get(i), times.get(i)).getTime());
        }

        //Parse the title from column 4
        List<String> titles = new ArrayList<>();
        List<String> enemies = interpreter.getColumnAsString(enemyIdx,firstRowIdx);
        for (int i = 0; i < enemies.size(); i++) {
            titles.add((name + " vs " + enemies.get(i)));
        }

        //get the location from column 5
        List<String> locations = interpreter.getColumnAsString(locationIdx, firstRowIdx);

        //get the info from column 6
        List<String> infos = interpreter.getColumnAsString(infoIdx,firstRowIdx);

        for (int i = 0; i < dates.size(); i++) {
            if(dates.size() != titles.size() || titles.size() != locations.size() || titles.size() != infos.size() ){
                System.err.println("FUCK");
            }
            else {
                System.out.println(titles.get(i) + " | " + dates.get(i) + " | " + locations.get(i) +  " | " + infos.get(i));
                calendar.createEvent(titles.get(i), dates.get(i), duration, locations.get(i), infos.get(i));
            }
        }

        calendar.exportToICS("mixedListe.ics");

    }

    public static void main (String ... args){
        new TTTableToCalendar("Mixed", "mixed.xlsx").execute(DEF_COL_IDX__DATE, DEF_COL_IDX__TIME, DEF_COL_IDX__ENEMY, DEF_COL_IDX__LOCATION, DEF_COL_IDX__INFO, DEF_DURATION, DEF_SKIP_FIRST_ROW);
    }




}
