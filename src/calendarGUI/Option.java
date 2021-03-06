package calendarGUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import calendardata.CalendarEvent;
import calendarlogic.CalendarEventContext;

/**
 * 
 * Klasa abstrakcyjna będąca podstawą dla klas AddEvent i EditEvent, jako że ich konstrukcja jest bardzo zbliżona.
 * 
 * @author Paweł Młynarczyk
 * @author Mateusz Kuzniarek
 *
 */
@SuppressWarnings("serial")
public abstract class Option extends JFrame
{
	protected MainWindow mainWindow;
	protected CalendarEventContext calendarEventContext;
	protected JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	protected JTextField textFieldName;
	protected JTextField textFieldPlace;
	protected JTextArea textDescription;
	protected SpinnerModel startTimeSpinnerModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
	protected SpinnerModel endTimeSpinnerModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
	protected SpinnerModel timeAlarmSpinnerModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
	protected SpinnerModel startDateSpinnerModel;
	protected SpinnerModel endDateSpinnerModel;
	protected SpinnerModel dateAlarmSpinnerModel;
	protected JSpinner startTimeSpinner = new JSpinner(startTimeSpinnerModel);
	protected JSpinner endTimeSpinner = new JSpinner(endTimeSpinnerModel);
	protected JSpinner timeAlarmSpinner = new JSpinner(timeAlarmSpinnerModel);
	protected JSpinner startDateSpinner;
	protected JSpinner endDateSpinner;
	protected JSpinner dateAlarmSpinner;
	protected JCheckBox alarmCheckBox;
	protected JLabel alarmTimeLabel;
	protected JLabel alarmDateLabel;
	
	/**
	 * Metoda tworząca blok służący wpisaniu nazwy wydarzenia.
	 */
	protected void addName()
	{
		//nazwa
		JLabel label = new JLabel("Nazwa Wydarzenia:");
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label);
		
