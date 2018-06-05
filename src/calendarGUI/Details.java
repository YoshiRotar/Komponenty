package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import calendardata.CalendarEvent;

/**
 * Klasa reprezentująca okno będące podsumowaniem danych dotyczących wydarzenia.
 * 
 * @author Paweł Młynarczyk
 * @author Mateusz Kuzniarek
 *
 */
@SuppressWarnings("serial")
public class Details extends JFrame
{
	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy H:mm"); 
	
	/**
	 * Konstrukor klasy mający na celu utworzenie okna, oraz elementów tekstowych opisujących dane wydarzenie.
	 * 
	 * @param calendarEvent opisywane wydarzenie
	 * @see CalendarEvent
	 */
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
		JLabel start = new JLabel("Rozpoczęcie: " + dateFormat.format(Date.from(calendarEvent.getStartOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
		start.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		JLabel end = new JLabel("Zakończenie: " + dateFormat.format(Date.from(calendarEvent.getEndOfEvent().atZone(ZoneId.systemDefault()).toInstant())));
		end.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		JLabel description = new JLabel("Opis: ");
		description.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		String text = calendarEvent.getDescription().replaceAll("\n", "<br>");
		JLabel descriptionText = new JLabel("<html>" + text + "</hrml>");
		descriptionText.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		JLabel alarm = new JLabel();
		if(calendarEvent.getBuzzer()==null) alarm.setText("Przypomnienie: Brak");
		else alarm.setText("Przypomnienie: " + dateFormat.format(Date.from(calendarEvent.getBuzzer().atZone(ZoneId.systemDefault()).toInstant())));
		alarm.setAlignmentX(JLabel.LEFT_ALIGNMENT);
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
		contentPane.add(alarm);
		contentPane.add(description);
		contentPane.add(scrollPane);
	}
}
	
