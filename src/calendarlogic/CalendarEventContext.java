package calendarlogic;

import java.util.Iterator;
import java.util.TreeSet;


import calendardata.CalendarEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    TreeSet<CalendarEvent> getEventsFromCertainDay(LocalDate date)
    {
    	TreeSet<CalendarEvent> result = new TreeSet<CalendarEvent>();
    	Iterator<CalendarEvent> iterator = calendarEvents.iterator();
    	while(iterator.hasNext())
    	{
    		if(iterator.next().getStartOfEvent().toLocalDate().isEqual(date)) result.add(iterator.next());
    	}
    	return result;
    }
        
    boolean editEvent(CalendarEvent event, String name, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description)
    {
    	if(!calendarEvents.contains(event)) return false;
    	event.setName(name);
    	event.setStartOfEvent(startOfEvent);
    	event.setEndOfEvent(endOfEvent);
    	event.setDescription(description);
    	return true;
    }
}
