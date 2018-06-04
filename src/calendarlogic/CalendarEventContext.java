package calendarlogic;

import java.util.Iterator;
import java.util.TreeSet;


import calendardata.CalendarEvent;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalendarEventContext 
{
	private String xmlPath = "./data/events.xml";
	private TreeSet<CalendarEvent> calendarEvents = new TreeSet<CalendarEvent>();
	
	public String getXmlPath() 
	{
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) 
	{
		this.xmlPath = xmlPath;
	}
	
	public TreeSet<CalendarEvent> getCalendarEvents()
	{
		return calendarEvents;
	}
	
	public void initAlarm(AlarmListener listener, CalendarEvent calendarEvent)
	{
		
		if(calendarEvent.getBuzzer()!=null)
		{
			Alarm alarm = new Alarm(listener, calendarEvent);
			new Thread(alarm).start();
		}
	}
	
	public void initAlarms(AlarmListener listener)
	{
		System.out.println();
		for(CalendarEvent calendarEvent : calendarEvents)
		{
			if(calendarEvent.getBuzzer()!=null)
			{
				initAlarm(listener, calendarEvent);
			}
		}
	}
	
	public void encodeToXml()
	{
		XMLEncoder encoder=null;
		
		Path path = Paths.get(xmlPath);
		File file = path.getParent().toFile();
		file.mkdirs();
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
			encoder.setPersistenceDelegate(LocalDateTime.class, new PersistenceDelegate() 
				{
                    @Override
                    protected Expression instantiate(Object obj, Encoder encdr) 
                    {
                        LocalDateTime localDateTime = (LocalDateTime) obj;
                        return new Expression(localDateTime, LocalDateTime.class, "parse", new Object[]{localDateTime.toString()});
                    }
		        });

			encoder.writeObject(calendarEvents);
		}
		catch(Exception e)
		{
			System.out.println("Nie udało się otworzyc pliku xml");
		}
		encoder.close();
	}
	
	@SuppressWarnings("unchecked")
	public void decodeFromXml()
	{
		XMLDecoder decoder=null;
		File file = new File(xmlPath);
		if(!file.exists()) return;
		try 
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlPath)));
			this.calendarEvents = (TreeSet<CalendarEvent>)decoder.readObject();
		} 
		catch (Exception e) 
		{
			System.out.println("Nie udalo sie otworzyc pliku xml");
		}
	}
	
	public boolean addEvent(CalendarEvent newEvent)
	{
		if(newEvent.getName().equals("") || newEvent.getStartOfEvent() == null)
		{
			return false;
		}
		else if (newEvent.getStartOfEvent().isAfter(newEvent.getEndOfEvent()))
		{
			return false;
		}
		else if (newEvent.getBuzzer()!=null && newEvent.getBuzzer().isAfter(newEvent.getStartOfEvent()))
		{
			return false;
		}
		else if (newEvent.getBuzzer()!=null && newEvent.getBuzzer().isBefore(LocalDateTime.now()))
		{
			return false;
		}
		else if(calendarEvents.isEmpty())
		{
			return calendarEvents.add(newEvent);
		}
		else if ((calendarEvents.floor(newEvent) != null && calendarEvents.floor(newEvent).compareTo(newEvent) == 0))
		{
			return false;
		}
		else if ((calendarEvents.lower(newEvent) == null || calendarEvents.lower(newEvent).getEndOfEvent().isBefore(newEvent.getStartOfEvent())) && (calendarEvents.higher(newEvent) == null || calendarEvents.higher(newEvent).getStartOfEvent().isAfter(newEvent.getEndOfEvent())))
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
    	CalendarEvent temp = new CalendarEvent(null, null, dateOfEvent, null, null, null);
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
        
    public boolean editEvent(CalendarEvent event, String name, String place, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description, LocalDateTime buzzer)
    {
    	if(!calendarEvents.contains(event)) return false;
    	event.setName(name);
    	event.setPlace(place);
    	event.setStartOfEvent(startOfEvent);
    	event.setEndOfEvent(endOfEvent);
    	event.setDescription(description);
    	event.setBuzzer(buzzer);
    	return true;
    }
    
    public int eraseOlderThan(LocalDate date)
    {
    	int elements = 0;
    	LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
    	CalendarEvent temp = new CalendarEvent(null, null, dateTime, null, null, null);
    	while (this.calendarEvents.lower(temp) != null)
    	{
    		this.calendarEvents.remove(this.calendarEvents.lower(temp));
    		elements++;
    	}
		return elements;
    }
}
