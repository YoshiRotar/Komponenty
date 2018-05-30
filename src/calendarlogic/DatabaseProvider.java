package calendarlogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;

import calendardata.CalendarEvent;

public class DatabaseProvider
{	
	private Connection connection;
	
	private void openConnection()
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://db4free.net:3306/mkpm_kalendarz?autoReconnect=true&useSSL=false" , "kalendarz", "kalendarz" );
		}
		catch (SQLException /*| ClassNotFoundException*/ e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void closeConnection()
	{
		try
		{
			connection.close();
		} 
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void readFromDatabase(CalendarEventContext context)
	{
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		String name;
		String place;
		LocalDateTime startOfEvent;
		LocalDateTime endOfEvent;
		String description;
		CalendarEvent temp;
		try
		{
			this.openConnection();
			preparedStatement = this.connection.prepareStatement("SELECT * FROM `CalendarEvent`");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				name = resultSet.getString("Name");
				place = resultSet.getString("Place");
				startOfEvent = resultSet.getTimestamp("StartOfEvent").toLocalDateTime();
				endOfEvent = resultSet.getTimestamp("EndOfEvent").toLocalDateTime();
				description = resultSet.getString("Description");
				temp = new CalendarEvent(name, place, startOfEvent, endOfEvent, description);
				context.getCalendarEvents().add(temp);
			}
			resultSet.close();
			preparedStatement.close();
			this.closeConnection();
		} 
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void writeIntoDatabase(CalendarEventContext context)
	{
		PreparedStatement preparedStatement;
		String name;
		String place;
		Timestamp startOfEvent;
		Timestamp endOfEvent;
		String description;
		CalendarEvent temp;
		try
		{
			this.openConnection();
			preparedStatement = this.connection.prepareStatement("TRUNCATE TABLE `CalendarEvent`");
			preparedStatement.executeUpdate();
			preparedStatement = this.connection.prepareStatement("INSERT INTO `CalendarEvent`(`Name`, `Place`, `StartOfEvent`, `EndOfEvent`, `Description`) VALUES (?, ?, ?, ?, ?)");
			Iterator<CalendarEvent> iterator = context.getCalendarEvents().iterator();
			while(iterator.hasNext())
			{
				temp = iterator.next();
				name = temp.getName();
				place = temp.getPlace();
				startOfEvent = Timestamp.valueOf(temp.getStartOfEvent());
				endOfEvent = Timestamp.valueOf(temp.getEndOfEvent());
				description = temp.getDescription();
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, place);
	            preparedStatement.setTimestamp(3, startOfEvent);
	            preparedStatement.setTimestamp(4, endOfEvent);
	            preparedStatement.setString(5, description);
	            preparedStatement.executeUpdate();
			}
			preparedStatement.close();
			this.closeConnection();
		} 
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
