package QQ登录界面;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GridLayoutExample extends JFrame
{
	JButton button1 = new JButton("ONE");
	JButton button2 = new JButton("TWO");
	JButton button3 = new JButton("THREE");
	JButton button4 = new JButton("FOUR");
	JButton button5 = new JButton("FIVE");
	JButton button6 = new JButton("SIX");
	JButton button7 = new JButton("SEVEN");
	JButton button8 = new JButton("EIGHT");
	JButton button9 = new JButton("NINE");
	
	GridLayoutExample()
	{
		init();
		this.setTitle("网格布局");
		this.setResizable(true);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	void init()
	{
		this.setLayout(new GridLayout(4,2,5,5));
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
		this.add(button6);
		this.add(button7);
		this.add(button8);
		//this.add(button9);
	}
	
	public static void main(String[] args)
	{
		new GridLayoutExample(); 
	}
}
