package testing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Gui extends JFrame implements ActionListener{
	private JTabbedPane jtp;
	private JLabel jl0,jl1,jl2,jl3,jl4,jl5,jl6,jl7;//��ʾ�߳���,����Ŀ,�ֶ���,������,��ӱ�,����ֶ�,���н����
	
	public static JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;//���ǩ���Ӧ���ı���
	private GridLayout gl= null;
	
	private JPanel jp1,jp2,jp3,jp4,jp5;
	
	private JButton jb1,jb2,jb3,jb4,jb5;

	private JScrollPane jsp= null;
	public static JTextArea jta= null;
	
	public Gui() {
		// TODO Auto-generated constructor stub
		this.setTitle("���ݿ�ѹ������");
		
		jtp= new JTabbedPane();
		jl0= new JLabel(new ImageIcon("images/picture.png"));
		jl1= new JLabel("�߳���",JLabel.CENTER);
		jl2= new JLabel("����Ŀ",JLabel.CENTER);
		jl3= new JLabel("�ֶ���",JLabel.CENTER);
		jl4= new JLabel("������",JLabel.CENTER);
		jl5= new JLabel("��ӱ�",JLabel.CENTER);
		jl6= new JLabel("����ֶ�",JLabel.CENTER);
		jl7= new JLabel("���н��",JLabel.CENTER);
		jtf1= new JTextField("1",10);
		jtf2= new JTextField("1",10);
		jtf3= new JTextField("1",10);
		jtf4= new JTextField("0",10);
		jtf5= new JTextField("0",10);
		jtf6= new JTextField("0",10);
		
		jtf1.setHorizontalAlignment(JTextField.CENTER);
		jtf2.setHorizontalAlignment(JTextField.CENTER);
		jtf3.setHorizontalAlignment(JTextField.CENTER);
		jtf4.setHorizontalAlignment(JTextField.CENTER);
		jtf5.setHorizontalAlignment(JTextField.CENTER);
		jtf6.setHorizontalAlignment(JTextField.CENTER);
		
		jb1= new JButton("�������");
		jb2= new JButton("��ѯ����");
		jb3= new JButton("��ձ�");
		jb4= new JButton("��ӱ�");
		jb5= new JButton("�����");
		
		jb1.addActionListener(this);//ע��5����ť����
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		jb5.addActionListener(this);
		
		jta= new JTextArea(5,20);
		jsp= new JScrollPane(jta);
		
		jp1= new JPanel();
		jp2= new JPanel();
		jp3= new JPanel();
		jp4= new JPanel();
		jp5= new JPanel();
		
		gl= new GridLayout(5,2);
		gl.setHgap(5);	gl.setVgap(5);
		jp1.setLayout(gl);//5��2�е����񲼾�
		jp1.add(jl1);	jp1.add(jtf1); 
		jp1.add(jl2);	jp1.add(jtf2);
		jp1.add(jl3);	jp1.add(jtf3);
		jp1.add(jl4);	jp1.add(jtf4);
		jp1.add(jb1);	jp1.add(jb2); 
		
		gl= new GridLayout(3,1);
		gl.setVgap(5);
		jp4.setLayout(gl);
		jp4.add(jb3); jp4.add(jb4); jp4.add(jb5);
		
		gl= new GridLayout(2,2);
		gl.setHgap(5);	gl.setVgap(5);
		jp5.setLayout(gl);
		jp5.add(jl5);	jp5.add(jtf5);
		jp5.add(jl6);	jp5.add(jtf6);
		jp2.setLayout(new BorderLayout());
		jp2.add(jp5,BorderLayout.NORTH);	jp2.add(jp4);
		
		jp3.setLayout(new BorderLayout());//���û����ϵĲ���
		jp3.add(jl7,BorderLayout.NORTH);
		jp3.add(jsp);
		
		jtp.add(jp1);	jtp.add(jp2);
		jp1.setName("ѹ������");	jp2.setName("�޸ı�ṹ");
	
		jtp.add(jp1);
		jtp.add(jp2);
		this.add(jl0,BorderLayout.NORTH);//���ͼƬ
		this.add(jtp);//��Ӵ���
		this.add(BorderLayout.SOUTH,jp3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 200, 270, 400);
		setResizable(false);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		int threadCount= Integer.parseInt(jtf1.getText());
		int tableCount= Integer.parseInt(jtf2.getText());
		int fieldCount= Integer.parseInt(jtf3.getText());
		int dataCount= Integer.parseInt(jtf4.getText());
		Operating operating;
		String operation;
		Object op= e.getSource();
		jta.setText("..............\n");
		
		if(op== jb1)//����Ҳ������ע������ĵط���setActionCommand,Ȼ��������ʹ��getActionCommand��ȡ���͵���Ϣ
		{//���뱻���
			operation= "insert testing";
		}
		else if(op== jb2)//��ѯ�����
		{
			operation= "query testing";
		}
		else if(op== jb3)//��ձ����
		{
			operation= "clean";
			threadCount= 1;
		}
		else if(op== jb4)//��ӱ�
		{
			threadCount= 1;
			operation= "add table";
		}
		else{//�����
			threadCount= 1;
			operation= "add column";
		}
		
		operating= new Operating(operation, tableCount, fieldCount, dataCount);
		for (int i = 0; i < threadCount; i++) {//����ָ���������̲߳�����
			new Thread(operating,"�߳�"+i).start();
		}
	}
	
	public static void main(String[] args) {
		new Gui();
	}
}
