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

/**
 * Klasa reprezentująca okno, pośredniczące wprowadzaniu nowych wydarzeń do kalendarza, rozszerzająca możliwości klasy Option.
 * 
 * @author Paweł Młynarczyk
 * @author Mateusz Kuzniarek
 */
@SuppressWarnings("serial")
public class AddEvent extends Option
{
	/**
	 * Konstruktor mający na celu utworzenie okna, oraz jego elementów, wraz z handlerami do przycisków, 
	 * za pomocą których wywoływana jest metoda dodawania nowego wydarzenia do calendarEventContext, 
	 * na podstawie danych zebranych z pozostałych elementów okna.
	 * 
	 * @param calendarEventContext obiekt klasy CalendarEventContext, do którego metod odnosi się ta klasa
	 * @param mainWindow referencja na obiekt okna głównego, które zostanie odświerzone w przypadku dodania wydarzenia
	 * @see CalendarEventContext
	 */
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
