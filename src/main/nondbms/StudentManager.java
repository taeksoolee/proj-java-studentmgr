package main.nondbms;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class StudentManager{
	private List<Student> studentList;
	
	public StudentManager() {
		// TODO Auto-generated constructor stub
		super();
		this.studentList = new Vector<Student>();
	}

//Insert function
	//�л������� �Է¹޾� ����Ʈ�� ����
	
	public boolean insertStudent(int num, String name, int kor, int eng, int mat) {
		// ���� ��ȣ�� �ִ��� Ȯ���� ��ȯ
		for(Student s  : studentList) {
			if(s.getNum() == num)	return false;	
		}
		studentList.add(new Student(num,name,kor,eng,mat));
		return true;
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
		Student student = selectStudent(num); 
		student.setName(name);
		student.setKor(kor);
		student.setEng(eng);
		student.setMat(mat);
	}
//Read
	//search
	// ��ȣ�� �����Ͽ� �л� �ν��Ͻ��� ��ȯ�ϴ� �޼ҵ�
	public Student selectStudent(int num) {
		for(Student s  : studentList) {
			if(s.getNum() == num) return s;
		}
		return null;
	}
	
	//��ȣ�� �л��� ����Ʈ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	public boolean isStudent(int num) {
		for(Student s  : studentList) {
			if(s.getNum() == num) return true;
		}
		return false;
	}
	//��� ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
	public List<Student> getStudentList() {
		return studentList;
	}
	
//���ڿ��� ���� ������ ��ȯ�ϴ� �޼ҵ�
	public String print(int num) {
		Student selectS = selectStudent(num);
		int number = selectS.getNum();
		int kor = selectS.getKor();
		int eng = selectS.getEng();
		int mat = selectS.getMat();
		int tot = kor+eng+mat;
		int rank = 1;
		
		for(Student student : studentList) {
			if(student.getKor() + student.getEng() + student.getMat() > tot) {
				rank++;
			}
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
		return selectStudent(num).getName();
	}
	
//Delete
	//�л���ȣ�� �����ؼ� �ش��л��� ����� �޼ҵ�
	public boolean deleteStudent(int num) {
		Student student = selectStudent(num);
		if(student == null) return false;
		studentList.remove(student);
		return true;
	}
	//����л������� ����� �޼ҵ�
	public boolean deleteAll() {
		if(studentList.size() == 0) return false;
		studentList.clear();
		return true;
	}
	
//�л������� �ֿܼ� ����ϴ� �޼ҵ�(�׽�Ʈ��)
	public void printList(String txt) {
		System.out.println(txt);
		for(Student student : studentList) {
			System.out.println(student);
		}
	}
//TEST
	public static void main(String[] args) {
		StudentManager s = new StudentManager();
		s.insertStudent(123, "aaa", 10, 10, 10);
		s.insertStudent(234, "aaa", 20, 20, 20);
		String[][] content = new String [s.studentList.size()][5];
		
		for(int row = 0; row < content.length; row++) {
			for(int col = 0; col < content[row].length; col++) {
				switch (col) {
				case 0: content[row][col] = s.studentList.get(row).getNum()+""; break;
				case 1: content[row][col] = s.studentList.get(row).getName(); break;
				case 2: content[row][col] = s.studentList.get(row).getKor()+""; break;
				case 3: content[row][col] = s.studentList.get(row).getEng()+""; break;
				case 4: content[row][col] = s.studentList.get(row).getMat()+""; break;
				}
			}
		}
		
		for(String[] strs : content) {
			for(String str : strs) {
				System.out.print(str + "\t");
			}
			System.out.println();
		}
	}
}