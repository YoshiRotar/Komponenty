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

/**
 * 
 * Klasa pozwalaj¹ca na po³¹czenie z baz¹ danych
 * 
 * @author Mateusz Kuzniarek
 * @author Pawe³ M³ynarczyk
 * 
 */
public class DatabaseProvider
{	
	private Connection connection;
	
	/**
	 * Metoda otwieraj¹ca po³¹czenie z baz¹ danych
	 */
	private void openConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://db4free.net:3306/mkpm_kalendarz?autoReconnect=true&useSSL=false" , "kalendarz", "kalendarz" );
		}
		catch (SQLException | ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Metoda zamykaj¹ca po³¹czenie z baz¹ danych
	 */
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
	
	/**
	 * 
	 * Metoda pobieraj¹ca dane ze zdalnej bazy danych i zapisuj¹ca je do obiektu przechowuj¹cego dane na u¿ytek programu
	 * 
	 * @param context obiekt, do którego zapisywane s¹ dane z bazy danych
	 */
	public void readFromDatabase(CalendarEventContext context)
	{
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		String name;
		String place;
		LocalDateTime startOfEvent;
		LocalDateTime endOfEvent;
		String description;
		LocalDateTime buzzer;
		CalendarEvent temp;
		try
		{
			this.openConnection();
			preparedStatement = this.connection.prepareStatement("SELECT * FROM `CalendarEvent`");
			resultSet = preparedStatement.executeQuery();
			context.getCalendarEvents().clear();
			while(resultSet.next())
			{
				name = resultSet.getString("Name");
				place = resultSet.getString("Place");
				startOfEvent = resultSet.getTimestamp("StartOfEvent").toLocalDateTime();
				endOfEvent = resultSet.getTimestamp("EndOfEvent").toLocalDateTime();
				description = resultSet.getString("Description");
				if(resultSet.getTimestamp("Buzzer")==null) buzzer = null;
				else buzzer = resultSet.getTimestamp("Buzzer").toLocalDateTime();
				temp = new CalendarEvent(name, place, startOfEvent, endOfEvent, description, buzzer);
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
	
	/**
	 * Metoda zapisuj¹ca dane znajduj¹ce siê w pamiêci do zdalnej bazy danych
	 * 
	 * @param context obiekt zawieraj¹cy informacje o wydarzeniach
	 */
	public void writeIntoDatabase(CalendarEventContext context)
	{
		PreparedStatement preparedStatement;
		String name;
		String place;
		Timestamp startOfEvent;
		Timestamp endOfEvent;
		String description;
		Timestamp buzzer;
		CalendarEvent temp;
		try
		{
			this.openConnection();
			preparedStatement = this.connection.prepareStatement("TRUNCATE TABLE `CalendarEvent`");
			preparedStatement.executeUpdate();
			preparedStatement = this.connection.prepareStatement("INSERT INTO `CalendarEvent`(`Name`, `Place`, `StartOfEvent`, `EndOfEvent`, `Description`, `Buzzer`) VALUES (?, ?, ?, ?, ?, ?)");
			Iterator<CalendarEvent> iterator = context.getCalendarEvents().iterator();
			while(iterator.hasNext())
			{
				temp = iterator.next();
				name = temp.getName();
				place = temp.getPlace();
				startOfEvent = Timestamp.valueOf(temp.getStartOfEvent());
				endOfEvent = Timestamp.valueOf(temp.getEndOfEvent());
				description = temp.getDescription();
				if(temp.getBuzzer()==null) buzzer = null;
				else buzzer = Timestamp.valueOf(temp.getBuzzer());
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, place);
	            preparedStatement.setTimestamp(3, startOfEvent);
	            preparedStatement.setTimestamp(4, endOfEvent);
	            preparedStatement.setString(5, description);
	            preparedStatement.setTimestamp(6, buzzer);
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
