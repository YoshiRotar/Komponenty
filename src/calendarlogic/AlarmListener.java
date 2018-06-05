package calendarlogic;
/**
 * 
 * Interfejs wymuszaj¹cy implementacjê metody reaguj¹cej na nadejœcie czasu przypomnienia
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe³ M³ynarczyk
 *
 */
public interface AlarmListener 
{
	/**
	 * Metoda wykonywana podczas nadejœcia ustawionego czasu przypomnienia
	 * @param message ³añcuch znakowy zawieraj¹cy podstawowe informacje o przypominanym wydarzeniu 
	 */
	public void onAlarm(String message);
}
