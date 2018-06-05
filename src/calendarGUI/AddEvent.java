package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		    	CalendarEvent newEvent = makeEventOutOfTakenData();
		    	boolean ifAdded = calendarEventContext.addEvent(newEvent);
		    	if(ifAdded) JOptionPane.showMessageDialog(null,"Wydarzenie utworzone");
		    	else 
		    	{
		    		JOptionPane.showMessageDialog(null,"Nie udało się utworzyć wydarzenia","Błąd",JOptionPane.ERROR_MESSAGE);
		    		return;
		    	}

	    		mainWindow.getCalendarEventContext().initAlarm(mainWindow, newEvent);
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
