package QQ��¼����;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class ImageTest
{
	public static void main(String[] args)
    {
		JFrame jf = new JFrame("ͼƬ����");
        MyPanel mp = new MyPanel();
        jf.add(mp);
        jf.pack();
        jf.validate();
        jf.setVisible(true);
	}
 
}

class MyPanel extends JPanel
{
	
	public MyPanel()
	{
		super();
		
		//JButton jb1;
		JButton jb2;
		
		//Image image = getToolkit().getImage("C:/Users//17734//Desktop//timg (2).jpg");
		ImageIcon imageIcon = new ImageIcon("C:/Users//17734//Desktop//timg (2).jpg");
		
		//jb1 = new JButton();
		jb2 = new JButton(imageIcon);
		
		/*��ֻҪע�͵���仰���ͻᷢ�������ߵ�Ч������ȫ��ͬ��*/
		//image = image.getScaledInstance(50,50,image.SCALE_SMOOTH);
		//jb1.setIcon(new ImageIcon(image));
		
		//this.add(jb1);
		this.add(jb2);
	}
 
}
