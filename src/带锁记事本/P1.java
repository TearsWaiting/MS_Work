package �������±�;

//��ť�������ʹ��ڼ������÷�

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class P1
{
    //�����ͼ������������������
    private Frame f;
    private Button bt; 
    
    //����
    P1()//���췽��
    {
        madeFrame();
    }
    
    public void madeFrame()
    {
        f = new Frame("My Frame");
        
        //��Frame���л������á�
        f.setBounds(300,100,600,500);//�Կ�ܵ�λ�úʹ�С��������
        f.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));//��Ʋ���
        
        bt = new Button("My Button");
        
        //�������ӵ�Frame��
        f.add(bt);
        
        //����һ�´����ϵ��¼�
        myEvent();
        
        //��ʾ����
        f.setVisible(true);
    }
    
    private void myEvent()
    {
        f.addWindowListener(new WindowAdapter()//���ڼ���
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("����ִ�йرգ�");
                System.exit(0);
            }
        });
        //�ð�ť�߱��رմ��ڵĹ���
        bt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 System.out.println("��ťִ�йرմ��ڵĹ���");
                 System.exit(0);
            }
        });
    }
    
    public static void main(String[] agrs)
    {
        new P1();
    }
}