package calendarGUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class Option extends JFrame
{

	protected JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	protected JTextField textFieldName;
	protected JTextField textFieldPlace;
	protected JTextArea textDescription;
	protected JSpinner timeSpinner;
	protected JSpinner dateSpinner;
	protected JSpinner dateAlarmSpinner;
	protected JSpinner timeAlarmSpinner;
	

	protected void addName()
	{
		//nazwa
		JLabel label = new JLabel("Nazwa Wydarzenia:");
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label);
		
		textFieldName = new JTextField();
		textFieldName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		textFieldName.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldName);
	}
	
	protected void addPlace()
	{
		//miejsce
		JLabel label_1 = new JLabel("Miejsce Wydarzenia:");
		label_1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_1);
		
		textFieldPlace = new JTextField();
		textFieldPlace.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		textFieldPlace.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldPlace);
	}
	
	protected void addDescription()
	{
		//opis
		JLabel label_2 = new JLabel("Opis Wydarzenia:");
		label_2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_2);
		
		textDescription = new JTextArea();
		
		
		JScrollPane areaScrollPane = new JScrollPane(textDescription);
		areaScrollPane.setAlignmentX(JLabel.LEFT_ALIGNMENT);	
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setMaximumSize(new Dimension(500, 200));
		areaScrollPane.setMinimumSize(new Dimension(500, 200));	
		contentPane.add(areaScrollPane);
	}
	
	protected void addTime()
	{
		//godzina
		JLabel label_3 = new JLabel("Data Wydarzenia");
		label_3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_3);
		
		SpinnerModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
		timeSpinner = new JSpinner(model);
		timeSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		timeSpinner.setMaximumSize(new Dimension(500,30));
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm:ss"));
		contentPane.add(timeSpinner);
	}
	
	protected void addDate()
	{
		//data
		JLabel label_5 = new JLabel("Czas Wydarzenia");
		label_5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		contentPane.add(label_5);
		
		SpinnerModel dateModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.YEAR);
		dateSpinner = new JSpinner(dateModel);
		dateSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		dateSpinner.setMaximumSize(new Dimension(500,30));
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy:MM:dd"));
		contentPane.add(dateSpinner);
	}
	
	protected void addAlarm()
	{
		//przypomnienie
		JPanel alarmPanel = new JPanel();
		alarmPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		SpringLayout spring = new SpringLayout();
		alarmPanel.setLayout(spring);
		alarmPanel.setMaximumSize(new Dimension(500,30));
		
		JPanel alarmDatePanel = new JPanel();
		alarmDatePanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.setLayout(new BoxLayout(alarmDatePanel, BoxLayout.Y_AXIS));
		alarmDatePanel.setMaximumSize(new Dimension(500,90));
		
		JLabel label_4 = new JLabel("Przypomnienie:");
		label_4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmPanel.add(label_4);
		
		JCheckBox alarmCheckBox = new JCheckBox();
		alarmCheckBox.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
		    	if(alarmDatePanel.getComponent(0).isEnabled()) for(Component c : alarmDatePanel.getComponents()) c.setEnabled(false);
		    	else for(Component c : alarmDatePanel.getComponents()) c.setEnabled(true);
		    }
		});
		alarmCheckBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmPanel.add(alarmCheckBox);
		spring.putConstraint(SpringLayout.WEST, alarmCheckBox, 5, SpringLayout.EAST, label_4);

		contentPane.add(alarmPanel);
		
		//godzina
		JLabel alarmTimeLabel = new JLabel("Data Przypomnienia");
		alarmTimeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.add(alarmTimeLabel);
		
		SpinnerModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
		timeAlarmSpinner = new JSpinner(model);
		timeAlarmSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		timeAlarmSpinner.setMaximumSize(new Dimension(500,30));
		timeAlarmSpinner.setEditor(new JSpinner.DateEditor(timeAlarmSpinner, "HH:mm:ss"));
		alarmDatePanel.add(timeAlarmSpinner);
		
		//data
		JLabel alarmDateLabel = new JLabel("Czas Przypomnienia");
		alarmDateLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		alarmDatePanel.add(alarmDateLabel);
		
		SpinnerModel dateModel = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.YEAR);
		dateAlarmSpinner = new JSpinner(dateModel);
		dateAlarmSpinner.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		dateAlarmSpinner.setMaximumSize(new Dimension(500,30));
		dateAlarmSpinner.setEditor(new JSpinner.DateEditor(dateAlarmSpinner, "yyyy:MM:dd"));
		alarmDatePanel.add(dateAlarmSpinner);
		
		contentPane.add(alarmDatePanel);
		for(Component c : alarmDatePanel.getComponents()) c.setEnabled(false);
	}
	
	public Option()
	{
		setSize(300,500);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		addName();
		addPlace();
		addDate();
		addTime();
		addDescription();
		addAlarm();
	}


}
