package calendarlogic;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import calendardata.CalendarEvent;

public class Alarm implements Runnable
{
	CalendarEvent calendarEvent;
	AlarmListener listener;

	public Alarm(AlarmListener listener, CalendarEvent calendarEvent)
	{
		this.listener = listener;
		this.calendarEvent = calendarEvent;
		
	}

	@Override
	public void run() 
	{
		if(calendarEvent.getBuzzer()==null) return;
		Duration duration = Duration.between(LocalDateTime.now(), calendarEvent.getBuzzer());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy H:mm:ss"); 
		System.out.println(duration);
		try 
		{
			System.out.println(duration.toSeconds());
			TimeUnit.SECONDS.sleep(duration.toSeconds());
			System.out.println(duration);
			String message = "<html>Przypomnienie!<br>Nazwa: " + calendarEvent.getName() + "<br>Miejsce: " + calendarEvent.getPlace() +
					"<br>Rozpoczêcie: " + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant()));
			listener.onAlarm(message);
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Problem z uspieniem watku");
		}
	}

}
