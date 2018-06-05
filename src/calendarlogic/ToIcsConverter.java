package calendarlogic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import calendardata.CalendarEvent;

/**
 * 
 * Klasa zawieraj¹ca operacjê konwersji wydarzeñ do formatu .ics (format ten umo¿liwia zaimportowanie wydarzeñ
 * do programu Microsoft OutLook
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe³ M³ynarczyk
 */
public class ToIcsConverter
{
	private static PrintWriter writer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	
	/**
	 * Statyczna metoda konwertuj¹ca wydarzenie do postaci w formacie .ics
	 * @param calendarEventContext obiekt zawieraj¹cy konwertowane wydarzenia
	 * @param path ³añcuch reprezentuj¹cy œcie¿kê dostêpu do miejsca, w którym zapisany zostanie plik .ics
	 */
	public static void convert(CalendarEventContext calendarEventContext, String path)
	{
		try
		{
			writer = new PrintWriter(path, "UTF-8");
			writer.println("BEGIN:VCALENDAR");
			
			for(CalendarEvent calendarEvent : calendarEventContext.getCalendarEvents())
			{
				writer.println("BEGIN:VEVENT");
				
				writer.println("SUMMARY;LANGUAGE=pl:" + calendarEvent.getName());
				writer.println("LOCATION:" + calendarEvent.getPlace());
				writer.println("DTSTART:" + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
				writer.println("DTEND:" + dateFormat.format(Date.from(calendarEvent.getEndOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
				writer.println("DESCRIPTION:" + calendarEvent.getDescription());
				
				if(calendarEvent.getBuzzer() != null)
				{
					writer.println("BEGIN:VALARM");		
					
					writer.println("TRIGGER;VALUE=DATE-TIME:" + dateFormat.format(Date.from(calendarEvent.getBuzzer().atZone(ZoneId.systemDefault()).toInstant())));
					writer.println("ACTION:DISPLAY");
					
					writer.println("END:VALARM");
				}
				
				writer.println("END:VEVENT");
			}
			
			writer.println("END:VCALENDAR");
		} 
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(writer != null) writer.close();
		}
	}
}
