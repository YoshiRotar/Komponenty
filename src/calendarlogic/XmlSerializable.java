package calendarlogic;

/**
 * Interfejs zawieraj¹cy metody zwi¹zane z serializacj¹ danych do pliku .xml
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe³ M³ynarczyk
 */
public interface XmlSerializable
{
	/**
     * Metoda pozawalaj¹ca na odczytanie œciezki dostêpu do pliku .xml
     * 
     * @see XmlSerializable
     * @return ³añcuch znakowy reprezentuj¹cy œcie¿kê dostêpu do pliku .xml
     */
	public String getXmlPath();
	
	/**
     * Meotda pozawalaj¹ca na ustawienie œciezki dostêpu do pliku .xml
     * 
     * @see XmlSerializable
     * @param xmlPath ³añcuch znakowy reprezentuj¹cy now¹ œcie¿kê dostêpu do pliku .xml
     */
	public void setXmlPath(String xmlPath);
	
	/**
	 * Metoda koduj¹ca dany obiekt do formatu xml i zapisuj¹ca zakodowane dane do pliku
	 */
	public void encodeToXml();
	
	/**
	 * Metoda dekoduj¹ca dany obiekt z formatu xml i odczytuj¹ca zakodowane dane z pliku
	 */
	public void decodeFromXml();
}
