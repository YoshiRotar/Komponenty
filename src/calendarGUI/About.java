package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class About extends JFrame
{
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel contentPane;
	
	About()
	{
		setSize(200,200);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);
		setVisible(true);
		
		
		JLabel about = new JLabel("<html>Autorzy:<br/><p>Pawe� M�ynarczyk<br/>Mateusz Kuzniarek<<br/><br/><br/>Wersja:<br/>2v0.11_turbo</p></html>");
		contentPane.add(about);
		
	}
}
