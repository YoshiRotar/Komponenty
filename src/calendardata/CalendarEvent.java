package calendardata;

import java.time.LocalDateTime;

public class CalendarEvent implements Comparable<CalendarEvent>
{
	String name;
	LocalDateTime startOfEvent;
	LocalDateTime endOfEvent;
	String description;
	
	public CalendarEvent(String name, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description) 
	{
		super();
		this.name = name;
		this.startOfEvent = startOfEvent;
		this.endOfEvent = endOfEvent;
		this.description = description;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public LocalDateTime getStartOfEvent() 
	{
		return startOfEvent;
	}

	public void setStartOfEvent(LocalDateTime startOfEvent) 
	{
		this.startOfEvent = startOfEvent;
	}

	public LocalDateTime getEndOfEvent() 
	{
		return endOfEvent;
	}

	public void setEndOfEvent(LocalDateTime endOfEvent) 
	{
		this.endOfEvent = endOfEvent;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	@Override
	public int compareTo(CalendarEvent event)
	{
		if(this.startOfEvent.isAfter(event.getStartOfEvent())) return 1;
		else if (this.startOfEvent.isBefore(event.getStartOfEvent())) return -1;
		else return 0;
	}
}
