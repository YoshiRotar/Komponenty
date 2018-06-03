package calendarGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import calendardata.CalendarEvent;
import calendarlogic.CalendarEventContext;
import calendarlogic.DatabaseProvider;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class MainWindow extends JFrame 
{
	
	private final CalendarEventContext calendarEventContext = new CalendarEventContext();
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
	private JButton editEvent;
	private JButton deleteEvent;
	
	public CalendarEventContext getCalendarEventContext()
	{
		return calendarEventContext;
	}
	
	public int getMonth() 
	{
		return month;
	}
	
	public void setMonth(int month) 
	{
		this.month = month;
	}
	
	public int getYear() 
	{
		return year;
	}
	
	public void setYear(int year) 
	{
		this.year = year;
	}
	
	public int getDay()
	{
		if(buttonSelected==null) return 1;
		return Integer.parseInt(this.buttonSelected.getText());
	}

	public static void main(String[] args) 
	{
		MainWindow frame = new MainWindow();
		frame.printCalendar();
		frame.setVisible(true);
	}	
	
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
			/*
			String text = "<html>Nazwa: " + calendarEvent.getName() +"<br>" +"Miejsce: " + calendarEvent.getPlace()+ "<br>" +
						  "Czas: " +  calendarEvent.getStartOfEvent().getHour() + ":" + calendarEvent.getStartOfEvent().getMinute() + "<br>" +
						  "Zako?czenie; " + calendarEvent.getEndOfEvent().toString() + "<br>" +
						  "Opis: " + calendarEvent.getDescription() + "</html>";
						  */
			String text = "<html>Nazwa: " + calendarEvent.getName() +"<br>" +
						  "Miejsce: " + calendarEvent.getPlace()+ "<br>" +
						  "Czas: " +  calendarEvent.getStartOfEvent().getHour() + ":" +
						  calendarEvent.getStartOfEvent().getMinute() + "<br>" + "</html>";
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
	
	public void initCalendarPanel(JPanel parent)
	{
		 String[] weekDays = {"Pon", "Wt", "Sr", "Czw", "Pt", "So", "N"};
		 JPanel dayPanel = new JPanel(new GridLayout(7, 7));
		 for(int i=0; i<7; i++)
		 {
			 JLabel weekday = new JLabel(weekDays[i], SwingConstants.CENTER);
			 dayPanel.add(weekday);
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
	 
	 public void printCalendar()
	 {
		 for(int i=0; i<days.length; i++) days[i].setText("");
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(year, month, 1);
		 SimpleDateFormat format = new SimpleDateFormat("MM, YYYY");
		 int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		 dayOfWeek--;
		 if(dayOfWeek==0) dayOfWeek = 7;
		 int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		 
		 //na razie tylko tak prowizorycznie, fajnie byloby to dac w jedna petle
		 for(int i=0; i<days.length; i++) days[i].setBackground(Color.WHITE);
		 for(int i=0; i<daysInMonth; i++)
		 {
			 days[i+dayOfWeek-1].setText(Integer.toString(i+1));
			 if(!this.calendarEventContext.getEventsFromCertainDay(LocalDate.of(this.year, this.month+1, i+1)).isEmpty()) days[i+dayOfWeek-1].setBackground(Color.GREEN);
		 }
		 date.setText(format.format(calendar.getTime()));
		 if(buttonSelected!=null && month==selectedMonth && year==selectedYear) buttonSelected.setBackground(Color.LIGHT_GRAY);
	 }
	
	public MainWindow() 
	{
		
		super("Turbo Calendar 0.3");
		MainWindow thisWindow = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280,720);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		
		this.addWindowListener( new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
		      calendarEventContext.encodeToXml();
		      dispose();
		   }
		});
		
		this.addWindowListener( new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
		      calendarEventContext.encodeToXml();
		      dispose();
		   }
		});
		
		calendarEventContext.decodeFromXml();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		JMenuItem mntmNowy = new JMenuItem("Nowy");
		mnPlik.add(mntmNowy);
		
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
		
		JMenuItem mntmZapiszDoXml = new JMenuItem("Zapisz do XML");
		mnPlik.add(mntmZapiszDoXml);
		
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
		
		JMenuItem mntmWczytajZXml = new JMenuItem("Wczytaj z XML");
		mnPlik.add(mntmWczytajZXml);
		
		JMenuItem mntmKonwertujDo = new JMenuItem("Konwertuj do formatu standardowego");
		mnPlik.add(mntmKonwertujDo);
		
		JMenu mnNewMenu = new JMenu("Edycja");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmUsuWydarzeniaStarsza = new JMenuItem("Usu� wydarzenia starsze od...");
		mntmUsuWydarzeniaStarsza.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				EraseOlderThan eraseOlderThan = new EraseOlderThan(thisWindow);
			}
		});
		mnNewMenu.add(mntmUsuWydarzeniaStarsza);
		
		JMenuItem mntmFiltruj = new JMenuItem("Filtruj");
		mnNewMenu.add(mntmFiltruj);
		
		JMenuItem mntmUstawienia = new JMenuItem("Ustawienia");
		mntmUstawienia.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				Settings settings = new Settings();
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
		JButton newEvent = new JButton("Dodaj Wydarzenie");
		
		
		newEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				AddEvent add = new AddEvent(calendarEventContext, thisWindow);
			}
		});
		newEvent.setPreferredSize(buttonSize);
		

		deleteEvent = new JButton("Usu� Wydarzenie");
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
		
		JButton toCurrentDay = new JButton("Bie��cy Dzie�");
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

}
