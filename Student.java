package demo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Student extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField NameField;
    private JTextField rollnotextField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Student frame = new Student();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Student() {
    	setTitle("Student");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 951, 586);
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("pathtoimage.jpg"); 
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBackground(new Color(255, 228, 225));
        contentPane.setForeground(Color.RED);
        contentPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(223, 130, 160, 42);
        contentPane.add(lblNewLabel);
        
        JLabel lblStudentDetails = new JLabel("Student Details");
        lblStudentDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblStudentDetails.setBounds(304, 78, 160, 42);
        contentPane.add(lblStudentDetails);
        
        NameField = new JTextField();
        NameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        NameField.setBounds(345, 130, 217, 34);
        contentPane.add(NameField);
        NameField.setColumns(10);
        
        JLabel lblRollno = new JLabel("Rollno");
        lblRollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblRollno.setBounds(223, 195, 160, 42);
        contentPane.add(lblRollno);
        
        rollnotextField = new JTextField();
        rollnotextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rollnotextField.setBounds(345, 195, 217, 34);
        contentPane.add(rollnotextField);
        rollnotextField.setColumns(10);
        
        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblDepartment.setBounds(223, 263, 160, 42);
        contentPane.add(lblDepartment);
        
        JLabel lblYear = new JLabel("YEAR");
        lblYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblYear.setBounds(223, 344, 160, 42);
        contentPane.add(lblYear);
        
        JComboBox DepartmentcomboBox = new JComboBox();
        DepartmentcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        DepartmentcomboBox.setModel(new DefaultComboBoxModel(new String[] {"","CSE", "ECE", "EEE", "BIO-TECH"}));
        DepartmentcomboBox.setBounds(345, 263, 217, 35);
        contentPane.add(DepartmentcomboBox);
        
        JComboBox yearcomboBox = new JComboBox();
        yearcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
        yearcomboBox.setModel(new DefaultComboBoxModel(new String[] {"", "I", "II", "III", "IV"}));
        yearcomboBox.setBounds(345, 345, 89, 34);
        contentPane.add(yearcomboBox);
        
        // Submit Button
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = NameField.getText();
                String rollNo = rollnotextField.getText();
                String department = (String) DepartmentcomboBox.getSelectedItem();
                String year = (String) yearcomboBox.getSelectedItem();

                // Input validation
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (rollNo.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Roll number is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (department.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Department must be selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (year.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Year must be selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (insertStudentData(name, rollNo, department, year)) {
                    JOptionPane.showMessageDialog(contentPane, "Submitted Successfully");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Error in Submission");
                }
            }
        });
        btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnSubmit.setBounds(324, 421, 124, 34);
        contentPane.add(btnSubmit);

        // Delete Button
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new frame to enter roll number for deletion
                new DeleteStudentFrame().setVisible(true);
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDelete.setBounds(464, 421, 124, 34);
        contentPane.add(btnDelete);

        // Display Button
        JButton btnDisplay = new JButton("Display");
        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DisplayStudentsFrame().setVisible(true);
            }
        });
        btnDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnDisplay.setBounds(604, 421, 124, 34);
        contentPane.add(btnDisplay);
        
        JButton btnbackButton = new JButton("Back");
        btnbackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Dashboard().setVisible(true);
        	}
        });
        btnbackButton.setBounds(787, 43, 85, 34);
        contentPane.add(btnbackButton);
    }

    
    private boolean insertStudentData(String name, String rollNo, String department, String year) {
        try (Connection conn = MainApp.connect();)//Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) 
        {
            String query = "INSERT INTO students (name, rollno, year, department) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, rollNo);
                stmt.setString(4, year);
                stmt.setString(3, department);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Method to delete student data from the database
    public static boolean deleteStudentData(String rollNo) {
        try (Connection conn = MainApp.connect();)
        {
            String query = "DELETE FROM students WHERE rollno = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, rollNo);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Frame for deleting a student by roll number
    class DeleteStudentFrame extends JFrame {
        private JTextField textFieldRollNo;

        public DeleteStudentFrame() {
            setBounds(500, 200, 400, 200);
            setTitle("Delete Student");

            JPanel panel = new JPanel();
            panel.setLayout(null);
            setContentPane(panel);

            JLabel lblRollNo = new JLabel("Enter Roll No to Delete:");
            lblRollNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblRollNo.setBounds(30, 30, 150, 30);
            panel.add(lblRollNo);

            textFieldRollNo = new JTextField();
            textFieldRollNo.setBounds(200, 30, 150, 30);
            panel.add(textFieldRollNo);
            textFieldRollNo.setColumns(10);

            JButton btnDelete = new JButton("Delete");
            btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnDelete.setBounds(150, 80, 100, 30);
            panel.add(btnDelete);

            btnDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String rollNo = textFieldRollNo.getText();
                    if (rollNo.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Roll number is required.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (deleteStudentData(rollNo)) {
                        JOptionPane.showMessageDialog(panel, "Student Deleted Successfully");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "No student found with the provided roll number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    // Frame to display all students in a table
    class DisplayStudentsFrame extends JFrame {

        public DisplayStudentsFrame() {
            setBounds(500, 200, 800, 400);
            setTitle("All Students");

            JPanel panel = new JPanel();
            panel.setLayout(null);
            setContentPane(panel);

            String[] columns = {"Name", "Roll No","Year", "Department"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable table = new JTable(model);
            table.setBounds(30, 30, 700, 300);
            panel.setVisible(true);
            panel.add(table);
            
            
            try (Connection conn = MainApp.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
                model.addRow(new Object[]{"Name", "RollNo", "Year", "Department"});
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String rollNo = rs.getString("rollno");
                    String department = rs.getString("department");
                    String year = rs.getString("year");
                    model.addRow(new Object[]{name, rollNo, department, year});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error fetching student data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
    }
}

