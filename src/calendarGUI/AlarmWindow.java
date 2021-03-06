package calendarGUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Klasa reprezentująca okno powiadomienia, w przypadku wywołania się alarmu.
 * 
 * @author Paweł Młynarczyk
 * @author Mateusz Kuzniarek
 */
@SuppressWarnings("serial")
public class AlarmWindow extends JFrame
{
	
	private JPanel contentPane;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	Clip clip;
	
	/**
	 * Konstruktor klasy mający na celu odtworzenie wiadomości oraz puszczenie dzwięku alarmu.
	 * 
	 * @param message wiadomość tekstowa wyświetlona w oknie
	 * @param alarmPath ścieżka do pliku dzwiękowego wav
	 */
	AlarmWindow(String message, String alarmPath)
	{
		try
		{
			File file = new File(alarmPath);
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
	        clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
		}
		catch(Exception e)
		{
			System.out.println("Nie udalo sie odtworzyc pliku");
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
		
		System.out.println(message);
		
		JLabel label = new JLabel(message);
		contentPane.add(label);

		setVisible(true);
	}
}
