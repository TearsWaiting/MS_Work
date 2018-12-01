package QQ��¼����;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

//�̳� JFrame �Ϳ���ʹ����������(BorderLayoutExample)��Ϊһ������
//���ҿ��ԶԴ�������Խ�����չ���ҿ��Զ����Լ���Ҫ�������������
public class BorderLayoutExample extends JFrame
{
	
	JButton button1 = new JButton("East");
	JButton button2 = new JButton("South");
	JButton button3 = new JButton("West");
	JButton button4 = new JButton("North");
	JButton button5 = new JButton("Center");
	
	BorderLayoutExample()
	{
		init();
		//init�������Զ���ģ��������void init()��
		//��û�е���������������ɵı߿��ǿհ׵ģ�û�в���ؼ����˳���Ŀؼ��ǰ�ťJButton��
		this.setTitle("�߽粼��");
		//���ô���ı��⣨��ʾ�����Ͻǵģ�
		this.setResizable(true);
		//�Ƿ�����ô��������Ե��������С
		this.setSize(500,500);
		//setSize()�����е�һ������Ϊ����Ŀ�ȣ��ڶ�������Ϊ����ĸ߶�
		this.setLocationRelativeTo(null);
		//���ô���ĳ�ʼλ��
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//���ô����Ĭ�Ϲرշ�ʽ�ǹرմ���ͻ�ֹͣ����Ľ���
		this.setVisible(true);
		//���ô����Ƿ�ɼ�
	}
	
	void init()
	{
		this.setLayout(new BorderLayout(5,5));
		//BorderLayout()���췽���У���һ�������ǵ�ˮƽ��࣬�ڶ����������Ĵ�ֱ���
		
		//�ڴ��������JButton����(��ť�ؼ�)
		//add()�����У���һ�������ǲ���Ŀؼ����󣬵ڶ����ǲ����λ��
		this.add(button1,BorderLayout.EAST);
		this.add(button2,BorderLayout.SOUTH);
		this.add(button3,BorderLayout.WEST);
		this.add(button4,BorderLayout.NORTH);
		this.add(button5,BorderLayout.CENTER);
	}
	
	public static void main(String[] args)
	{
		new BorderLayoutExample();
	}
	
}