package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
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
public class EditEvent extends Option
{
	//tutaj jako argument trzeba dac reprezentacje eventu w warstwie logiki
	private void initValues(MainWindow mainWindow)
	{
		
		CalendarEvent calendarEvent = mainWindow.getSelectedEvent();
		textFieldName.setText(calendarEvent.getName());
		textFieldPlace.setText(calendarEvent.getPlace());
		textDescription.setText(calendarEvent.getDescription());
		startDateSpinner.setValue(Timestamp.valueOf(calendarEvent.getStartOfEvent()));
		endDateSpinner.setValue(Timestamp.valueOf(calendarEvent.getEndOfEvent()));
		startTimeSpinner.setValue(Timestamp.valueOf(calendarEvent.getStartOfEvent()));
		endTimeSpinner.setValue(Timestamp.valueOf(calendarEvent.getEndOfEvent()));
		
		if(calendarEvent.getBuzzer()!=null)
		{
			alarmCheckBox.setSelected(true);;
			timeAlarmSpinner.setValue(Timestamp.valueOf(calendarEvent.getBuzzer()));
			dateAlarmSpinner.setValue(Timestamp.valueOf(calendarEvent.getBuzzer()));
			timeAlarmSpinner.setEnabled(true);
			dateAlarmSpinner.setEnabled(true);
			alarmTimeLabel.setEnabled(true);
			alarmDateLabel.setEnabled(true);
		}
		
	}
	
	EditEvent(CalendarEventContext calendarEventContext, MainWindow mainWindow)
	{
		super(calendarEventContext, mainWindow);
		JPanel panel = new JPanel();
		panel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(500, 30));
		panel.setLayout(new FlowLayout());
		JButton add = new JButton("Edytuj");
		add.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
		    	CalendarEvent newEvent = makeEventOutOfTakenData();
		    	
		    	boolean ifAdded = calendarEventContext.editEvent(mainWindow.getSelectedEvent(), newEvent.getName(), newEvent.getPlace(), newEvent.getStartOfEvent(), newEvent.getEndOfEvent(), newEvent.getDescription(), newEvent.getBuzzer());
		    	if(ifAdded) JOptionPane.showMessageDialog(null,"Wydarzenie utworzone");
		    	else 
		    	{
		    		JOptionPane.showMessageDialog(null,"Nie udało się utworzyć wydarzenia","Błąd",JOptionPane.ERROR_MESSAGE);
		    		return;
		    	}

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
		

		initValues(mainWindow);
	}
}
