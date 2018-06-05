package calendardata;

import java.time.LocalDateTime;

/**
 * Klasa zawierająca informacje o pojedynczym wydarzeniu: Jego nazwę, miejsce, czas rozpoczęcia, zakończenia, opis
 * i czas przypomnienia (null w przypadku braku przypomnienia). Klasa implementuje interfejs Comparable.
 * 
 * 
 * @author Mateusz Kuzniarek
 * @author Paweł Młynarczyk
 */
public class CalendarEvent implements Comparable<CalendarEvent>
{
	private String name;
	private String place;
	private LocalDateTime startOfEvent;
	private LocalDateTime endOfEvent;
	private String description;
	private LocalDateTime buzzer;
	transient boolean removedFromTree = false;

	/**
	 * Konstruktor uzupełniający pola podanymi wartościami
	 * 
	 * @param name nazwa wydarzenia
     * @param place miejsce wydarzenia
     * @param startOfEvent chwila rozpoczęcia wydarzenia
     * @param endOfEvent chwila zakończenia wydarzenia
     * @param description opis wydarzenia
     * @param buzzer chwila przypomnienia o wydarzeniu (null w przypadku braku przypomnienia)
	 */
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
	/**
	 * Pusty konstruktor potrzebny do poprawnego wykonania serializacji
	 */
	public CalendarEvent()
	{
		
	}
	
	/*
	 *  Sprawdzenie, czy wydarzenie zostało usunięte. Pozwala to zadecydować o tym,
	 *  czy ustawione wcześniej przypomnienie powinno się uaktywnić
	 */
	public boolean isRemovedFromTree() 
	{
		return removedFromTree;
	}

	/**
	 * Ustawienie zmiennej informującej o tym, czy wydarzenie zostało usunięte. Pozwala to zadecydować o tym,
	 *  czy ustawione wcześniej przypomnienie powinno się uaktywnić 
	 * @param removedFromTree nowa wartość zmiennej informującej o tym, czy wydarzenie zostało usunięte
	 */
	public void setRemovedFromTree(boolean removedFromTree) 
	{
		this.removedFromTree = removedFromTree;
	}
	
	/**
	 * Metoda zwracająca chwilę przypomnienia o wydarzeniu.
	 * 
	 * @return chwila przypomnienia o wydarzeniu (null w przypadku braku przypomnienia) 
	 */
	public LocalDateTime getBuzzer() 
	{
		return buzzer;
	}

	/**
	 * Metoda ustawiająca chwilę przypomnienia o wydarzeniu.
	 * @param buzzer nowa chwila przypomnienia o wydarzeniu (null w przypadku braku przypomnienia) 
	 */
	public void setBuzzer(LocalDateTime buzzer) 
	{
		this.buzzer = buzzer;
	}

	/**
	 * Metoda zwracająca nazwę wydarzenia
	 * 
	 * @return nazwa wydarzenia
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Metoda ustawiająca nazwę wydarzenia
	 * @param name nowa nazwa wydarzenia
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * MEtoda zwracająca miejsce wydarzenia
	 * @return miejsce wydarzenia
	 */
	public String getPlace()
	{
		return place;
	}

	/**
	 * Metoda ustawiająca miejsce wydarzenia
	 * @param place nowe miejsce wydarzenia
	 */
	public void setPlace(String place)
	{
		this.place = place;
	}

	/**
	 * Metoda zwracająca chwilę rozpoczęcia wydarzenia
	 * @return chwila rozpoczęcia wydarzenia
	 */
	public LocalDateTime getStartOfEvent() 
	{
		return startOfEvent;
	}

	/**
	 * Metoda ustawiająca chwilę rozpoczęcia wydarzenia
	 * @param startOfEvent nowa chwila rozpoczęcia wydarzenia
	 */
	public void setStartOfEvent(LocalDateTime startOfEvent) 
	{
		this.startOfEvent = startOfEvent;
	}

	/**
	 * Metoda zwracająca chwilę zakończenia wydarzenia
	 * @return chwila zakończenia wydarzenia
	 */
	public LocalDateTime getEndOfEvent() 
	{
		return endOfEvent;
	}

	/**
	 * Metoda ustawiająca chwilę zakończenia wydarzenia
	 * @param endOfEvent nowa chwila zakończenia wydarzenia
	 */
	public void setEndOfEvent(LocalDateTime endOfEvent) 
	{
		this.endOfEvent = endOfEvent;
	}

	/**
	 * Metoda zwracająca opis wydarzenia
	 * @return opis wydarzenia
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * Metoda ustawiająca opis wydarzenia
	 * @param description nowy opis wydarzenia
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	@Override
	/**
	 * Metoda porównująca dwa wydarzenia. Porównywanie wydarzeń odbywa się poprzez porównanie ich chwil rozpoczęcia.
	 */
	public int compareTo(CalendarEvent event)
	{
		if(this.startOfEvent.isAfter(event.getStartOfEvent())) return 1;
		else if (this.startOfEvent.isBefore(event.getStartOfEvent())) return -1;
		else return 0;
	}
}