		textFieldName = new JTextField();
		textFieldName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		textFieldName.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldName);
	}
	
	/**
	 * Metoda tworząca blok służący wpisaniu miejsca wydarzenia.
	 */
	protected void addPlace()
	{
		//miejsce
		JLabel label_1 = new JLabel("Miejsce Wydarzenia:");
		label_1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_1);
		
		textFieldPlace = new JTextField();
		textFieldPlace.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		textFieldPlace.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldPlace);
	}
	
	/**
	 * Metoda tworząca blok służący wpisaniu opisu wydarzenia.
	 */
	protected void addDescription()
	{
		//opis
		JLabel label_2 = new JLabel("Opis Wydarzenia:");
		label_2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_2);
		
		textDescription = new JTextArea();
		
		
		JScrollPane areaScrollPane = new JScrollPane(textDescription);
		areaScrollPane.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setMaximumSize(new Dimension(500, 200));
		areaScrollPane.setMinimumSize(new Dimension(500, 200));	
		contentPane.add(areaScrollPane);
	}
	
	/**
	 * Metoda tworząca blok służący wpisaniu czasu związanego z wydarzeniem.
	 * 
	 * @param label opis etykiety użytej w bloku
	 * @param spinner referencja na obiekt spinnera dotyczącego czasu
	 */
	protected void addTime(String label, JSpinner spinner)
	{
		//godzina
		JLabel label_3 = new JLabel(label);
		label_3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_3);
		spinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		spinner.setMaximumSize(new Dimension(500,30));
		spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm:ss"));
		contentPane.add(spinner);
	}
	
	/**
	 * Metoda tworząca blok służący wpisaniu daty związanej z wydarzeniem.
	 * 
	 * @param label opis etykiety użytej w bloku
	 * @param spinner referencja na obiekt spinnera dotyczącego daty
	 */
	protected void addDate(String label, JSpinner spinner)
	{
		//data
		JLabel label_5 = new JLabel(label);
		label_5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_5);
		spinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		spinner.setMaximumSize(new Dimension(500,30));
		spinner.setEditor(new JSpinner.DateEditor(spinner, "dd:MM:yyyy"));
		contentPane.add(spinner);
	}
	
	/**
	 * Metoda tworząca blok służący do ustawiania parametrów brzęczyka.
	 */
	protected void addAlarm()
	{
		//przypomnienie
		JPanel alarmPanel = new JPanel();
		alarmPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		SpringLayout spring = new SpringLayout();
		alarmPanel.setLayout(spring);
		alarmPanel.setMaximumSize(new Dimension(500,30));
		
		JPanel alarmDatePanel = new JPanel();
		alarmDatePanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.setLayout(new BoxLayout(alarmDatePanel, BoxLayout.Y_AXIS));
		alarmDatePanel.setMaximumSize(new Dimension(500,90));
		
		JLabel label_4 = new JLabel("Przypomnienie:");
		label_4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmPanel.add(label_4);
		
		alarmCheckBox = new JCheckBox();
		alarmCheckBox.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
		    	if(alarmDatePanel.getComponent(0).isEnabled()) for(Component c : alarmDatePanel.getComponents()) c.setEnabled(false);
		    	else for(Component c : alarmDatePanel.getComponents()) c.setEnabled(true);
		    }
		});
		alarmCheckBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmPanel.add(alarmCheckBox);
		spring.putConstraint(SpringLayout.WEST, alarmCheckBox, 5, SpringLayout.EAST, label_4);

		contentPane.add(alarmPanel);
		
		//godzina
		alarmTimeLabel = new JLabel("Czas Przypomnienia");
		alarmTimeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.add(alarmTimeLabel);
		
		timeAlarmSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		timeAlarmSpinner.setMaximumSize(new Dimension(500,30));
		timeAlarmSpinner.setEditor(new JSpinner.DateEditor(timeAlarmSpinner, "HH:mm:ss"));
		alarmDatePanel.add(timeAlarmSpinner);
		
		//data
		alarmDateLabel = new JLabel("Data Przypomnienia");
		alarmDateLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.add(alarmDateLabel);
		
		dateAlarmSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		dateAlarmSpinner.setMaximumSize(new Dimension(500,30));
		dateAlarmSpinner.setEditor(new JSpinner.DateEditor(dateAlarmSpinner, "dd:MM:yyyy"));
		alarmDatePanel.add(dateAlarmSpinner);
		
		contentPane.add(alarmDatePanel);
		for(Component c : alarmDatePanel.getComponents()) c.setEnabled(false);
	}
	
	/**
	 * Metoda tworząca obiekt CalendarEvent z danych zebranych w blokach tego okna
	 * 
	 * @return obiekt klasy CalendarEvent
	 * @see CalendarEvent
	 */
	protected CalendarEvent makeEventOutOfTakenData()
	{
		String name = textFieldName.getText();
    	String place = textFieldPlace.getText();
    	LocalDate startDate = ((Date)startDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalTime startTime = ((Date)startTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    	LocalDateTime startOfEvent = LocalDateTime.of(startDate, startTime);
    	LocalDate endDate = ((Date)endDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalTime endTime = ((Date)endTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    	LocalDateTime endOfEvent = LocalDateTime.of(endDate, endTime);
    	LocalDateTime buzzer = null;
    	if(alarmCheckBox.isSelected())
    	{
    		LocalDate alarmDate = ((Date)dateAlarmSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    	LocalTime alarmTime = ((Date)timeAlarmSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	    	buzzer = LocalDateTime.of(alarmDate, alarmTime);
	    	
    	}
    	String description = textDescription.getText();
    	
    	return new CalendarEvent(name, place, startOfEvent, endOfEvent, description, buzzer);
	}
	
	/**
	 * Konstruktor okna będącego wzorcem dla pózniejszych okien AddEvent i EditEvent
	 * 
	 * @param calendarEventContext obiekt klasy CalendarEventContext, do którego metod będą odnosić się klasy potomne
	 * @param mainWindow referencja na obiekt okna głównego, które zostanie odświerzone w przypadku operacji na nim
	 * @see AddEvent
	 * @see EditEvent
	 */
	public Option(CalendarEventContext calendarEventContext, MainWindow mainWindow)
	{
		LocalDate date = LocalDate.of(mainWindow.getYear(), mainWindow.getMonth()+1, mainWindow.getDay());
		
		startDateSpinnerModel = new SpinnerDateModel(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), null, null, Calendar.YEAR);
		endDateSpinnerModel = new SpinnerDateModel(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), null, null, Calendar.YEAR);
		dateAlarmSpinnerModel = new SpinnerDateModel(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), null, null, Calendar.YEAR);
		startDateSpinner = new JSpinner(startDateSpinnerModel);
		endDateSpinner = new JSpinner(endDateSpinnerModel);
		dateAlarmSpinner = new JSpinner(dateAlarmSpinnerModel);
		
		setSize(300,600);
		this.calendarEventContext = calendarEventContext;
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		addName();
		addPlace();
		addDate("Data rozpoczęcia", this.startDateSpinner);
		addTime("Czas rozpoczęcia", this.startTimeSpinner);
		addDate("Data Zakończenia", this.endDateSpinner);
		addTime("Czas Zakończenia", this.endTimeSpinner);
		addDescription();
		addAlarm();
	}


}
