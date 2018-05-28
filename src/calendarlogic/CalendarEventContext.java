package calendarlogic;

import java.util.TreeSet;

import calendardata.CalendarEvent;
import java.time.LocalDateTime;

//jeszcze nie wiem jak to wpiąć w pola kalendarza i jak to ogólnie będzie działać
public class CalendarEventContext 
{
	TreeSet<CalendarEvent> calendarEvents;
	
	boolean addEvent(CalendarEvent newEvent)
	{
		if(calendarEvents.lower(newEvent) == null)
		{
			return calendarEvents.add(newEvent);
		}
		else if (calendarEvents.floor(newEvent).compareTo(newEvent) == 0)
		{
			return false;
		}
		else if (calendarEvents.floor(newEvent).getEndOfEvent().isBefore(newEvent.getStartOfEvent()))
		{
			return calendarEvents.add(newEvent);
		}
		else
		{
			return false;
		}
	}
	
	boolean deleteEvent(CalendarEvent event)
	{
		return calendarEvents.remove(event);
	}
        
    CalendarEvent getEvent(LocalDateTime dateOfEvent)
    {
    	CalendarEvent temp = new CalendarEvent(null, dateOfEvent, null, null);
        if(calendarEvents.ceiling(temp).compareTo(calendarEvents.floor(temp)) == 0) return calendarEvents.ceiling(temp);
        else return null;
    }
        
        //coś ma być
    boolean editEvent(CalendarEvent before, CalendarEvent after)
    {
                
    }
}
