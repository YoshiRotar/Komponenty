package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import calendardata.CalendarEvent;
import calendarlogic.CalendarEventContext;

@SuppressWarnings("serial")
public class AddEvent extends Option
{
	
	AddEvent(CalendarEventContext calendarEventContext, MainWindow mainWindow)
	{
		super(calendarEventContext, mainWindow);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(500, 30));
		panel.setLayout(new FlowLayout());
		JButton add = new JButton("Dodaj");
		add.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
		    	String name = textFieldName.getText();
		    	String place = textFieldPlace.getText();
		    	/*
		    	LocalDateTime startDate = LocalDateTime.ofInstant(((Date)startDateSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	LocalDateTime startTime = LocalDateTime.ofInstant(((Date)startTimeSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	*/
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
		    	boolean ifAdded = calendarEventContext.addEvent(new CalendarEvent(name, place, startOfEvent, endOfEvent, description, buzzer));
		    	if(ifAdded) JOptionPane.showMessageDialog(null,"Wydarzenie utworzone");
		    	else JOptionPane.showMessageDialog(null,"Nie udało się utworzyć wydarzenia","Błąd",JOptionPane.ERROR_MESSAGE);
		    	mainWindow.printCalendar();
		    	mainWindow.printEvents();
				dispose();
		    }
		});
		JButton cancel = new JButton("Zaniechaj");
		cancel.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
				dispose();
		    }
		});
		panel.add(add);
		panel.add(cancel);
		
		contentPane.add(panel);
	}
}
