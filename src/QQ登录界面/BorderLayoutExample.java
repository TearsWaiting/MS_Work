package QQ登录界面;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

//继承 JFrame 就可以使定义的这个类(BorderLayoutExample)成为一个窗体
//并且可以对窗体的属性进行扩展并且可以定义自己需要的特殊操作方法
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
		//init方法是自定义的（见下面的void init()）
		//若没有调用这个方法则生成的边框是空白的，没有插入控件（此程序的控件是按钮JButton）
		this.setTitle("边界布局");
		//设置窗体的标题（显示在左上角的）
		this.setResizable(true);
		//是否可以让窗体伸缩以调整窗体大小
		this.setSize(500,500);
		//setSize()方法中第一个参数为窗体的宽度，第二个参数为窗体的高度
		this.setLocationRelativeTo(null);
		//设置窗体的初始位置
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//设置窗体的默认关闭方式是关闭窗体就会停止程序的进程
		this.setVisible(true);
		//设置窗体是否可见
	}
	
	void init()
	{
		this.setLayout(new BorderLayout(5,5));
		//BorderLayout()构造方法中，第一个参数是的水平间距，第二个是组件间的垂直间距
		
		//在窗体里加入JButton对象(按钮控件)
		//add()方法中，第一个参数是插入的控件对象，第二个是插入的位置
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