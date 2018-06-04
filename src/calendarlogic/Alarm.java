package calendarlogic;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import calendardata.CalendarEvent;
/**
 * 
 * Obiekty klasy Alarm reprezentuj¹ pojedyncze przypomnienia 
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe³ M³ynarczyk
 *
 */
public class Alarm implements Runnable
{
	
	private CalendarEvent calendarEvent;
	private AlarmListener listener;

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
			if(Duration.between(LocalDateTime.now(), calendarEvent.getBuzzer()).compareTo(Duration.ofSeconds(1))>0)
			{
				System.out.println("weszlo");
				run();
				return;
			}
			if(calendarEvent.isRemovedFromTree()) return;
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
