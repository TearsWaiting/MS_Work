package �������±�;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame{
	static JFrame jf;
	static JPanel jp;
	static JButton jb;
	static JLabel jl1;
	static JLabel jl2;
	static JLabel jl3;
	static JLabel jl4;
	static JLabel jl5;
	static JTextField jt1;
	static JPasswordField jt2;
	static JCheckBox jc1;
	static JCheckBox jc2;
	
	public Login()
	{
		jf = new JFrame();
		jp = new JPanel();
		jb = new JButton("��¼");
		jl1 = new JLabel("ע���˻�");
		jl2 = new JLabel("��������");
		jl3 = new JLabel("Welcome to LockedText");
		jl4 = new JLabel("�˻�");
		jl5 = new JLabel("����");
		jt1 = new JTextField(); 
		jt2 = new JPasswordField();
		jc1 = new JCheckBox("��ס����");
		jc2 = new JCheckBox("�Զ���¼");
		
		
		jf.setLayout(new GridLayout(1,1,0,0));
		jf.setTitle("TextLogin");
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setBounds(550, 270, 445, 365);
		
		jf.add(jp);
		jp.add(jb);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jl4);
		jp.add(jl5);
		jp.add(jt1);
		jp.add(jt2);
		jp.add(jc1);
		jp.add(jc2);

		
		jb.setBounds(120, 260, 200, 30);
		jl1.setBounds(330, 110, 150, 50);
		jl2.setBounds(330, 160, 150, 50);
		jl3.setBounds(80, 40, 300, 50);
		jl4.setBounds(80, 110, 150, 50);
		jl5.setBounds(80, 160, 150, 50);
		jt1.setBounds(120, 120, 200, 30);
		jt2.setBounds(120, 170, 200, 30);
		jc1.setBounds(115, 220, 80, 30);
		jc2.setBounds(245, 220, 80, 30);
		
		
		jp.setLayout(null);
		jp.setBackground(Color.white);
		jc1.setBackground(Color.white);
		jc2.setBackground(Color.white);
		
		jl3.setFont(new Font("",3,25));
		jl3.setForeground(Color.BLUE);
		jb.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String username = jt1.getText();
						String userpassword = jt2.getText();
						if(username.equals("123")&&userpassword.equals("456"))
						{
						JOptionPane.showMessageDialog(jf,"��¼�ɹ�");
						enter();
						}
						else
						{
						JOptionPane.showMessageDialog(jf,"��¼ʧ��");	
						}
					}
				}
				
				);
	}
	
	private void enter()
	{
		new LockedText1();
	}
	
	public static void main(String[] args)
	{
		Login login = new Login();
	}
}


//���������ڵ�¼��ת�����±�
