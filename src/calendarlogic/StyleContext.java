package calendarlogic;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import calendardata.Style;

public class StyleContext implements XmlSerializable
{
	private Style style = new Style();
	private String xmlPath = "./data/styles.xml";

	public Style getStyle()
	{
		return style;
	}

	@Override
	public String getXmlPath()
	{
		return xmlPath;
	}

	@Override
	public void setXmlPath(String xmlPath)
	{
		this.xmlPath = xmlPath;
	}

	@Override
	public void encodeToXml()
	{
		XMLEncoder encoder=null;
		
		Path path = Paths.get(xmlPath);
		File file = path.getParent().toFile();
		file.mkdirs();
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
			encoder.setPersistenceDelegate(LocalDateTime.class, new PersistenceDelegate() 
				{
                    @Override
                    protected Expression instantiate(Object obj, Encoder encdr) 
                    {
                        LocalDateTime localDateTime = (LocalDateTime) obj;
                        return new Expression(localDateTime, LocalDateTime.class, "parse", new Object[]{localDateTime.toString()});
                    }
		        });

			encoder.writeObject(style);
		}
		catch(Exception e)
		{
			System.out.println("Nie udało się otworzyc pliku xml");
		}
		finally
		{
			if(encoder != null )encoder.close();
		}
	}

	@Override
	public void decodeFromXml()
	{
		XMLDecoder decoder=null;
		File file = new File(xmlPath);
		if(!file.exists()) return;
		try 
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlPath)));
			this.style = (Style)decoder.readObject();
		} 
		catch (Exception e) 
		{
			System.out.println("Nie udalo sie otworzyc pliku xml");
		}
	}
	
	
}
