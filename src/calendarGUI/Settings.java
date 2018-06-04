package calendarGUI;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Settings extends JFrame 
{

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel contentPane;
	
	Settings(MainWindow mainWindow)
	{
		setSize(275,175);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		JPanel stylePanel = new JPanel(new BorderLayout());
		JLabel style = new JLabel("Styl: ");
		style.setPreferredSize(new Dimension(100,50));
		JRadioButton dark = new JRadioButton("Dark");
		JRadioButton light = new JRadioButton("Light");
		ButtonGroup styleGroup = new ButtonGroup();
		styleGroup.add(light);
		styleGroup.add(dark);
		stylePanel.add(style, BorderLayout.WEST);
		JPanel styleOptions = new JPanel();
		styleOptions.setLayout(new BoxLayout(styleOptions, BoxLayout.X_AXIS));
		styleOptions.setPreferredSize(new Dimension(150,50));
		styleOptions.add(dark);
		styleOptions.add(light);
		stylePanel.add(styleOptions, BorderLayout.EAST);
		if(mainWindow.getStyleContext().getStyle().getBackgroundColor().equals(Color.WHITE)) light.setSelected(true);
		else dark.setSelected(true);
		contentPane.add(stylePanel);
		
		JPanel languagePanel = new JPanel(new BorderLayout());
		JLabel lang = new JLabel("Czcionka: ");
		lang.setPreferredSize(new Dimension(100,50));
		languagePanel.add(lang, BorderLayout.WEST);
		JPanel langOptions = new JPanel();
		langOptions.setPreferredSize(new Dimension(150,50));
		langOptions.setLayout(new BoxLayout(langOptions, BoxLayout.X_AXIS));
		languagePanel.add(langOptions, BorderLayout.EAST);
		
		Choice fontChoice = new Choice();
		fontChoice.setMaximumSize(new Dimension(100, 50));
		fontChoice.add("Arial");
		fontChoice.add("Gothic");
		fontChoice.select(mainWindow.getStyleContext().getStyle().getFont());
		langOptions.add(fontChoice);
		
		contentPane.add(languagePanel);
		
		JPanel pathPanel = new JPanel(new BorderLayout());
		pathPanel.setPreferredSize(new Dimension(250,20));
		JLabel path = new JLabel("Ścieżka alarmu: ");
		path.setPreferredSize(new Dimension(100,50));
		pathPanel.add(path, BorderLayout.WEST);
		JPanel pathOptions = new JPanel();
		pathOptions.setPreferredSize(new Dimension(150,20));
		pathOptions.setLayout(new BoxLayout(pathOptions, BoxLayout.X_AXIS));
		pathPanel.add(pathOptions, BorderLayout.EAST);
		
		JFileChooser chooser = new JFileChooser("./data/");
		JButton pathButton = new JButton("...");
		JTextField pathText = new JTextField();
		pathText.setPreferredSize(new Dimension(75,10));
		pathText.setText(mainWindow.getStyleContext().getStyle().getAlarmPath());
		pathButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				int result = chooser.showOpenDialog(pathPanel);
				if (result == JFileChooser.APPROVE_OPTION) 
				{
				    pathText.setText(chooser.getSelectedFile().toString());
				}
				
			}
		});
		
		pathOptions.add(pathText);
		pathOptions.add(pathButton);
		contentPane.add(pathPanel);
		
		JPanel okPanel = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				String font = fontChoice.getSelectedItem();
				Color fontColor = light.isSelected() ? Color.BLACK : new Color(255, 204, 0);
				Color backgroundColor = light.isSelected() ? Color.WHITE : new Color(61, 61, 92);
				String alarmPath = mainWindow.getStyleContext().getStyle().getAlarmPath();
				if( chooser.getSelectedFile() != null ) alarmPath = chooser.getSelectedFile().getAbsolutePath();
				mainWindow.getStyleContext().getStyle().setFont(font);
				mainWindow.getStyleContext().getStyle().setFontColor(fontColor);
				mainWindow.getStyleContext().getStyle().setBackgroundColor(backgroundColor);
				mainWindow.getStyleContext().getStyle().setAlarmPath(alarmPath);
				//mainWindow.getCalendarEventContext().setAlarmPath(pathText.getText());
				
				mainWindow.printCalendar();
				dispose();
			}
		});
		okPanel.add(ok);
		contentPane.add(okPanel);
		
		
		

	}
}
