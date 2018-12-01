package QQµ«¬ºΩÁ√Ê;

import java.awt.*;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUItest1 extends JFrame 
{
	Container container = this.getContentPane();
	ImageIcon image = new ImageIcon("C:/Users//17734//Desktop//timg (2).jpg");
	JLabel jl1 = new JLabel(image);
	
	public GUItest1() 
	{
		container.add(jl1);
		this.setVisible(true);
		this.pack();
	
	
	}
	
	
    public static void main(String[] args) 
    {
    	
    	//JFrame jf = new JFrame("TEST");
    	//Container container = jf.getContentPane();
    	
    	//JButton jb1 = new JButton("≤‚ ‘");
    	//container.add(jb1);
    	JPanel jp = new JPanel();
    	//jf.setContentPane(jp);
    	
    	
    	
    	//jp.add(jl1);
    	//jf.setVisible(true);
    	//jf.pack();
    	
        new GUItest1();
    }
}
