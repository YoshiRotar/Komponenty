package calendarGUI;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Settings extends JFrame 
{

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel contentPane;
	
	Settings()
	{
		setSize(200,200);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(3,1));
		setContentPane(contentPane);
		setVisible(true);
		
		JPanel stylePanel = new JPanel(new FlowLayout());
		JLabel style = new JLabel("Styl: ");
		JRadioButton dark = new JRadioButton("Ciemny");
		JRadioButton light = new JRadioButton("Jasny");
		ButtonGroup styleGroup = new ButtonGroup();
		styleGroup.add(light);
		styleGroup.add(dark);
		stylePanel.add(style);
		stylePanel.add(dark);
		stylePanel.add(light);
		contentPane.add(stylePanel);
		
		JPanel languagePanel = new JPanel(new FlowLayout());
		Choice languageChoice = new Choice();
		languageChoice.add("polski");
		languageChoice.add("angielski");
		languagePanel.add(languageChoice);
		contentPane.add(languagePanel);
		
		JPanel okPanel = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				dispose();
				
			}
		});
		okPanel.add(ok);
		contentPane.add(okPanel);
		
		
		

	}
}
