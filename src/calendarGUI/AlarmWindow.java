package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class AlarmWindow extends JFrame
{
	
	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	Clip clip;
	
	AlarmWindow(String message)
	{
		try
		{
			File file = new File("./data/alarm.wav");
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
	        clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
		this.addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
		
				if(clip!=null)
				{
					clip.stop();
					clip.close();
					dispose();		
				}
			}
		});
		
		setSize(300,120);
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
