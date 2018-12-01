package QQ登录界面;
import java.awt.*;
import javax.swing.*;

public class QQtest {
	
	static JFrame jf;
	static JPanel jp1;
	static JPanel jp2;
	static JButton jb;
	static JLabel jl1;
	static JLabel jl2;
	static JLabel jl3;
	static JLabel jl4;
	static JTextField jt1;
	static JPasswordField jt2;
	static JCheckBox jc1;
	static JCheckBox jc2;

	
	public QQtest()
	{
		jf = new JFrame("QQ2018");
		init();
		//this.setTitle("QQ2018");
		jf.setResizable(true);
		//此处无用：this.setSize(500,500);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		//this.setLayout(null);
		jf.setBounds(550, 270, 445, 365);
		Image img0 = new ImageIcon("C:/Users//17734//Desktop//timg (2).jpg").getImage();
		jf.setIconImage(img0);
	}
	
	public void init ()
	{
		jp1 = new JPanel(new GridLayout(1,1,0,0));
		jp2 = new JPanel(null);
		jb  = new JButton("登录");
		jl1 = new JLabel("注册账号");
		jl2 = new JLabel("找回密码");
		jt1 = new JTextField("QQ号码/手机/邮箱");
		jt2 = new JPasswordField();
		jc1 = new JCheckBox("记住密码");
		jc2 = new JCheckBox("自动登录");
		
		
		
		jl3 = new JLabel();
		Image img1 = new ImageIcon("C:/Users//17734//Desktop//timg (1).jpg").getImage();
		jl3.setIcon(new ImageIcon(img1));
		
		//Image image = getToolkit().getImage("C:/Users//17734//Desktop//timg (2).jpg");
		//image = image.getScaledInstance(50,50,image.SCALE_SMOOTH);
		//jl3.setIcon(new ImageIcon(image));
		
		ImageIcon ico = new ImageIcon("C:/Users//17734//Desktop//timg.jpg");
		jl4=new JLabel(ico);
		

		
		
		jb.setBounds(120,115,200,30);
		jl1.setBounds(330,10,150,50);
		jl2.setBounds(330,-10,150,150);
		jl3.setBounds(27,20,80,80);
		jt1.setBounds(120,20,200,30);
		jt2.setBounds(120,50,200,30);
		jc1.setBounds(115,80,80,30);
		jc2.setBounds(245,80,80,30);
		

		//jp1.add(new JButton("QQ"));
		jp1.add(jl4);
		jp2.add(jb);
		jp2.add(jl1);
		jp2.add(jl2);
		jp2.add(jl3);
		jp2.add(jt1);
		jp2.add(jt2);
		jp2.add(jc1);
		jp2.add(jc2);
		
		
		//Container container = getContentPane();
		jf = new JFrame();
		Container container=jf.getContentPane();
		container.setLayout(new GridLayout(2,2,0,0));
		container.add(jp1);
		container.add(jp2);
		//jf.setContentPane(jp1);
		//jf.setContentPane(jp2);
		
	}
	
	public static void main(String[] args)
	{
		new QQtest();
	}
}