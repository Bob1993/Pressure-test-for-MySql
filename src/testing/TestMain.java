package testing;

import java.sql.*;
import util.JDBCUtil;

public class TestMain {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private String sql;
	private String sqlTemp;

	public void add(int tableCount, int fieldCount, int dataCount)// ��Ӳ���,���������ֱ��ǣ�
	// �������ı�ĸ������ֶ�����������
	{
		Thread currentThread = Thread.currentThread();
		int count = 0;
		try {
			conn = JDBCUtil.getConnection();// ��ȡ���ݿ�����,���쳣���ⲿ����󣬳����Զ�ֹͣ�����쳣���׽���֮�󣬳��������ⲿû���쳣���������ɻ�����Ǹ�����while
			tableCount = tableCount > tableCount() ? tableCount() : tableCount;// ��Ŀ����������ʵ�ʱ�����ѡС����Ϊʵ��ֵ
			fieldCount = fieldCount > fieldCount() ? fieldCount() : fieldCount;
			/*
			 * ��ʼƴ��sql���
			 */
			sqlTemp = " (";
			String val = ")values(";
			for (int i = 0; i < fieldCount - 1; i++) {// ���ø�������ֵ
				sqlTemp += "id" + i + ",";// ƴ��ǰn-1���ֶ�
				val += "0,";// ��������Ŀ���������0
			}
			sqlTemp += "id" + (fieldCount - 1) + val + "0);";// sql���ĺ�벿��ƴ�����

			while (dataCount > 0) {
				for (int i = 0; i < tableCount; i++) {
					sql = "insert into " + "target" + i + sqlTemp;// ��ʾ��targeti������в�������
					ps = conn.prepareStatement(sql);// ��ˣ�����ֻ��Ҫ���쳣�׳��ĵط�������һ�¶�Ӧ������Ҫ��ʾ�����ݼ���
					ps.executeUpdate();// ִ�и���
					dataCount--;
					count++;
				}
			}
		} catch (SQLException e) {// ������
			// TODO: handle exception
			synchronized (this) {
			Gui.jta.append(currentThread.getName() + "�ҵ��������������Ϊ��" + count+ '\n');
			currentThread.stop();	
			}
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
	}

	public void query(int tableCount, int dataCount)// ��ѯ����
	{
		Thread currenThread = Thread.currentThread();
		int id = 0, count = 0;
		try {
			conn = JDBCUtil.getConnection();// �������ݿ�
			tableCount = tableCount > tableCount() ? tableCount() : tableCount;// ��Ŀ����������ʵ�ʱ�����ѡС����Ϊʵ��ֵ
			int counting = counter(tableCount);// ��¼���б��еļ�¼����
			counting = (dataCount < counting ? dataCount : counting);// ��ȡ��Ҫ��ѯ����������������֮��Ľ�Сֵ
			Gui.jta.append("���ݲ�ѯ��Ϊ��" + counting + '\n');// ��ʾ�����߳��Ƿ�ҵ�����Ҫ�����ѯ��������Ϊ���û�ҵ�����������ͱ�ʾ��ѯ���Ľ��
			sqlTemp = " where id0= ?;";// ������������ռλ����ֻ��ֵ�����ã���Ϊ�����ݿ��У��κ���
			while (counting > 0)// ��ѯ�����ս���������
			{
				for (int i = 0; i < tableCount; i++) {
					sql = "select * from target" + i + sqlTemp;
					ps = conn.prepareStatement(sql);// ����Ԥ������
					ps.setInt(1, id);
					ps.executeQuery();// ִ�в�ѯ
					counting--;
					count++;
				}
				id++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			synchronized (this) {
				Gui.jta.append(currenThread.getName() + "�ҵ�����ѯ����" + count+'\n');// ����ҵ������������ѯ�ɹ��Ĵ���
				currenThread.stop();
			}
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
	}

	public void addTable(int tableCount)// �ӱ���������Ҳ�����Ǹ�����������ʾɾ������������ӻ���ɾ�������ǵ���
	{
		try {
			conn = JDBCUtil.getConnection();// �������ݿ�,���쳣�׽�����
			int count = tableCount();// ��ȡ��ǰ�������

			if (count == 0)// ���test����û�б���ô��ֱ�Ӵ�����
			{
				for (int i = 0; i < tableCount; i++) {
					ps = conn.prepareStatement("create table target" + i
							+ "(id0 int(10) primary key auto_increment)");// ���ſ�ʼɾ����
					ps.execute();// ִ��
				}
				System.out.println(123456);
			} else if (tableCount <= 0)// ɾ����Ĳ���
			{
				tableCount = -tableCount;//
				for (int i = count - 1; (i > count - 1 - tableCount) && i >= 0; i--) {
					ps = conn.prepareStatement("drop table target" + i + ";");// ���ſ�ʼɾ����
					ps.execute();// ִ��ɾ��
				}
			} else {// ������
				for (int i = count; i < (count + tableCount); i++) {
					sql = "create table target" + i + " like target0;";// �Ա�0Ϊģ��ӱ�
					ps = conn.prepareStatement(sql);
					ps.execute();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		Gui.jta.append("add Table successfully!!!\n");// ֪ͨ���ɹ�
	}

	public void addField(int fieldCount)// Ϊ���б�����ֶΣ�ͬ������ɾ������
	{
		sqlTemp = "alter table ";
		try {
			conn = JDBCUtil.getConnection();// �������ݿ�,���쳣�׽�����
			int fieldscount = fieldCount();
			int tablecount = tableCount();// ��ȡ��ǰ�������

			for (int i = 0; i < tablecount; i++) {// ����ÿһ����
				if (fieldCount < 0)// ɾ���ֶ�
				{
					for (int j = fieldscount - 1; j > fieldscount + fieldCount
							- 1; j--) {
						sql = sqlTemp + "target" + i + " drop column id" + j
								+ ";";
						ps = conn.prepareStatement(sql);
						ps.execute();
					}
				} else {
					for (int j = fieldscount; j < fieldscount + fieldCount; j++) {
						sql = sqlTemp + "target" + i + " add column id" + j
								+ " int(10);";
						ps = conn.prepareStatement(sql);
						ps.execute();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		Gui.jta.append("add field successfully!!!\n");// ֪ͨ��ӳɹ�
	}

	public void clean()// ������б���Ϊ�������ã�����ÿ���õ�ʱ�����ȫ�����һ�κõ㡣
	{
		sqlTemp = "truncate table ";
		try {
			conn = JDBCUtil.getConnection();// �������ݿ�,���쳣�׽�����
			int count = tableCount();
			for (int i = 0; i < count; i++) {
				sql = sqlTemp + "target" + i + ";";
				ps = conn.prepareStatement(sql);
				ps.execute();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		Gui.jta.append("clean successfully!!!\n");
	}

	public int counter(int tableCount) throws SQLException// ͳ��Ŀ����е����м�¼��������Ҫ��Ϊ��ѯ�ṩ�����������ޡ�
	{// Ϊʲô����ѯ���б�ļ�¼���أ� ��Ϊ����������ѯ���Ե�ʱ����������������Ҫ�ɶ�Ӧ����ܼ�¼������������test���м�¼����
		int count = 0;
		sqlTemp = "select count(id0) as c from ";
		for (int i = 0; i < tableCount; i++) {
			sql = sqlTemp + "target" + i + ";";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();// Ĭ���α�ָ����rs���ݼ������ݵ�ǰһ���α꣬��Ӧ����0��
			count += rs.getInt("c");// ��ʱ�洢����ȡ�Ľ��
		}
		return count;
	}

	public int tableCount() throws SQLException// ��ȡ��ǰ�������
	{
		ps = conn.prepareStatement("show tables;");
		rs = ps.executeQuery();
		rs.last();
		return rs.getRow();// ��ȡ��ǰ�������
	}

	public int fieldCount() throws SQLException {
		ps = conn.prepareStatement("desc target0;");// ���ֱ�ṹ
		rs = ps.executeQuery();
		rs.last();
		return rs.getRow();// ��ȡ�ֶ�����
	}
}
