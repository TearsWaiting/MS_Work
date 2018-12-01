package QQ登录界面;

import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class QQ extends JFrame {

	static JPanel jp1;
	static JPanel jp2;
	static JButton jb;
	static JLabel jl1;
	static JLabel jl2;
	static JLabel jl3;
	static JLabel jl4;
	static JTextField jt1;
	static JTextField jt2;
	// static JPasswordField jt2;
	static JCheckBox jc1;
	static JCheckBox jc2;
	//static int jp1Width = 431;
	//static int jp1Height = 164;

	public QQ() {
		init();
		this.setTitle("QQ2018");
		this.setResizable(false);
		// 此处无用：this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		// this.setLayout(null);
		this.setBounds(550, 270, 445, 365);
		Image image0 = new ImageIcon("src\\QQ登录界面\\timg (2).jpg").getImage();
		this.setIconImage(image0);
	}

	public void init() {
		jp1 = new JPanel(new GridLayout(1, 1, 0, 0));
		jp2 = new JPanel(null);
		jb = new JButton("登录");
		jl1 = new JLabel("注册账号");
		jl2 = new JLabel("找回密码");
		jt1 = new JTextField("QQ号码/手机/邮箱");
		jt2 = new JTextField("密码");
		// jt2 = new JPasswordField();
		jc1 = new JCheckBox("记住密码");
		jc2 = new JCheckBox("自动登录");

		ImageIcon image1 = new ImageIcon("src\\QQ登录界面\\timg (2).jpg");
		// image1.setImage(image1.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT
		jl3 = new JLabel(image1);
		ImageIcon image2 = new ImageIcon("src\\\\QQ登录界面\\u=1201113425,1926591130&fm=26&gp=0.jpg");
		// image2.setImage(image2.getImage().getScaledInstance(jp1Width, jp1Height,
		// Image.SCALE_DEFAULT));
		jl4 = new JLabel(image2);

		jb.setBounds(120, 115, 200, 30);
		jl1.setBounds(330, 10, 150, 50);
		jl2.setBounds(330, 45, 150, 50);
		//出现-10的原因是因为jl1，jl2的size调太大了，超过了窗口边界
		jl3.setBounds(27, 20, 80, 80);
		jt1.setBounds(120, 20, 200, 30);
		jt2.setBounds(120, 50, 200, 30);
		jc1.setBounds(115, 80, 80, 30);
		jc2.setBounds(245, 80, 80, 30);
		
		jp1.setSize(new Dimension(445,170));//虽然结果貌似跟自己在前面声明差不多，但学会这样做还是很重要
		//System.out.println(jp1.getWidth());
		image1.setImage(image1.getImage().getScaledInstance(jl3.getWidth(), jl3.getHeight(), Image.SCALE_DEFAULT));
		image2.setImage(image2.getImage().getScaledInstance(jp1.getWidth(), jp1.getHeight(), Image.SCALE_DEFAULT));

		jp1.add(jl4);
		jp2.add(jb);
		jp2.add(jl1);
		jp2.add(jl2);
		jp2.add(jl3);
		jp2.add(jt1);
		jp2.add(jt2);
		jp2.add(jc1);
		jp2.add(jc2);

		//有需要可以设置背景颜色
		 jp2.setBackground(Color.white);
		 jc1.setBackground(Color.white);
		 jc2.setBackground(Color.white);

		Container container = getContentPane();
		container.setLayout(new GridLayout(2, 1, 0, 0));
		container.add(jp1);
		container.add(jp2);

		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if ("登录".equals(cmd)) {
					String username = jt1.getText();
					String userpassword = jt2.getText();
					if (username.equals("microsoft") && userpassword.equals("666666")) {
						JOptionPane.showConfirmDialog(null, "登录成功");
					}
					/*
					 * if (username.length() == 0 && userpassword.length() == 0) {
					 * JOptionPane.showConfirmDialog(null, "请输入账号和密码"); }
					 */
					if (!username.equals("microsoft") || !userpassword.equals("666666")) {
						JOptionPane.showConfirmDialog(null, "账号或密码错误");
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		new QQ();
		// System.out.println(System.getProperty("user.dir")+"\\src\\timg.jpg");
		// System.out.println(jl3.getWidth());
		// System.out.println(jl3.getHeight());
		//System.out.println(jp1.getWidth());
		//System.out.println(jp1.getHeight());
	}
}
