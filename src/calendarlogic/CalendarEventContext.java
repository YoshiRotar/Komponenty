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

/**
 * 
 * Klasa zawierająca informacje o zdarzeniach i metody pozwalające na nich operować. Implementuje ona interfejs
 * XmlSerializable
 * 
 * @author Mateusz Kuzniarek
 * @author Paweł Młynarczyk
 * 
 */
public class CalendarEventContext implements XmlSerializable
{
	private String xmlPath = "./data/events.xml";
	private TreeSet<CalendarEvent> calendarEvents = new TreeSet<CalendarEvent>();
	
	
	/**
	 * Zwraca zbiór wszystkich wydarzeń
	 * 
	 * @return zbiór wszystkich wydarzeń
	 */
	public TreeSet<CalendarEvent> getCalendarEvents()
	{
		return calendarEvents;
	}
	
	/**
	 * Uruchamia wątek danego przypomnienia
	 * 
	 * @param listener obiekt powiadamiany o nadejściu ustawionego czasu przypomnienia
	 * @param calendarEvent wydarzenie zawierające dane przypomnienie
	 */
	public void initAlarm(AlarmListener listener, CalendarEvent calendarEvent)
	{
		
		if(calendarEvent.getBuzzer()!=null)
		{
			Alarm alarm = new Alarm(listener, calendarEvent);
			new Thread(alarm).start();
		}
	}
	
	/**
	 * 
	 * Uruchamia wątki wszystkich wydarzeń
	 * 
	 * @param listener obiekt powiadamiany o nadejściu ustawionego czasu przypomnienia
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	public void encodeToXml()
	{
		XMLEncoder encoder=null;
		
		Path path = Paths.get(xmlPath);
		File file = path.getParent().toFile();
		file.mkdirs();
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
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
		finally
		{
			if(encoder != null )encoder.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * Metoda sprawdzająca poprawność wartości danego wydarzenia. Sprawdza ona czy: nazwa nie jest pusta; koniec wydarzenia nie jest
	 * wcześniej niż jej początek; przypomnienie jest ustawione przed rozpoczęciem wydarzenia oraz w przyszłości;
	 * wydarzenia nie nachodzą na siebie.
	 * 
	 * 
	 * @param newEvent sprawdzane wydarzenie
	 * @return wartość logiczna informująca o tym, czy dane wartości nie naruszają integralności danych
	 */
	private boolean checkIntegrity(CalendarEvent newEvent)
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
			return true;
		}
		else if ((calendarEvents.floor(newEvent) != null && calendarEvents.floor(newEvent).compareTo(newEvent) == 0))
		{
			return false;
		}
		else if ((calendarEvents.lower(newEvent) == null || calendarEvents.lower(newEvent).getEndOfEvent().isBefore(newEvent.getStartOfEvent())) && (calendarEvents.higher(newEvent) == null || calendarEvents.higher(newEvent).getStartOfEvent().isAfter(newEvent.getEndOfEvent())))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Metoda dodająca nowe wydarzenie
	 * 
	 * @param newEvent dodawane wydarzenie
	 * @return Wartość logiczna (powodzenie lub niepowdzenie dodania nowego wydarzenia)
	 */
	public boolean addEvent(CalendarEvent newEvent)
	{
		if(!checkIntegrity(newEvent)) return false;
		else
		{
			return calendarEvents.add(newEvent);
		}
	}
	
	/**
	 * Metoda usuwająca wydarzenie
	 * 
	 * @param event usuwane wydarzenie
	 * @return Wartość logiczna (powodzenie lub niepowdzenie usuni�cia wydarzenia)
	 */
	public boolean deleteEvent(CalendarEvent event)
	{
		if(calendarEvents.remove(event))
		{
			event.setRemovedFromTree(true);
			return true;
		}
		else return false;
	}
        
	/**
	 * Metoda zwracająca wydarzenie rozpoczynajace się w danej chwili czasowej identyfikującej dane wydarzenie
	 * 
	 * @param dateOfEvent chwila rozpoczęcia wydarzenia
	 * @return wydarzenie rozpoczynające się w danej chwili
	 */
    public CalendarEvent getEvent(LocalDateTime dateOfEvent)
    {
    	CalendarEvent temp = new CalendarEvent(null, null, dateOfEvent, null, null, null);
        if(calendarEvents.ceiling(temp).compareTo(calendarEvents.floor(temp)) == 0) return calendarEvents.ceiling(temp);
        else return null;
    }
    
    /**
     * Metoda zwracająca wszystkie wydarzenia z danego dnia w postaci Treesetu
     * 
     * @param date dzień, z którego zwracane są wydarzenia
     * @return Treeset zawierający wszystkie wydarzenia danego dnia
     */
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
        
    /**
     * Metoda edytująca pola danego wydarzenia
     * 
     * @param event referencja na edytowane wydarzenie
     * @param name nowa nazwa wydarzenia
     * @param place nowe miejsce wydarzenia
     * @param startOfEvent nowa chwila rozpoczęcia wydarzenia
     * @param endOfEvent nowa chwila zakonczenia wydarzenia
     * @param description nowy opis wydarzenia
     * @param buzzer nowa chwila przypomnienia o wydarzeniu (null w przypadku braku przypomnienia)
     * @return Wartość logiczna (powodzenie lub niepowdzenie edytowania wydarzenia)
     */
    public boolean editEvent(CalendarEvent event, String name, String place, LocalDateTime startOfEvent, LocalDateTime endOfEvent, String description, LocalDateTime buzzer)
    {
    	if(!calendarEvents.contains(event)) return false;
    	CalendarEvent tempEvent = new CalendarEvent(name, place, startOfEvent, endOfEvent, description, buzzer);
    	if(!checkIntegrity(tempEvent)) return false;
    	event.setName(name);
    	event.setPlace(place);
    	event.setStartOfEvent(startOfEvent);
    	event.setEndOfEvent(endOfEvent);
    	event.setDescription(description);
    	event.setBuzzer(buzzer);
    	return true;
    }
    
    /**
     * Metoda usuwająca wydarznia starsze niż dany dzień
     * @param date data, od której starsze wydarzenia zostaną usunięte
     * @return liczba usuniętych wydarzeń
     */
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

    /**
     * {@inheritDoc}
     */
    public String getXmlPath() 
	{
		return xmlPath;
	}

    /**
     * {@inheritDoc}
     */
	public void setXmlPath(String xmlPath) 
	{
		this.xmlPath = xmlPath;
}
}
