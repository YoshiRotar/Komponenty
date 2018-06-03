package calendardata;

import java.time.LocalDateTime;

public class CalendarEvent implements Comparable<CalendarEvent>
{
	String name;
	String place;
	LocalDateTime startOfEvent;
	LocalDateTime endOfEvent;
	String description;
	LocalDateTime buzzer;
	
	public CalendarEvent(String name, String place, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description, LocalDateTime buzzer) 
	{
		super();
		this.name = name;
		this.place = place;
		this.startOfEvent = startOfEvent;
		this.endOfEvent = endOfEvent;
		this.description = description;
		this.buzzer = buzzer;
	}
	
	//do serializacji
	public CalendarEvent()
	{
		
	}
	
	public LocalDateTime getBuzzer() 
	{
		return buzzer;
	}

	public void setBuzzer(LocalDateTime buzzer) 
	{
		this.buzzer = buzzer;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getPlace()
	{
		return place;
	}

	public void setPlace(String place)
	{
		this.place = place;
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
