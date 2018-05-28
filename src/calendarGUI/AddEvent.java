package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AddEvent extends JFrame
{

	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	public AddEvent()
	{
		setSize(500,300);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setVisible(true);
		
		JLabel label = new JLabel("Nazwa Wydarzenia:");
		contentPane.add(label);
		
		JTextField textFieldName = new JTextField();
		textFieldName.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldName);
		
		JLabel label_1 = new JLabel("Miejsce Wydarzenia:");
		contentPane.add(label_1);
		
		JTextField textFieldPlace = new JTextField();
		textFieldPlace.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldPlace);
		
		JLabel label_2 = new JLabel("Opis Wydarzenia:");
		contentPane.add(label_2);
		
		JTextField textFieldDescription = new JTextField();
		textFieldDescription.setMaximumSize(new Dimension(500, 30));
		contentPane.add(textFieldDescription);
		
	}

}
