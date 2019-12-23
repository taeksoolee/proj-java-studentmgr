package main.nondbms;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*
 	�л������� ����, ����, �˻�, ���� �ϴ� ���α׷�
 	
 */
public class StudentManagerApp extends JFrame{
	private static final long serialVersionUID = 1L;
//field
	//�Է°��� ���� ����
	private String numT;
	private String nameT;
	private String korT;
	private String engT;
	private String matT;
	private int numDig;
	private int korDig;
	private int engDig;
	private int matDig;
	//
	private JTextField numberText;
	private JTextField nameText;
	private JTextField korText;
	private JTextField engText;
	private JTextField matText;
	
	private JButton createBtn;
	private JButton updateBtn;
	private JButton readBtn;
	private JButton deleteBtn;
	private JButton cancelBtn;
	
	private JTable table;
	private DefaultTableModel model;
	
	private final String[] index = {"Number","Name","Korea","English","Math"};
	
	private StudentManager studentManager;
	private int step; // ���¸� ��Ÿ���� ���� / 0:�⺻
//constructor	
	public StudentManagerApp() {
		super("Student Managerment");
		//�⺻����
		studentManager = new StudentManager();
		step = 0;
				
		//container
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel();
		JPanel numberPanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel korPanel = new JPanel();
		JPanel engPanel = new JPanel();
		JPanel matPanel = new JPanel();
		JPanel inputPanel = new JPanel(new GridLayout(5,1));
		
		// component
		numberText = new JTextField(16);
		nameText = new JTextField(16);
		korText = new JTextField(16);
		engText = new JTextField(16);
		matText = new JTextField(16);
		createBtn = new JButton("Creat");
		updateBtn = new JButton("Update");
		readBtn = new JButton("Read");
		deleteBtn = new JButton("Delete");
		cancelBtn = new JButton("Cancel");
		
		//build
		btnPanel.add(createBtn);
		btnPanel.add(updateBtn);
		btnPanel.add(readBtn);
		btnPanel.add(deleteBtn);
		btnPanel.add(cancelBtn);
		
		numberPanel.add(new JLabel	("Number   "));
		numberPanel.add(numberText);
		namePanel.add(new JLabel	("Name     "));
		namePanel.add(nameText);
		korPanel.add(new JLabel		("Korea    "));
		korPanel.add(korText);
		engPanel.add(new JLabel		("English  "));
		engPanel.add(engText);
		matPanel.add(new JLabel		("Math     "));
		matPanel.add(matText);
		
		inputPanel.add(numberPanel);
		inputPanel.add(namePanel);
		inputPanel.add(korPanel);
		inputPanel.add(engPanel);
		inputPanel.add(matPanel);
		
		mainPanel.add(btnPanel, BorderLayout.NORTH);
		mainPanel.add(inputPanel, BorderLayout.CENTER);
		
		//contetntPane add
		table = new JTable();
		creatModel("�ʱ����");
		getContentPane().add(mainPanel);
		getContentPane().add(new JScrollPane(table));
		
		//add listener
		createBtn.addActionListener(new CreateBtnHandler());
		updateBtn.addActionListener(new UpdateBtnHandler());
		readBtn.addActionListener(new ReadBtnHandler());
		deleteBtn.addActionListener(new DeleteBtnHandler());
		cancelBtn.addActionListener(new CancelBtnHandler());
		
		// component setting
		setAllTextFiled(false);
		
		//frame setting
		setLayout(new GridLayout(2,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500,100,400,500);
		setVisible(true);
		
		setAllT();
		setAllDig();
	}
	
	public static void main(String[] args) {
		new StudentManagerApp();
	}
	
	//���̺� ���� ����� �޼ҵ�
	public void creatModel(String txt) {
		if(model != null) model.setRowCount(0);
		//table Setting
		String[][] arr = new String[studentManager.getStudentList().size()][5];
		
		//����� ���� ����
		for(int row = 0; row < arr.length; row++) {
			for(int col = 0; col < arr[row].length; col++) {
				switch (col) {
				case 0: arr[row][col] = studentManager.getStudentList().get(row).getNum()+""; break;
				case 1: arr[row][col] = studentManager.getStudentList().get(row).getName(); break;
				case 2: arr[row][col] = studentManager.getStudentList().get(row).getKor()+""; break;
				case 3: arr[row][col] = studentManager.getStudentList().get(row).getEng()+""; break;
				case 4: arr[row][col] = studentManager.getStudentList().get(row).getMat()+""; break;
				}
			}
		}
		
		//console���
		System.out.println(txt);
		for(String[] tmps : arr) {
			for(String tmp : tmps) {
				System.out.print(tmp + "\t");
			}
			System.out.println();
		}
		model = new DefaultTableModel(arr, index);
		table.setModel(model);
	}// creatTable end
	
	//��� ��ư�� �ѹ��� �����ϴ� �޼ҵ�
	public void setAllBtn(boolean b) {
		createBtn.setEnabled(b);
		updateBtn.setEnabled(b);
		readBtn.setEnabled(b);
		deleteBtn.setEnabled(b);
		cancelBtn.setEnabled(b);
	}
	//��� ��Ʈ�� �ѹ��� �����ϴ� �޼ҵ�
	public void setAllTextFiled(boolean b) {
		numberText.setEnabled(b);
		nameText.setEnabled(b);
		korText.setEnabled(b);
		engText.setEnabled(b);
		matText.setEnabled(b);
	}
	
	
	public void setAllTextEmpty() {
		numberText.setText("");
		nameText.setText("");
		korText.setText("");
		engText.setText("");
		matText.setText("");
	}
	
//���ڵ��� ó���ϴ� �޼ҵ�
	//�ؽ�Ʈ �ʵ��� ���� ���ڿ��� ������ ""�̸� false ��ȯ
	public boolean setNumT() {
		numT = numberText.getText();
		if(numT.equals("")) return false;
		return true;
	}
	
	public boolean setNameT() {
		nameT = nameText.getText();
		if(nameT.equals("")) return false;
		return true;
	}
	
	public boolean setKorT() {
		korT = korText.getText();
		if(korT.equals("")) return false;
		return true;
	}
	
	public boolean setEngT() {
		engT = engText.getText();
		if(engT.equals("")) return false;
		return true;
	}
	
	public boolean setMatT() {
		matT = matText.getText();
		if(matT.equals("")) return false;
		return true;
	}
	
	public boolean setAllT() { // �Ѱ��� ""�� ������ false
		
		if(setNumT() && setNameT() && setKorT() && setEngT() && setMatT()) return true;
		return true;
	}
	
	//
	public boolean setNumDig() {
		if(numT==null) {
		}else if(Pattern.matches("[0-9]+", numT)) {numDig = Integer.parseInt(numT); return true;}
		return false;
	}
	
	public boolean setKorDig() {
		if(korT==null) {
		}else if(Pattern.matches("[0-9]+", korT)) {
			korDig = Integer.parseInt(korT); return true;}
		return false;
	}
	
	public boolean setEngDig() {
		if(engT==null) {
		}else if(Pattern.matches("[0-9]+", engT)) {engDig = Integer.parseInt(engT); return true;}
		return false;
	}
	
	public boolean setMatDig() {
		if(matT==null) {
		}else if(Pattern.matches("[0-9]+", matT)) {matDig = Integer.parseInt(matT); return true;}
		return false;
	}
	
	public boolean setAllDig() {
		setAllT();
		if(setNumDig() && setKorDig() && setEngDig() && setMatDig()) return true;
		return false;
	}
	
	
//Event Handler Classes
	//������ư �̺�Ʈó��
	class CreateBtnHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (step) {
			case 0: // �⺻�����϶� - ������ư, ��ҹ�ư�� Ȱ��ȭ
				setAllBtn(false);
				cancelBtn.setEnabled(true);
				createBtn.setEnabled(true);
				setAllTextFiled(true);
				step = 1;
				break;
			case 1: // �Է��� Ȱ��ȭ����
				if(setAllT()) {
					if(setAllDig()) {
						if(!studentManager.isStudent(numDig)) {
							if(korDig < 0 || korDig > 100 || engDig < 0 || engDig > 100 || matDig < 0 || matDig > 100) {
								JOptionPane.showMessageDialog(new JFrame(), "������ 0~100�� ���ڸ� �Է��ϼ���..", "����",JOptionPane.ERROR_MESSAGE);
							}else {
								studentManager.insertStudent(numDig, nameT, korDig, engDig, matDig); // �߰�
								JOptionPane.showMessageDialog(new JFrame(), "����� �Ϸ�Ǿ����ϴ�.", "�˸�",JOptionPane.INFORMATION_MESSAGE);
								creatModel("�Է¿Ϸ�");
								setAllBtn(true);
								setAllTextFiled(false);
								setAllTextEmpty();
								step = 0;
							}
						}else JOptionPane.showMessageDialog(new JFrame(), "���� �л��� �ֽ��ϴ�.", "����",JOptionPane.ERROR_MESSAGE);
					}else JOptionPane.showMessageDialog(new JFrame(), "��ȣ�� ������ ���ڸ� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
				}else JOptionPane.showMessageDialog(new JFrame(), "��� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
		
	//������ư �̺�Ʈó��
	class UpdateBtnHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (step) {
			case 0: // �⺻�����϶� > ��ȣ�� �Է¹޵��Ϲٲ�
				setAllBtn(false);
				cancelBtn.setEnabled(true);
				updateBtn.setEnabled(true);
				setAllTextFiled(false);
				numberText.setEnabled(true);
				step = 1;
				break;
			case 1: // ��ȣ�� �Է¹��� ��  �ٸ� 4���� �׸��� �����Ҽ� �ֵ�����.
				setNumT();
				if(setNumDig()) {
					if(studentManager.isStudent(numDig)) {
						setAllTextFiled(true);
						numberText.setEnabled(false);
						nameText.setText(nameT);
						korText.setText(korT);
						engText.setText(engT);
						matText.setText(matT);
						step = 2;
					}else JOptionPane.showMessageDialog(new JFrame(), "ã���л��� ��Ͽ� �����ϴ�..", "����",JOptionPane.ERROR_MESSAGE);
				}else JOptionPane.showMessageDialog(new JFrame(), "�ùٸ� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
			case 2:
				setNumT();
				if(setNumDig()) {
					if(studentManager.isStudent(numDig)) {
						if(korDig < 0 || korDig > 100 || engDig < 0 || engDig > 100 || matDig < 0 || matDig > 100) {
							JOptionPane.showMessageDialog(new JFrame(), "������ 0~100�� ���ڸ� �Է��ϼ���..", "����",JOptionPane.ERROR_MESSAGE);
						}else {
							setAllTextFiled(true);
							numberText.setEnabled(false);
							nameText.setText(studentManager.selectStudent(numDig).getName());
							korText.setText(studentManager.selectStudent(numDig).getKor()+"");
							engText.setText(studentManager.selectStudent(numDig).getEng()+"");
							matText.setText(studentManager.selectStudent(numDig).getMat()+"");
							step = 3;
						}
					}else JOptionPane.showMessageDialog(new JFrame(), "ã���л��� ��Ͽ� �����ϴ�..", "����",JOptionPane.ERROR_MESSAGE); 
					break;
				}else JOptionPane.showMessageDialog(new JFrame(), "�ùٸ� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
				break;
			case 3:
				setAllT();
				if(setAllDig()) {
					JOptionPane.showMessageDialog(new JFrame(), "������ �����Ǿ����ϴ�..", "�˸�",JOptionPane.INFORMATION_MESSAGE);
					studentManager.updateAll(numDig, nameT, korDig, engDig, matDig); // ����
					creatModel("�����Ϸ�");
					setAllBtn(true);
					setAllTextFiled(false);
					setAllTextEmpty();
					step = 0;
				}else JOptionPane.showMessageDialog(new JFrame(), "�ùٸ� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	//�б��ư �̺�Ʈó��
	class ReadBtnHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (step) {
			case 0: // �⺻�����϶� - ������ư, ��ҹ�ư�� Ȱ��ȭ
				setAllBtn(false);
				cancelBtn.setEnabled(true);
				readBtn.setEnabled(true);
				setAllTextFiled(false);
				numberText.setEnabled(true);
				step = 1;
				break;
			case 1:
				setNumT();
				if(setNumDig()) {
					if(studentManager.isStudent(numDig)) {
						JOptionPane.showMessageDialog(new JFrame(), 
								studentManager.print(numDig),
								"'" + studentManager.namePrint(numDig) + " '���� �л�����",JOptionPane.INFORMATION_MESSAGE);
						setAllBtn(true);
						setAllTextFiled(false);
						setAllTextEmpty();
						step = 0;
					}else JOptionPane.showMessageDialog(new JFrame(), "ã���л��� ��Ͽ� �����ϴ�..", "����",JOptionPane.ERROR_MESSAGE);
				} else JOptionPane.showMessageDialog(new JFrame(), "�ùٸ� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	//������ư �̺�Ʈó��
	class DeleteBtnHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (step) {
			case 0: // �⺻�����϶� - ������ư, ��ҹ�ư�� Ȱ��ȭ
				setAllBtn(false);
				cancelBtn.setEnabled(true);
				deleteBtn.setEnabled(true);
				setAllTextFiled(false);
				numberText.setEnabled(true);
				step = 1;
				break;
			case 1:
				setNumT();
				if(setNumDig()) {
					if(studentManager.isStudent(numDig)) {
						//�������� �ѹ��� ����� ���
						if(JOptionPane.showConfirmDialog(new JFrame(), "���������Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION) == 0) {
							studentManager.deleteStudent(numDig);
							creatModel("�����Ϸ�");
							setAllBtn(true);
							setAllTextFiled(false);
							setAllTextEmpty();
							step = 0;
						}else numberText.setText(""); // ��� ��������
					}else JOptionPane.showMessageDialog(new JFrame(), "ã���л��� ��Ͽ� �����ϴ�..", "����",JOptionPane.ERROR_MESSAGE);
				} else JOptionPane.showMessageDialog(new JFrame(), "�ùٸ� ���� �Է��ϼ���.", "����",JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	// ��ҹ�ư �̺�Ʈó��
	class CancelBtnHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			setAllBtn(true);
			setAllTextEmpty();
			setAllTextFiled(false);
			step = 0;
		}
	}
}