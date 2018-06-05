package calendarlogic;

/**
 * Interfejs zawierający metody związane z serializacją danych do pliku .xml
 * 
 * @author Mateusz Kuzniarek
 * @author Paweł Młynarczyk
 */
public interface XmlSerializable
{
	/**
     * Metoda pozawalająca na odczytanie ściezki dostępu do pliku .xml
     * 
     * @see XmlSerializable
     * @return �a�cuch znakowy reprezentujący ściezkę dostępu do pliku .xml
     */
	public String getXmlPath();
	
	/**
     * Meotda pozawalająca na ustawienie ściezki dostępu do pliku .xml
     * 
     * @see XmlSerializable
     * @param xmlPath �a�cuch znakowy reprezentujacy nową ścieżkę dostępu do pliku .xml
     */
	public void setXmlPath(String xmlPath);
	
	/**
	 * Metoda kodująca dany obiekt do formatu xml i zapisująca zakodowane dane do pliku
	 */
	public void encodeToXml();
	
	/**
	 * Metoda dekodująca dany obiekt z formatu xml i odczytująca zakodowane dane z pliku
	 */
	public void decodeFromXml();
}
