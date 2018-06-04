package calendarlogic;

public interface XmlSerializable
{
	public String getXmlPath();
	public void setXmlPath(String xmlPath);
	public void encodeToXml();
	public void decodeFromXml();
}
