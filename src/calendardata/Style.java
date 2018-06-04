package calendardata;

import java.awt.Color;

public class Style
{
	String font;
	Color fontColor;
	Color backgroundColor;
	String alarmPath;

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
	
	public String getFont()
	{
		return font;
	}
	
	public void setFont(String font)
	{
		this.font = font;
	}

	public Color getFontColor()
	{
		return fontColor;
	}

	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
	
	public String getAlarmPath()
	{
		return alarmPath;
	}

	public void setAlarmPath(String alarmPath)
	{
		this.alarmPath = alarmPath;
	}
}
