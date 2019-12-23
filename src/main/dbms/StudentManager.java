package main.dbms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class StudentManager extends JdbcController {
	private List<Student> studentList;
	
	public StudentManager() {
		// TODO Auto-generated constructor stub
		super();
		this.studentList = new Vector<Student>();
		connect(); // conn ��ü ����
	}

//Insert function
	//�л������� �Է¹޾� ����Ʈ�� ����
	
	public boolean insertStudent(int num, String name, int kor, int eng, int mat) {
		// ���� ��ȣ�� �ִ��� Ȯ���� ��ȯ
		
		try {
			int rows = st.executeUpdate("INSERT INTO APP_STUDENT VALUES "+"("+num
																				+",'"+name
																				+"',"+kor
																				+","+eng
																				+","
																				+mat+")");
			System.out.println(rows + "���� �߰��߽��ϴ�.");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
//Update function
	//������ �����ϴ� �޼���
	
	//��ȣ�� ���������� �޾Ƽ� ����Ʈ�� �ٲٴ� �޼ҵ�
	/*
	public boolean updateKor(int num, int kor) {
		Student student = selectStudent(num); 
		if(student == null) return false;
		student.setKor(kor); 
		return true;
	}
	public boolean updateEng(int num, int eng) {
		Student student = selectStudent(num); 
		if(student == null) return false;
		student.setKor(eng); 
		return true;
	}
	public boolean updateMat(int num, int mat) {
		Student student = selectStudent(num); 
		if(student == null) return false;
		student.setKor(mat); 
		return true;
	}
	*/
	public void updateAll(int num, String name, int kor, int eng, int mat) {
		try {
			int row = st.executeUpdate("UPDATE APP_STUDENT SET name='"+name+"',kor="+kor+",eng="+eng+",mat="+mat+"WHERE num="+num);
			System.out.println(row + "���� ���� �����Ϸ��߽��ϴ�.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//Read
	//search
	// ��ȣ�� �����Ͽ� �л� �ν��Ͻ��� ��ȯ�ϴ� �޼ҵ�
	public Student selectStudent(int num) {
		Student student = new Student();
		try {
			rs = st.executeQuery("SELECT * FROM APP_STUDENT WHERE num = " + num);
			while(rs.next()) {
				student.setNum(rs.getInt(1));
				student.setName(rs.getString(2));
				student.setKor(rs.getInt(3));
				student.setEng(rs.getInt(4));
				student.setMat(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	//��ȣ�� �л��� ����Ʈ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	public boolean isStudent(int num) {
		try {
			rs = st.executeQuery("SELECT num FROM APP_STUDENT WHERE num = " + num);
			
			if(!rs.next())return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	//��� ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
	public List<Student> getStudentList() {
		return studentList;
	}
	
//���ڿ��� ���� ������ ��ȯ�ϴ� �޼ҵ�
	public String print(int num) {
		Student selectS = selectStudent(num);
		ResultSet rs;
		int number = num, kor=0, eng=0, mat=0, tot, rank = 1, avg, cnt = 0;
		int[] avgs = {};
		
		try {
			rs = st.executeQuery("SELECT * FROM APP_STUDENT WHERE num = " + num);
			while(rs.next()) {
				kor = rs.getInt(3);
				eng = rs.getInt(4);
				mat = rs.getInt(5);
			}
			rs = st.executeQuery("SELECT kor, eng, mat FROM APP_STUDENT WHERE num <> " + num);
			while(rs.next()) { cnt++; } // ī��Ʈ ����
			rs.beforeFirst();
			avgs = new int[cnt];
			int i = 0;
			while(rs.next()) {
				avgs[i] = (rs.getInt(1) + rs.getInt(2) + rs.getInt(3))/3;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tot = kor+eng+mat;
		avg = tot/3;
		for(int tmp : avgs) {
			if(tmp > avg) rank++;
		}
		return "number : " + number + "\n"
				+ "kor : " + kor + "\n"
				+ "eng : " + eng + "\n"
				+ "mat : " + mat + "\n"
				+ "tot : " + tot + "\n"
				+ "avg : " + tot/3 + "\n"
				+ "rank : " + rank;
	}
	
	public String namePrint(int num) {
		String name = "";
		try {
			rs = st.executeQuery("SELECT name FROM APP_STUDENT WHERE num = " + num);
			rs.next();
			name = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name; 
	}
	
//Delete
	//�л���ȣ�� �����ؼ� �ش��л��� ����� �޼ҵ�
	public boolean deleteStudent(int num) {
		Student student = selectStudent(num);
		if(student == null) return false;
		try {
			int row = st.executeUpdate("DELETE FROM APP_STUDENT WHERE num="+num);
			System.out.println(row + "���� �����߽��ϴ�.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}