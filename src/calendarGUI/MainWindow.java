package calendarGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
public class MainWindow extends JFrame {
	
	private int month = Calendar.getInstance().get(Calendar.MONTH);
	private int year = Calendar.getInstance().get(Calendar.YEAR);;
	private int currentDay = 0;
	private JPanel contentPane;
	JScrollPane scrollPane;
	private JLabel date = new JLabel("", SwingConstants.CENTER);
	private JButton[] days = new JButton[42];
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JButton buttonSelected;
	
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
	
	public int getCurrentDay() 
	{
		return currentDay;
	}
	
	public void setCurrentDay(int currentDay) 
	{
		this.currentDay = currentDay;
	}

	public static void main(String[] args) 
	{
		MainWindow frame = new MainWindow();
		frame.setVisible(true);
		LocalDateTime dateTime = LocalDateTime.of(2015, 2, 13, 15, 30);
		CalendarEventContext con = new CalendarEventContext();
		con.addEvent(new CalendarEvent("Wspaniały Event", dateTime, dateTime.plusDays(10), "Najlepszy Event"));
		con.addEvent(new CalendarEvent("Najgorszy Event", dateTime.plusDays(30), dateTime.plusDays(50), "Suabe"));
		con.addEvent(new CalendarEvent("Taki Se Event", dateTime.plusDays(60), dateTime.plusDays(100), "Hue Hue"));
		DatabaseProvider dp = new DatabaseProvider();
		dp.writeIntoDatabase(con);
		dp.readFromDatabase(con);
		for (CalendarEvent c : con.getCalendarEvents())
		{
			System.out.println("N:" + c.getName() + " S:" + c.getStartOfEvent().toString() + " E:" + c.getEndOfEvent().toString() + " D:" + c.getDescription());
		}
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
			 days[i].setBackground(Color.white);
			 days[i].addActionListener(new ActionListener() 
			 {
				public void actionPerformed(ActionEvent ae) 
				{
					if(((JButton)ae.getSource()).getText()!="")
					{
						int currentDay = Integer.parseInt(((JButton)ae.getSource()).getText());
						scrollPane.setViewportView(new JLabel(currentDay+", "+date.getText(), SwingConstants.CENTER));
						if(buttonSelected!=null) buttonSelected.setBackground(Color.WHITE);
						buttonSelected = (JButton)ae.getSource();
						printCalendar();
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
				 month--;
				 printCalendar();
			 }
		 });
		 
		 JButton next = new JButton(" >> ");
		 next.addActionListener(new ActionListener() 
		 {
			 public void actionPerformed(ActionEvent ae) 
			 {
				 month++;
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
		 for(int i=0; i<daysInMonth; i++)
		 {
			 days[i+dayOfWeek-1].setText(Integer.toString(i+1));
		 }
		 date.setText(format.format(calendar.getTime()));
		 if(buttonSelected!=null) buttonSelected.setBackground(Color.LIGHT_GRAY);
	 }
	
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		super("Turbo Calendar 0.3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280,720);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		
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
		mnPlik.add(mntmZapiszDoBazdy);
		
		JMenuItem mntmZapiszDoXml = new JMenuItem("Zapisz do XML");
		mnPlik.add(mntmZapiszDoXml);
		
		JMenuItem mntmWczytajZBazy = new JMenuItem("Wczytaj z bazy danych");
		mnPlik.add(mntmWczytajZBazy);
		
		JMenuItem mntmWczytajZXml = new JMenuItem("Wczytaj z XML");
		mnPlik.add(mntmWczytajZXml);
		
		JMenuItem mntmKonwertujDo = new JMenuItem("Konwertuj do formatu standardowego");
		mnPlik.add(mntmKonwertujDo);
		
		JMenu mnNewMenu = new JMenu("Edycja");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmUsuWydarzeniaStarsza = new JMenuItem("Usu\u0144 wydarzenia starsze od...");
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
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400,100));
		contentPane.add(scrollPane, BorderLayout.EAST);
		scrollPane.setViewportView(new JLabel("Wydarzenia: ", SwingConstants.CENTER));
		
		
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
				AddEvent add = new AddEvent();
				
			}
		});
		newEvent.setPreferredSize(buttonSize);
		
		JButton deleteEvent = new JButton("Usu� Wydarzenie");
		deleteEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				//
				
			}
		});
		deleteEvent.setPreferredSize(buttonSize);
		
		JButton editEvent = new JButton("Edytuj Wydarzenie");
		editEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				@SuppressWarnings("unused")
				EditEvent add = new EditEvent();
				
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
				currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				printCalendar();
				scrollPane.setViewportView(new JLabel(currentDay+", "+date.getText(), SwingConstants.CENTER));
			}
		});
		toCurrentDay.setPreferredSize(buttonSize);
		options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
		
		options.add(newEvent);
		options.add(deleteEvent);
		options.add(editEvent);
		options.add(toCurrentDay);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0,50));
		contentPane.add(panel, BorderLayout.SOUTH);
		
	}

}
