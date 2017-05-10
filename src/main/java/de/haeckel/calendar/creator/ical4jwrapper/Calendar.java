package de.haeckel.calendar.creator.ical4jwrapper;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for the fortuna ical4j library.
 * Created by TimoHäckel on 08.05.2017.
 */
public class Calendar {

    private final static String PRODID = "ICalendarCreator";

    /**
     * Timezone specs
     */
    private final static String TIMEZONEDEFAULT = "Europe/Berlin";
    private final TimeZoneRegistry timeZoneRegistry;

    private TimeZone timeZone;

    /**
     * Events cash.
     */
    private Map<String, VEvent> vEvents;

    /**
     * name of the calendar
     */
    private String name;

    /**
     * Calendar in background
     */
    private net.fortuna.ical4j.model.Calendar icsCalendar;

    public Calendar(String name) {
        this(name, TIMEZONEDEFAULT);
    }

    public Calendar(String name, String timezone){
        this.name = name;

        // Create a calendar
        icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//" + PRODID + "//" + name));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        //set timezone
        timeZoneRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();
        timeZone = timeZoneRegistry.getTimeZone(timezone);

        vEvents = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private VEvent getVEvent (String key){
        return vEvents.get(key);
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Set the timezone of the calendar
     * @param timezone
     *      must be formatted as follows:
     *      Area/City (e.g. Europe/Berlin)
     */
    public void setTimezone (String timezone){
        timeZone = timeZoneRegistry.getTimeZone(timezone);
    }

    /**
     * Create an event in the Calendar.
     * @param eventName the name of the event
     * @param startDate     the start time of the event
     * @param endDate       the end time of the event
     * @return unique identifier of the event (can be used to remove it.)
     */
    public String createEvent (String eventName, java.util.Calendar startDate, java.util.Calendar endDate){
        //create event
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        VEvent event = new VEvent(start, end, eventName);

        // append timezone
        event.getProperties().add(timeZone.getVTimeZone().getTimeZoneId());

        // generate unique identifier..
        String uid;
        try {
            UidGenerator ug = new UidGenerator(PRODID);
            Uid uniqueId = ug.generateUid();
            event.getProperties().add(uniqueId);
            //safe to string
            uid = uniqueId.getValue();
        } catch (SocketException e) {
            //need to create a backup id
            uid = PRODID + "BackupID-" + eventName + "@" + start.toString() + "%" + end.toString();
        }

        // add to map
        vEvents.put(uid, event);

        return uid;
    }

    /**
     * Export the calendar to ics file with defaul name in current directory
     */
    public void exportToICS () {
        exportToICS(name + ".ics");
    }

    /**
     * Export the calendar to ics file at the given location (must end on filename.ics!)
     * @param filepath filepath to exportData to
     */
    public void exportToICS (String filepath){
        // Add the events
        addEvents();

        //open file out
        try {
            FileOutputStream fout = new FileOutputStream(filepath);
            CalendarOutputter outputter = new CalendarOutputter();

            outputter.output(icsCalendar, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }


    }

    /**
     * Add all new events to the calendar.
     */
    private void addEvents () {
        if(vEvents != null && vEvents.values() != null){
            for (VEvent vEvent : vEvents.values()){
                if (!icsCalendar.getComponents().contains(vEvent)){
                    icsCalendar.getComponents().add(vEvent);
                }
            }
        }
    }

    @Override
    public String toString() {
        // Add the events
        addEvents();

        return icsCalendar.toString();
    }

}
