package calendarlogic;

/**
 * Interfejs zawieraj�cy metody zwi�zane z serializacj� danych do pliku .xml
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe� M�ynarczyk
 */
public interface XmlSerializable
{
	/**
     * Metoda pozawalaj�ca na odczytanie �ciezki dost�pu do pliku .xml
     * 
     * @see XmlSerializable
     * @return �a�cuch znakowy reprezentuj�cy �cie�k� dost�pu do pliku .xml
     */
	public String getXmlPath();
	
	/**
     * Meotda pozawalaj�ca na ustawienie �ciezki dost�pu do pliku .xml
     * 
     * @see XmlSerializable
     * @param xmlPath �a�cuch znakowy reprezentuj�cy now� �cie�k� dost�pu do pliku .xml
     */
	public void setXmlPath(String xmlPath);
	
	/**
	 * Metoda koduj�ca dany obiekt do formatu xml i zapisuj�ca zakodowane dane do pliku
	 */
	public void encodeToXml();
	
	/**
	 * Metoda dekoduj�ca dany obiekt z formatu xml i odczytuj�ca zakodowane dane z pliku
	 */
	public void decodeFromXml();
}
