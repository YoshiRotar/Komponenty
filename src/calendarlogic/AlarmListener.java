package calendarlogic;
/**
 * 
 * Interfejs wymuszaj�cy implementacj� metody reaguj�cej na nadej�cie czasu przypomnienia
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe� M�ynarczyk
 *
 */
public interface AlarmListener 
{
	/**
	 * Metoda wykonywana podczas nadej�cia ustawionego czasu przypomnienia
	 * @param message �a�cuch znakowy zawieraj�cy podstawowe informacje o przypominanym wydarzeniu 
	 */
	public void onAlarm(String message);
}
