package calendarGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import calendardata.CalendarEvent;
import calendarlogic.AlarmListener;
import calendarlogic.CalendarEventContext;
import calendarlogic.DatabaseProvider;
import calendarlogic.StyleContext;
import calendarlogic.ToIcsConverter;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;

/**
 * 
 * Klasa będąca graficzną interpretacją głównego okna interfejsu użytkownika.
 * 
 * @author Paweł Młynarczyk
 * @author Mateusz Kuzniarek
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements AlarmListener
{
	
	private final CalendarEventContext calendarEventContext = new CalendarEventContext();
	private StyleContext style = new StyleContext();
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private JPanel contentPane;
	private JPanel eventsPanel;
	private JLabel eventsLabel = new JLabel();
	private JPanel scrollingEventPanel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(scrollingEventPanel);
	private JLabel date = new JLabel("", SwingConstants.CENTER);
	private JButton[] days = new JButton[42];
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JButton buttonSelected;
	private int selectedMonth;
	private int selectedYear;
	private JButton selectedEventButton;
	private CalendarEvent selectedEvent;
	private JButton newEvent;
	private JButton editEvent;
	private JButton deleteEvent;
	JButton toCurrentDay;
	private JLabel[] weekDayLabels = new JLabel[7];
	
	/**
	 * Metoda zwracająca referencję na aktualnie wybrane wydarzenie.
	 * 
	 * @return referencję na to wydarzenie
	 */
	public CalendarEvent getSelectedEvent()
	{
		return selectedEvent;
	}

	/**
	 * Metoda ustawiająca wartość referencji na akutalnie wybrane wydarzenie.
	 * 
	 * @param selectedEvent referencja na to wydarzenie
	 */
	public void setSelectedEvent(CalendarEvent selectedEvent) 
	{
		this.selectedEvent = selectedEvent;
	}
	
	/**
	 * Metoda zwracająca referencję na pole calendarEventContext.
	 * 
	 * @return referencja na to pole
	 * @see CalendarEventContext
	 */
	public CalendarEventContext getCalendarEventContext()
	{
		return calendarEventContext;
	}
	
	/**
	 * Metoda zwracająca referencję na pole styleContext.
	 * 
	 * @return referencja na to pole
	 * @see StyleContext
	 */
	public StyleContext getStyleContext()
	{
		return style;
	}
	
	/**
	 * Metoda zwracająca aktualnie wybrany miesiąc.
	 * 
	 * @return wartość liczbowa z przedziału [0 - 11] odpowiadająca kolejnym miesiącom
	 */
	public int getMonth() 
	{
		return month;
	}
	
	/**
	 * Metoda ustawiająca aktualnie wybrany miesiąc.
	 * 
	 * @param month wartość liczbowa z przedziału [0 - 11] odpowiadająca kolejnym miesiącom
	 */
	public void setMonth(int month) 
	{
		this.month = month;
	}
	
	/**
	 * Metoda zwracająca aktualnie wybrany rok.
	 * 
	 * @return wartość liczbowa reprezentująca rok
	 */
	public int getYear() 
	{
		return year;
	}
	
	/**
	 * Metoda ustawiająca aktualnie wybrany rok.
	 * 
	 * @param year wartość liczbowa reprezentująca rok
	 */
	public void setYear(int year) 
	{
		this.year = year;
	}
	
	/**
	 * Metoda, która na podstawie pola buttonSelected określa wybrany przez użytkownika dzień.
	 * 
	 * @return wartość liczbowa reprezentująca dzień
	 */
	public int getDay()
	{
		if(buttonSelected==null) return 1;
		return Integer.parseInt(this.buttonSelected.getText());
	}

	/**
	 * Metoda główna, rozpoczynająca działanie programu, tworząca obiekt klasy MainWindow.
	 * 
	 * @param args tablica parametrów uruchomieniowych (nieużywana)
	 */
	public static void main(String[] args) 
	{
		MainWindow frame = new MainWindow();
		frame.printCalendar();
		frame.setVisible(true);
	}	
	
	/**
	 * Metoda wyświetlająca na panelu bocznym prawym, wszystkie wydarzenia z dnia wyliczonego na podstawie pola buttonSelected, w formacie:<br> Nazwa,<br> Miejsce,<br> Data rozpoczęcia.<br>
	 * Istnieje możliwość wyboru wydarzenia z listy i przypisania go do pola selectedEvent celem wykonywania na nim dalszych operacji.
	 * Po dwukrotnym kliknięciu na wydarzenie zostanie wyświetlone okno szczegółowe.
	 * 
	 * @see Details
	 */
	public void printEvents()
	{
		if(buttonSelected==null) return;
		selectedEventButton = null;
		selectedEvent = null;
		editEvent.setEnabled(false);
		deleteEvent.setEnabled(false);
		eventsLabel.setText("Wydarzenia z dnia: "+ buttonSelected.getText() + " " +date.getText());
		int day = Integer.parseInt(buttonSelected.getText());
		
		scrollingEventPanel.removeAll();
		for(CalendarEvent calendarEvent : calendarEventContext.getEventsFromCertainDay(LocalDate.of(selectedYear, selectedMonth+1, day)))
		{
			String text = "<html>Nazwa: " + calendarEvent.getName() +"<br>" +
						  "Miejsce: " + calendarEvent.getPlace()+ "<br>" +
						  "Czas: " +  calendarEvent.getStartOfEvent().getHour() + ":" +
						  String.format("%02d", calendarEvent.getStartOfEvent().getMinute())  + "<br>" + "</html>";
 			JButton button = new JButton(text);
 			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setPreferredSize(new Dimension(370,60));
			button.setBackground(Color.WHITE);
			button.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent ae) 
				{
					if(selectedEventButton!=null) selectedEventButton.setBackground(Color.WHITE);
					selectedEventButton = (JButton) ae.getSource();
					selectedEventButton.setBackground(Color.LIGHT_GRAY);
					selectedEvent = calendarEvent;
					editEvent.setEnabled(true);
					deleteEvent.setEnabled(true);
				}
			});
			
			button.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mousePressed(MouseEvent e) 
				{
					if(e.getClickCount()==2)
					{
						@SuppressWarnings("unused")
						Details d = new Details(selectedEvent);
					}
				}
				
			});
			scrollingEventPanel.add(button, null);
		}

		scrollingEventPanel.repaint();
	}
	
	/**
	 * Metoda inicjalizująca główny panel kalendarza.
	 * 
	 * @param parent panel w którym zostanie zainicjalizowany kalendarz
	 */
	public void initCalendarPanel(JPanel parent)
	{
		 String[] weekDays = {"Pon", "Wt", "Sr", "Czw", "Pt", "So", "N"};
		 JPanel dayPanel = new JPanel(new GridLayout(7, 7));
		 for(int i=0; i<7; i++)
		 {
			 weekDayLabels[i] = new JLabel(weekDays[i], SwingConstants.CENTER);
			 weekDayLabels[i].setFont(new Font(style.getStyle().getFont(), Font.PLAIN, 20));
			 dayPanel.add(weekDayLabels[i]);
		 }
		 for (int i = 0; i<days.length; i++) 
		 {
			 days[i] = new JButton();
			 days[i].setPreferredSize(new Dimension(120, 50));
			 days[i].setFocusPainted(false);
			 days[i].setBackground(Color.WHITE);
			 days[i].addActionListener(new ActionListener() 
			 {
				public void actionPerformed(ActionEvent ae) 
				{
					if(((JButton)ae.getSource()).getText()!="")
					{
						if(buttonSelected!=null) buttonSelected.setBackground(Color.WHITE);
						buttonSelected = (JButton)ae.getSource();
						selectedMonth = month;
						selectedYear = year;
						printCalendar();
						printEvents();
					}
					
				}
			 });
			 dayPanel.add(days[i]);
		 }
		 JPanel monthPanel = new JPanel(new GridLayout(1, 3));
		 JButton previous = new JButton(" << ");
		 previous.addActionListener(new ActionListener() 
		 {
			 public void actionPerformed(ActionEvent ae) 
			 {
				 if(month != 0)
				 {
					 month-- ;
				 }
				 else
				 {
					 month = 11;
					 year--;
				 }
				 printCalendar();
			 }
		 });
		 
		 JButton next = new JButton(" >> ");
		 next.addActionListener(new ActionListener() 
		 {
			 public void actionPerformed(ActionEvent ae) 
			 {
				 if(month != 11)
				 {
					 month++ ;
				 }
				 else
				 {
					 month = 0;
					 year++;
				 }
				 printCalendar();
			 }
		 });
		 
		 monthPanel.add(previous);
		 monthPanel.add(date);
		 monthPanel.add(next);
		 printCalendar();

		 parent.add(dayPanel, BorderLayout.CENTER);
		 parent.add(monthPanel, BorderLayout.SOUTH);
	 }
	 
	/**
	 * Metoda wyświetlająca kalendarz zgodnie z formatowaniem zawartym w obiekcie klasy StyleContext, oraz zaznaczająca na nim terminy trwania wydarzeń.
	 */
	 public void printCalendar()
	 {
		 for(int i=0; i<7; i++) weekDayLabels[i].setFont(new Font(style.getStyle().getFont(), Font.PLAIN, 30));
		 for(int i=0; i<days.length; i++)
		 {
			 days[i].setText("");
			 days[i].setFont(new Font(style.getStyle().getFont(), Font.PLAIN, 30));
			 days[i].setBackground(style.getStyle().getBackgroundColor());
			 days[i].setForeground(style.getStyle().getFontColor());
		 }
		 Calendar calendar = Calendar.getInstance();
		 LocalDate ref;
		 calendar.set(year, month, 1);
		 SimpleDateFormat format = new SimpleDateFormat("MM, YYYY");
		 int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		 dayOfWeek--;
		 if(dayOfWeek==0) dayOfWeek = 7;
		 int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		 
		 for(int i=0; i<days.length; i++) days[i].setBackground(style.getStyle().getBackgroundColor());
		 for(int i=0; i<daysInMonth; i++)
		 {
			 ref = LocalDate.of(this.year, this.month+1, i+1);
			 days[i+dayOfWeek-1].setText(Integer.toString(i+1));
			 if(!this.calendarEventContext.getEventsFromCertainDay(ref).isEmpty()) days[i+dayOfWeek-1].setBackground(new Color(51, 204, 51));
			 else if(this.calendarEventContext.getCalendarEvents().lower(new CalendarEvent(null, null, LocalDateTime.of(ref, LocalTime.MIDNIGHT), null, null, null)) != null && this.calendarEventContext.getCalendarEvents().lower(new CalendarEvent(null, null, LocalDateTime.of(ref, LocalTime.MIDNIGHT), null, null, null)).getEndOfEvent().isAfter(LocalDateTime.of(ref, LocalTime.MIDNIGHT))) days[i+dayOfWeek-1].setBackground(new Color(255, 128, 128));
		 }
		 date.setText(format.format(calendar.getTime()));
		 date.setFont(new Font(style.getStyle().getFont(), Font.PLAIN, 20));
		 if(buttonSelected!=null && month==selectedMonth && year==selectedYear) buttonSelected.setBackground(Color.LIGHT_GRAY);
	 }
	
	 /**
	  * Konstruktor klasy MainWindow, któego zadaniem jest wyświetlenie wszystkich elementów na ekranie, oraz przypisanie zdarzeń do tych elementów wywołujących inne okna.
	  * 
	  * @see About
	  * @see AddEvent
	  * @see EditEvent
	  * @see EraseOlderThan
	  * @see Settings
	  */
	public MainWindow() 
	{
		
		super("Turbo Calendar 2.0.11");
		MainWindow thisWindow = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280,720);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		
		calendarEventContext.initAlarms(this);
		
		this.addWindowListener( new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
			  calendarEventContext.encodeToXml();
			  style.encodeToXml(); 
		      dispose();
		   }
		});
		
		this.setFocusable(true);
		this.addKeyListener( new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) 
			{

		    	System.out.println("ccc");
			    if(e.getKeyCode() == KeyEvent.VK_F1)
			    {
			    	System.out.println("ggg");
			    	AddEvent add = new AddEvent(calendarEventContext, thisWindow);
			    }
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		calendarEventContext.decodeFromXml();
		style.decodeFromXml();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		
		JMenuItem mntmZapiszDoBazdy = new JMenuItem("Zapisz do bazy danych");
		mntmZapiszDoBazdy.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				DatabaseProvider databaseProvider = new DatabaseProvider();
				databaseProvider.writeIntoDatabase(calendarEventContext);
			}
		});
		mnPlik.add(mntmZapiszDoBazdy);
		
		JMenuItem mntmWczytajZBazy = new JMenuItem("Wczytaj z bazy danych");
		mntmWczytajZBazy.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				DatabaseProvider databaseProvider = new DatabaseProvider();
				databaseProvider.readFromDatabase(calendarEventContext);
				printCalendar();
			}
		});
		mnPlik.add(mntmWczytajZBazy);
		
		JMenuItem mntmKonwertujDo = new JMenuItem("Konwertuj do formatu standardowego");
		mntmKonwertujDo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser fileChooser = new JFileChooser();
				contentPane.add(fileChooser);
				fileChooser.showOpenDialog(null);
				if(fileChooser.getSelectedFile() != null)
				{
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					path += ".ics";
					ToIcsConverter.convert(calendarEventContext, path);
				}
			}
		});
		mnPlik.add(mntmKonwertujDo);
		
		JMenu mnNewMenu = new JMenu("Edycja");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmUsuWydarzeniaStarsza = new JMenuItem("Usuń wydarzenia starsze od...");
		mntmUsuWydarzeniaStarsza.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				EraseOlderThan eraseOlderThan = new EraseOlderThan(thisWindow);
			}
		});
		mnNewMenu.add(mntmUsuWydarzeniaStarsza);
		
		JMenuItem mntmUstawienia = new JMenuItem("Ustawienia");
		mntmUstawienia.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				Settings settings = new Settings(thisWindow);
			}
		});
		mnNewMenu.add(mntmUstawienia);
		
		JMenu mnPomoc = new JMenu("Pomoc");
		menuBar.add(mnPomoc);
		
		JMenuItem mntmOProgramie = new JMenuItem("O programie");
		mntmOProgramie.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				About about = new About();
				
			}
		});
		mnPomoc.add(mntmOProgramie);
		
		
		
		
		//Wydarzenia
		eventsPanel = new JPanel();
		eventsPanel.setPreferredSize(new Dimension(400,100));

		eventsLabel.setText("Wydarzenia:");
		eventsPanel.add(eventsLabel);
		scrollPane.setPreferredSize(new Dimension(400,500));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollingEventPanel.setLayout(new BoxLayout(scrollingEventPanel, BoxLayout.Y_AXIS));
		eventsPanel.add(scrollPane);
		contentPane.add(eventsPanel, BorderLayout.EAST);
		
		
		//Kalendarz
		JPanel calPanel = new JPanel();
		calPanel.setLayout(new BorderLayout());
		contentPane.add(calPanel, BorderLayout.WEST);
		initCalendarPanel(calPanel);
		
		//Opcje
		Dimension buttonSize = new Dimension(50,50);
		JPanel options = new JPanel();
		contentPane.add(options, BorderLayout.NORTH);
		options.setPreferredSize(new Dimension(100,55));
		
		newEvent = new JButton("Dodaj Wydarzenie");
		
		newEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				AddEvent add = new AddEvent(calendarEventContext, thisWindow);
			}
		});
		newEvent.setPreferredSize(buttonSize);
		

		deleteEvent = new JButton("Usuń Wydarzenie");
		deleteEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				selectedEventButton = null;
				calendarEventContext.deleteEvent(selectedEvent);
				selectedEvent = null;
				printCalendar();
				printEvents();
			}
		});
		deleteEvent.setPreferredSize(buttonSize);
		
		editEvent = new JButton("Edytuj Wydarzenie");
		editEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				EditEvent add = new EditEvent(calendarEventContext, thisWindow);
			}
		});
		editEvent.setPreferredSize(buttonSize);
		
		toCurrentDay = new JButton("Bieżący Dzień");
		toCurrentDay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				month = Calendar.getInstance().get(Calendar.MONTH);
				year = Calendar.getInstance().get(Calendar.YEAR);
				Integer day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				printCalendar();
				for(JButton button : days)
				{
					if(button.getText().equals(day.toString())) 
					{
						buttonSelected=button;
						selectedMonth=month;
						selectedYear=year;
					}
				}
				printCalendar();
				printEvents();
			}
		});
		toCurrentDay.setPreferredSize(buttonSize);
		options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
		
		options.add(newEvent);
		options.add(deleteEvent);
		options.add(editEvent);
		options.add(toCurrentDay);
		
		editEvent.setEnabled(false);
		deleteEvent.setEnabled(false);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0,50));
		contentPane.add(panel, BorderLayout.SOUTH);
		
	}

	/**
	 * Metoda interfejsu AlarmListener, zdefiniowana jako uchwyt dla wydarzenia wystąpienia alarmu, w przypadku którego tworzony jest nowe okno AlarmWindow.
	 * 
	 * @param message wiadomość wyświetlona w oknie AlarmWindow
	 * @see AlarmWindow
	 */
	@Override
	public void onAlarm(String message) 
	{
		@SuppressWarnings("unused")
		AlarmWindow alarmWindow = new AlarmWindow(message, style.getStyle().getAlarmPath());
	}

}
