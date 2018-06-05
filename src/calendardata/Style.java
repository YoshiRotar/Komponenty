package calendardata;

import java.awt.Color;

/**
 * Klasa zawierająca informacje na temat wyglądu interfejsu graficznego użytkownika. Nie zawiera ona żadnych dodatkowych metod
 * działajacych na danych. Te znajduję się w warstwie wyższej.
 * 
 * @author Mateusz Kuzniarek
 * @author Paweł Młynarczyk
 */
public class Style
{
	String font;
	Color fontColor;
	Color backgroundColor;
	String alarmPath;

	/**
	 * Konstruktor uzupełniający pola domyślnymi wartościami
	 */
	public Style()
	{
		if(font == null) font = "Arial";
		if(fontColor == null || backgroundColor == null)
		{
			fontColor = Color.BLACK;
			backgroundColor = Color.WHITE;
		}
		if(alarmPath == null) alarmPath = "./data/alarm.wav";
	}
	
	/**
	 * Metoda udostępniajca nazwę czcionki
	 * @return nazwa czcionki
	 */
	public String getFont()
	{
		return font;
	}
	
	/**
	 * Metoda modyfikująca nazwę czcionki
	 * @param font nazwa czcionki
	 */
	public void setFont(String font)
	{
		this.font = font;
	}
	
	/**
	 * Metoda udostępniająca kolor czcionki
	 * @return kolor czcionki
	 */
	public Color getFontColor()
	{
		return fontColor;
	}
	
	/**
	 * Metoda modyfikująca kolor czcionki
	 * @param fontColor kolor czcionki
	 */
	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}

	/**
	 * Metoda udostępniająca kolor tła
	 * @return kolor tła
	 */
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * Metoda modyfikująca kolor tła
	 * @param backgroundColor kolor tła
	 */
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
	
	/**
	 * Metoda udostępniająca ścieżkę dostępu do pliku .wav zawierającego dzwiek alarmu
	 * @return ścieżka dostępu do pliku .wav zawierającego dzwiek alarmu
	 */
	public String getAlarmPath()
	{
		return alarmPath;
	}

	/**
	 * Metoda modyfikująca ścieżkę dostępu do pliku .wav zawierającego dzwiek alarmu
	 * @param alarmPath ściezka dostępu do pliku .wav zawierającego dzwiek alarmu
	 */
	public void setAlarmPath(String alarmPath)
	{
		this.alarmPath = alarmPath;
	}
}
