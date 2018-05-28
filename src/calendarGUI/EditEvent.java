package calendarGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EditEvent extends Option
{
	//tutaj jako argument trzeba dac reprezentacje eventu w warstwie logiki
	private void initValues()
	{
		//Uzupelnienie pol wartosciami
	}
	
	EditEvent()
	{
		super();
		initValues();
		JPanel panel = new JPanel();
		panel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		panel.setMaximumSize(new Dimension(500, 30));
		panel.setLayout(new FlowLayout());
		JButton add = new JButton("Edytuj");
		add.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
		    	//dodawanie
				dispose();
		    }
		});
		JButton cancel = new JButton("Zaniechaj");
		cancel.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent event) 
		    {
				dispose();
		    }
		});
		panel.add(add);
		panel.add(cancel);
		
		contentPane.add(panel);
	}
}
