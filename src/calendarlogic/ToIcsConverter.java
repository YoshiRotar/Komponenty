package calendarlogic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import calendardata.CalendarEvent;

public class ToIcsConverter
{
	private static PrintWriter writer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	
	public static void convert(CalendarEventContext calendarEventContext, String path)
	{
		try
		{
			writer = new PrintWriter(path, "UTF-8");
			writer.println("BEGIN:VCALENDAR");
			//writer.println("PRODID:-//Microsoft Corporation//Outlook 12.0 MIMEDIR//EN");
			//writer.println("VERSION:2.0");
			
			for(CalendarEvent calendarEvent : calendarEventContext.getCalendarEvents())
			{
				writer.println("BEGIN:VEVENT");
				
				writer.println("SUMMARY;LANGUAGE=pl:" + calendarEvent.getName());
				writer.println("LOCATION:" + calendarEvent.getPlace());
				writer.println("DTSTART:" + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
				writer.println("DTEND:" + dateFormat.format(Date.from(calendarEvent.getEndOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
				writer.println("DESCRIPTION:" + calendarEvent.getDescription());
				
//				if(ma brzęczyk)
//				{
//					writer.println("BEGIN:VALARM");
//					
//					do alarmu
//					
//					writer.println("END:VALARM");
//				}
				
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
