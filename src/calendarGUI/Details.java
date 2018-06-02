package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import calendardata.CalendarEvent;

@SuppressWarnings("serial")
public class Details extends JFrame
{


	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	Details(CalendarEvent calendarEvent)
	{
		setSize(300,300);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		JLabel name = new JLabel("Nazwa: " + calendarEvent.getName());
		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		JLabel place = new JLabel("Miejsce: " + calendarEvent.getPlace());
		place.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		JLabel start = new JLabel("Rozpoczêcie: " + calendarEvent.getStartOfEvent().toString());
		start.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		JLabel end = new JLabel("Zakoñczenie: " + calendarEvent.getEndOfEvent().toString());
		end.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		JLabel description = new JLabel("Opis: ");
		description.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		String text = calendarEvent.getDescription().replaceAll("\n", "<br>");
		JLabel descriptionText = new JLabel("<html>" + text + "</hrml>");
		descriptionText.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		JPanel scrollingPanel = new JPanel();
		scrollingPanel.setLayout(new BoxLayout(scrollingPanel, BoxLayout.Y_AXIS));
		scrollingPanel.add(descriptionText);
		scrollingPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		
		JScrollPane scrollPane = new JScrollPane(scrollingPanel);
		scrollPane.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(name);
		contentPane.add(place);
		contentPane.add(start);
		contentPane.add(end);
		contentPane.add(description);
		contentPane.add(scrollPane);
	}
}
	
