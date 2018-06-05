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
 * Obiekty klasy Alarm reprezentuj� pojedyncze przypomnienia. Komunikuj� si� one z graficznym interfejsem u�ytkownika
 * przy pomocy wzorca projektowego - Obserwator Klasa ta implementuje interfejsc Runnable, aby umo�liwi� przy jej pomocy
 * utworzenie nowego w�tku.
 * 
 * @see AlarmListener
 * @author Mateusz Kuzniarek
 * @author Pawe� M�ynarczyk
 *
 */
public class Alarm implements Runnable
{
	
	private CalendarEvent calendarEvent;
	private AlarmListener listener;

	/**
	 * Konstruktor uzupe�niaj�cy pola podanymi warto�ciami
	 * @param listener obiekt powiadamiany o nadej�ciu ustawionego czasu przypomnienia
	 * @param calendarEvent wydarzenie, kt�re ma zosta� przypomniane
	 */
	public Alarm(AlarmListener listener, CalendarEvent calendarEvent)
	{
		this.listener = listener;
		this.calendarEvent = calendarEvent;
		
	}

	@Override
	/**
	 * Metoda oczekuj�ca na nadej�cie czasu przypomnienia, a nast�pnie informuj�ca o tym podany obiekt klasy AlarmListener.
	 * Przeznaczona jest ona do dzia�ania w oddzielnym w�tku.
	 */
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
					"<br>Rozpocz�cie: " + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant()));
			listener.onAlarm(message);
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Problem z uspieniem watku");
		}
	}

}
