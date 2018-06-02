package calendarlogic;

import java.util.Iterator;
import java.util.TreeSet;


import calendardata.CalendarEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalendarEventContext 
{
	private TreeSet<CalendarEvent> calendarEvents = new TreeSet<CalendarEvent>();
	
	public TreeSet<CalendarEvent> getCalendarEvents()
	{
		return calendarEvents;
	}

	public boolean addEvent(CalendarEvent newEvent)
	{
		if(newEvent.getName() == null || newEvent.getStartOfEvent() == null)
		{
			return false;
		}
		else if (newEvent.getStartOfEvent().isAfter(newEvent.getEndOfEvent()))
		{
			return false;
		}
		else if(calendarEvents.lower(newEvent) == null)
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
	
	public boolean deleteEvent(CalendarEvent event)
	{
		return calendarEvents.remove(event);
	}
        
    public CalendarEvent getEvent(LocalDateTime dateOfEvent)
    {
    	CalendarEvent temp = new CalendarEvent(null, null, dateOfEvent, null, null);
        if(calendarEvents.ceiling(temp).compareTo(calendarEvents.floor(temp)) == 0) return calendarEvents.ceiling(temp);
        else return null;
    }
    
    public TreeSet<CalendarEvent> getEventsFromCertainDay(LocalDate date)
    {
    	TreeSet<CalendarEvent> result = new TreeSet<CalendarEvent>();
    	CalendarEvent temp;
    	Iterator<CalendarEvent> iterator = calendarEvents.iterator();
    	while(iterator.hasNext())
    	{
    		temp = iterator.next();
    		if(temp.getStartOfEvent().toLocalDate().isEqual(date)) result.add(temp);
    	}
    	return result;
    }
        
    public boolean editEvent(CalendarEvent event, String name, String place, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description)
    {
    	if(!calendarEvents.contains(event)) return false;
    	event.setName(name);
    	event.setPlace(place);
    	event.setStartOfEvent(startOfEvent);
    	event.setEndOfEvent(endOfEvent);
    	event.setDescription(description);
    	return true;
    }
    
    public int eraseOlderThan(LocalDate date)
    {
    	int elements = 0;
    	LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
    	CalendarEvent temp = new CalendarEvent(null, null, dateTime, null, null);
    	while (this.calendarEvents.lower(temp) != null)
    	{
    		this.calendarEvents.remove(this.calendarEvents.lower(temp));
    		elements++;
    	}
		return elements;
    }
}
