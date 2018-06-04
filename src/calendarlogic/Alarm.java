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
		try 
		{
			TimeUnit.SECONDS.sleep(duration.toSeconds());
			//Na wypadek gdyby czas przypomnienia zostal edytowany. Jesli jeszcze nie czas rozpocznij metode od poczatku
			System.out.println(calendarEvent.getBuzzer());
			System.out.println(LocalDateTime.now());
			if(Duration.between(LocalDateTime.now(), calendarEvent.getBuzzer()).compareTo(Duration.ofSeconds(1))>0)
			{
				System.out.println("weszlo");
				run();
				return;
			}
			System.out.println("przeslo");
			String message = "<html>Przypomnienie!<br>Nazwa: " + calendarEvent.getName() + "<br>Miejsce: " + calendarEvent.getPlace() +
					"<br>Rozpocz�cie: " + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant()));
			listener.onAlarm(message);
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Problem z uspieniem watku");
		}
	}

}
