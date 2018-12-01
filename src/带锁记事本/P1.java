package 带锁记事本;

//按钮监听器和窗口监听器用法

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class P1
{
    //定义该图形中所需的组件的引用
    private Frame f;
    private Button bt; 
    
    //方法
    P1()//构造方法
    {
        madeFrame();
    }
    
    public void madeFrame()
    {
        f = new Frame("My Frame");
        
        //对Frame进行基本设置。
        f.setBounds(300,100,600,500);//对框架的位置和大小进行设置
        f.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));//设计布局
        
        bt = new Button("My Button");
        
        //将组件添加到Frame中
        f.add(bt);
        
        //加载一下窗体上的事件
        myEvent();
        
        //显示窗体
        f.setVisible(true);
    }
    
    private void myEvent()
    {
        f.addWindowListener(new WindowAdapter()//窗口监听
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("窗体执行关闭！");
                System.exit(0);
            }
        });
        //让按钮具备关闭窗口的功能
        bt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 System.out.println("按钮执行关闭窗口的功能");
                 System.exit(0);
            }
        });
    }
    
    public static void main(String[] agrs)
    {
        new P1();
    }
}