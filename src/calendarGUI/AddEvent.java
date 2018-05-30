package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		    	String plcae = textFieldPlace.getText();
		    	LocalDateTime startOfEvent = LocalDateTime.of()
		    	String description = textDescription.getText();
		    	calendarEventContext.addEvent(new())
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
