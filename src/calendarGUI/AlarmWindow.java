package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class AlarmWindow extends JFrame
{
	
	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	AlarmWindow(String message)
	{
		setSize(300,300);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		JLabel label = new JLabel(message);
		contentPane.add(label);
	}
}
