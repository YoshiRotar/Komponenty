package calendarlogic;
/**
 * 
 * Interfejs wymuszający implementację metody reagującej na nadejście czasu przypomnienia
 * 
 * @author Mateusz Kuzniarek
 * @author Paweł Młynarczyk
 *
 */
public interface AlarmListener 
{
	/**
	 * Metoda wykonywana podczas nadejścia ustawionego czasu przypomnienia
	 * @param message łańcuch znakowy zawierający podstawowe informacje o przypominanym wydarzeniu 
	 */
	public void onAlarm(String message);
}
