package demo;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;

public class Exam extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField examNameField, subjectCodeField, examDateField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextArea displayArea;  // To display the exams
    private JButton btnDisplay, btnDelete;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Exam frame = new Exam();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Exam() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 425);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Exam Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(123, 129, 78, 21);
		contentPane.add(lblNewLabel);
		
		examNameField = new JTextField();
		examNameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		examNameField.setBounds(211, 130, 96, 19);
		contentPane.add(examNameField);
		examNameField.setColumns(10);
		
		JLabel lblSubjectCode = new JLabel("Subject Code");
		lblSubjectCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSubjectCode.setBounds(123, 176, 83, 21);
		contentPane.add(lblSubjectCode);
		
		subjectCodeField = new JTextField();
		subjectCodeField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		subjectCodeField.setColumns(10);
		subjectCodeField.setBounds(211, 177, 96, 19);
		contentPane.add(subjectCodeField);
		
		JLabel lblSubjectCode_1 = new JLabel("Exam Date");
		lblSubjectCode_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSubjectCode_1.setBounds(123, 221, 83, 21);
		contentPane.add(lblSubjectCode_1);
		
		examDateField = new JTextField();
		examDateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		examDateField.setColumns(10);
		examDateField.setBounds(211, 222, 96, 19);
		contentPane.add(examDateField);
		
		JButton btnAddButton = new JButton("Add Exam");
		btnAddButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAddButton.addActionListener(e->addExam());
		btnAddButton.setBounds(148, 281, 109, 32);
		contentPane.add(btnAddButton);
		
		btnDisplay = new JButton("Display Exams");
		btnDisplay.setFont(new Font("Tahoma", Font.BOLD, 12));
	    btnDisplay.addActionListener(e -> displayExams());
	    btnDisplay.setBounds(264, 281, 127, 32);
	    contentPane.add(btnDisplay);

	    // Button to delete an exam
	    btnDelete = new JButton("Delete Exam");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDelete.addActionListener(e -> deleteExam());
	    btnDelete.setBounds(31, 281, 109, 32);
        contentPane.add(btnDelete);

        // Text area to display the exams
	    displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBounds(20, 410, 430, 150);
	    contentPane.add(displayArea);
	        
	    JLabel lblExamDetails = new JLabel("Exam Details");
	    lblExamDetails.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblExamDetails.setBounds(148, 77, 133, 21);
        contentPane.add(lblExamDetails);
	        
	    JButton btnbackButton = new JButton("Back");
        btnbackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
	        		dispose();
	        		new Dashboard().setVisible(true);
	        	}
	    });
	    btnbackButton.setBounds(339, 28, 85, 34);
	    contentPane.add(btnbackButton);        
	}

	private void addExam() {
		 String examName = examNameField.getText();
	        String subjectCode = subjectCodeField.getText();
	        String examDate = examDateField.getText();

	        if (examName.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter the exam name.");
	            return;
	        }
	        if (subjectCode.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter the subject code.");
	            return;
	        }
	        if (examDate.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter the exam date.");
	            return;
	        }
	        if (examName.isEmpty() || subjectCode.isEmpty() || examDate.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter the All Details.");
	            return;
	        }
	        try (Connection conn = MainApp.connect()) {
	            PreparedStatement stmt = conn.prepareStatement("INSERT INTO exams (exam_name, subject_code, exam_date) VALUES (?, ?, ?)");
	            stmt.setString(1, examName);
	            stmt.setString(2, subjectCode);
	            stmt.setDate(3, Date.valueOf(examDate));
	            stmt.executeUpdate();
	            JOptionPane.showMessageDialog(this, "Exam added successfully!");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    
	}
	
	private void displayExams() {
		
		setBounds(500, 200, 800, 400);
        setTitle("All Students");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        String[] columns = {"ExamName", "Subjectcode", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setBounds(30, 30, 700, 300);
        panel.setVisible(true);
        panel.add(table);
        
        
        try (Connection conn = MainApp.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM exams")) {
            model.addRow(new Object[]{"Exam_name", "Subject_code", "Exam_date"});
            while (rs.next()) {
                String name = rs.getString("exam_name");
                String code = rs.getString("subject_code");
                String date = rs.getString("exam_date");
                model.addRow(new Object[]{name, code, date});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching exam data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	  private void deleteExam() {
	        String examName = examNameField.getText();
	        if (examName.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please enter the exam name to delete.");
	            return;
	        }

	        try (Connection conn = MainApp.connect()) {
	            PreparedStatement stmt = conn.prepareStatement("DELETE FROM exams WHERE exam_name = ?");
	            stmt.setString(1, examName);
	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(this, "Exam deleted successfully!");
	            } else {
	                JOptionPane.showMessageDialog(this, "No exam found with that name.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
