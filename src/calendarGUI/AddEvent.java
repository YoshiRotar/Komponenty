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
	AddEvent(CalendarEventContext calendarEventContext)
	{
		super(calendarEventContext);
		
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
		    	LocalDate startDate = LocalDate.ofInstant(((Date)startDateSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	LocalTime startTime = LocalTime.ofInstant(((Date)startTimeSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	LocalDateTime startOfEvent = LocalDateTime.of(startDate, startTime);
		    	LocalDate endDate = LocalDate.ofInstant(((Date)endDateSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	LocalTime endTime = LocalTime.ofInstant(((Date)endTimeSpinner.getValue()).toInstant(), ZoneId.systemDefault());
		    	LocalDateTime endOfEvent = LocalDateTime.of(endDate, endTime);
		    	String description = textDescription.getText();
		    	boolean ifAdded = calendarEventContext.addEvent(new CalendarEvent(name, place, startOfEvent, endOfEvent, description));
		    	if(ifAdded) JOptionPane.showMessageDialog(null,"Wydarzenie utworzone");
		    	else JOptionPane.showMessageDialog(null,"Nie udało się utworzyć wydarzenia","Błąd",JOptionPane.ERROR_MESSAGE);
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
