package calendarGUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.EmptyBorder;

import calendarlogic.CalendarEventContext;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class EraseOlderThan extends JFrame
{
	CalendarEventContext calendarEventContext;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel contentPane;
	private SpinnerModel dateSpinnerModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
	private JSpinner dateSpinner = new JSpinner(dateSpinnerModel);

	public EraseOlderThan(MainWindow source)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Usuń starsze od...");
		setBounds(100, 100, 300, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		this.calendarEventContext = source.getCalendarEventContext();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(300,50));
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		dateSpinner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		dateSpinner.setMaximumSize(new Dimension(150,50));
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd:MM:yyyy"));
		panel.add(dateSpinner);
		
		JPanel panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(300,30));
		contentPane.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnUsuStarszeOd = new JButton("Usuń starsze od...");
		btnUsuStarszeOd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				String[] options = { "Tak", "Niekoniecznie" };
	            int answer = JOptionPane.showOptionDialog(null, "Jesteś pewien?","WARNING", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
	            if(answer == 0)
	            {
	            	LocalDate referenceDate = ((Date)dateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            	int counter = calendarEventContext.eraseOlderThan(referenceDate);
	            	JOptionPane.showMessageDialog(null,"Usunięto " +  counter + " wpisów");
	            	source.printCalendar();
	            	source.printEvents();
	            	dispose();
	            }
			}
		});
		panel_1.add(btnUsuStarszeOd);
		
		JButton btnAnuluj = new JButton("Zaniechaj");
		btnAnuluj.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				dispose();
			}
		});
		panel_1.add(btnAnuluj);
		
		setVisible(true);
	}

}
