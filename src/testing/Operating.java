package testing;

public class Operating implements Runnable {
	private String operation;//�����߳���һ������Ķ���
	private int tableCount= 1;//����������
	private int fieldCount= 1;//�����ֶ�����
	private int dataCount= 0;
	private TestMain tm= new TestMain();
	public Operating(String operation, int tableCount, int fieldCount, int dataCount) {
		// TODO Auto-generated constructor stub
		this.operation= operation;
		this.tableCount= tableCount;
		this.fieldCount= fieldCount;
		this.dataCount= dataCount;
	}
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		long startTime= System.currentTimeMillis();//��ȡִ��ǰ��ʱ��
		switch (operation) {
		case "insert testing":
			tm.add(tableCount, fieldCount, dataCount);
			break;
		case "query testing": 
			tm.query(tableCount, dataCount);
			break;
		case "clean": 
			tm.clean();
			break;
		case "add table":
			tm.addTable(Integer.parseInt(Gui.jtf5.getText()));
			break;
		case "add column":
			tm.addField(Integer.parseInt(Gui.jtf6.getText()));
		}
		String ThreadName= Thread.currentThread().getName();
		Gui.jta.append(ThreadName+"-->"+operation+": "+(System.currentTimeMillis()-startTime)+"ms\n");	
	}
}
