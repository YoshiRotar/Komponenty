package calendardata;

import java.awt.Color;

public class Style
{
	String font = "Arial";
	Color fontColor = Color.BLACK;
	Color backgroundColor = Color.WHITE;
	
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
	

}
