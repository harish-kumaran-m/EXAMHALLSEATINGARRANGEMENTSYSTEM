package demo;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class SeatingArrangement extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxExam;
    private JComboBox<String> comboBoxHall;
    private JComboBox<String> RollNo;
    private JButton btnGenerateSeating,btnDeleteSeating,btnShowAllSeating, btnbackButton ;
    private JTable tableSeating;
    private DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SeatingArrangement frame = new SeatingArrangement("Admin");
                    frame.btnbackButton.setVisible(false);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SeatingArrangement(String loginas) {
        setTitle("Seating Arrangement");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Label for Exam Selection
        JLabel lblExam = new JLabel("Select Exam");
        lblExam.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblExam.setBounds(30, 30, 100, 30);
        contentPane.add(lblExam);

        // Exam ComboBox (fill it dynamically from the database)
        comboBoxExam = new JComboBox<>();
        comboBoxExam.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxExam.setBounds(150, 30, 200, 30);
        contentPane.add(comboBoxExam);

        // Label for Hall Selection
        JLabel lblHall = new JLabel("Select Hall");
        lblHall.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblHall.setBounds(30, 80, 100, 30);
        contentPane.add(lblHall);

        // Hall ComboBox (fill it dynamically from the database)
        comboBoxHall = new JComboBox<>();
        comboBoxHall.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxHall.setBounds(150, 80, 200, 30);
        contentPane.add(comboBoxHall);

        // Label for Roll No Entry
        JLabel lblRollNo = new JLabel("Roll No");
        lblRollNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRollNo.setBounds(30, 130, 100, 30);
        contentPane.add(lblRollNo);

        // Roll No Input field
        RollNo = new JComboBox<String>();
        RollNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        RollNo.setBounds(150, 130, 200, 30);
        contentPane.add(RollNo);

        // Button to generate seating arrangement
        btnGenerateSeating = new JButton("Generate Seating");
        btnGenerateSeating.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnGenerateSeating.setBounds(150, 180, 200, 30);
        contentPane.add(btnGenerateSeating);

        // Table to display the seating arrangement
        String[] columns = { "Roll No", "Exam", "Exam Date", "Hall No"};
        model = new DefaultTableModel(columns, 0);
        tableSeating = new JTable(model);
        tableSeating.setBounds(30, 230, 700, 300);

        JScrollPane scrollPane = new JScrollPane(tableSeating);
        scrollPane.setBounds(30, 230, 700, 300);
        contentPane.add(scrollPane);

        // Action listener for the "Generate Seating" button
        btnGenerateSeating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateSeating();
            }
        });

        btnDeleteSeating = new JButton("Delete Seating");
        btnDeleteSeating.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDeleteSeating.setBounds(400, 180, 200, 30);  // Position next to Generate Seating button
        contentPane.add(btnDeleteSeating);

        btnDeleteSeating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSeating();
            }
        });
        
        btnShowAllSeating = new JButton("Show All Seating");
        btnShowAllSeating.setFont(new Font("Tahoma", Font.PLAIN, 16));
        //btnShowAllSeating.setBounds(150, 220, 200, 30); // Position below other buttons
        btnShowAllSeating.setBounds(400, 135, 200, 30);
        contentPane.add(btnShowAllSeating);
        
        btnShowAllSeating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllSeating();
            }
        });
        
        // Populate combo boxes for exams and halls
        populateComboBoxes();
        btnbackButton = new JButton("Back");
        btnbackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Dashboard().setVisible(true);
        	}
        });
        btnbackButton.setBounds(600, 30, 85, 34);
        contentPane.add(btnbackButton);
        if ("Student".equals(loginas)) {
            btnGenerateSeating.setEnabled(false); // Disable generate seating button
            btnDeleteSeating.setEnabled(false); // Disable delete button
            btnShowAllSeating.setEnabled(true); 
            btnbackButton.setEnabled(false);// disable seating table
        }
        contentPane.setBackground(new Color(230, 230, 250));
    }

    private void populateComboBoxes() {
        try (Connection conn = MainApp.connect()) {
            // Populate Exam ComboBox
            String examQuery = "SELECT exam_name FROM exams";
            PreparedStatement examStmt = conn.prepareStatement(examQuery);
            ResultSet rsExam = examStmt.executeQuery();
            while (rsExam.next()) {
                comboBoxExam.addItem(rsExam.getString("exam_name"));
            }

            // Populate Hall ComboBox
            String hallQuery = "SELECT hall_no FROM exam_halls";
            PreparedStatement hallStmt = conn.prepareStatement(hallQuery);
            ResultSet rsHall = hallStmt.executeQuery();
            while (rsHall.next()) {
                comboBoxHall.addItem(rsHall.getString("hall_no"));
            }
            
            // Populate rollno
            String studentQuery = "SELECT rollno FROM students";
            PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
            ResultSet rsstudent = studentStmt.executeQuery();
            while (rsstudent.next()) {
                RollNo.addItem(rsstudent.getString("rollno"));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
       
 /*   private void generateSeating() {
        String rollNo = (String) RollNo.getSelectedItem();
        String exam = (String) comboBoxExam.getSelectedItem();
        String hall = (String) comboBoxHall.getSelectedItem();

        if (rollNo.isEmpty() || exam == null || hall == null) {
            JOptionPane.showMessageDialog(contentPane, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate roll number
        if (isRollNoDuplicate(rollNo, exam, hall)) {
            JOptionPane.showMessageDialog(contentPane, "This student is already assigned to this exam in this hall.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = MainApp.connect()) {
            // Check if the hall is full
            String capacityQuery = "SELECT capacity FROM exam_halls WHERE hall_no = ?";
            PreparedStatement capacityStmt = conn.prepareStatement(capacityQuery);
            capacityStmt.setString(1, hall);
            ResultSet rsCapacity = capacityStmt.executeQuery();
            if (rsCapacity.next()) {
                int hallCapacity = rsCapacity.getInt("capacity");

                // Get the current number of students assigned to this hall
                String countQuery = "SELECT COUNT(*) FROM seating_arrangement WHERE hall_no = ?";
                PreparedStatement countStmt = conn.prepareStatement(countQuery);
                countStmt.setString(1, hall);
                ResultSet rsCount = countStmt.executeQuery();
                rsCount.next();
                int currentCount = rsCount.getInt(1);

                // If the hall is full
                if (currentCount >= hallCapacity) {
                    JOptionPane.showMessageDialog(contentPane, "Hall is full. Cannot assign more students to this hall.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Fetch student details based on roll number
            String studentQuery = "SELECT name FROM students WHERE rollno = ?";
            PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
            studentStmt.setString(1, rollNo);
            ResultSet rsStudent = studentStmt.executeQuery();
            if (!rsStudent.next()) {
                JOptionPane.showMessageDialog(contentPane, "No student found with that roll number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String studentName = rsStudent.getString("name");

            // Fetch exam details (exam date)
            String examQuery = "SELECT exam_date FROM exams WHERE exam_name = ?";
            PreparedStatement examDetailsStmt = conn.prepareStatement(examQuery);
            examDetailsStmt.setString(1, exam);
            ResultSet rsExam = examDetailsStmt.executeQuery();
            if (!rsExam.next()) {
                JOptionPane.showMessageDialog(contentPane, "No exam found with that name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date examDate = rsExam.getDate("exam_date");

            // Insert the seating arrangement into the database
            String seatingQuery = "INSERT INTO seating_arrangement (roll_no, exam_id, exam_date, hall_no) VALUES ( ?, ?, ?, ?)";
            PreparedStatement seatingStmt = conn.prepareStatement(seatingQuery);
            seatingStmt.setString(1, rollNo);
            seatingStmt.setString(2, exam);
            seatingStmt.setDate(3, examDate);
            seatingStmt.setString(4, hall);
            seatingStmt.executeUpdate();

            // Add the entry to the table view
            model.addRow(new Object[]{ rollNo, exam, examDate, hall});
            JOptionPane.showMessageDialog(contentPane, "Seating generated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    
    private void generateSeating() {

        String exam = (String) comboBoxExam.getSelectedItem();
        String hall = (String) comboBoxHall.getSelectedItem();

        if (exam == null || hall == null) {
            JOptionPane.showMessageDialog(contentPane,
                    "Please select exam and hall.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = MainApp.connect()) {

            /* 1. Get hall capacity */
            String capacityQuery = "SELECT capacity FROM exam_halls WHERE hall_no = ?";
            PreparedStatement capacityStmt = conn.prepareStatement(capacityQuery);
            capacityStmt.setString(1, hall);
            ResultSet rsCapacity = capacityStmt.executeQuery();

            if (!rsCapacity.next()) {
                JOptionPane.showMessageDialog(contentPane,
                        "Hall not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int hallCapacity = rsCapacity.getInt("capacity");

            /* 2. Count already assigned students */
            String countQuery = "SELECT COUNT(*) FROM seating_arrangement WHERE hall_no = ? AND exam_id = ?";
            PreparedStatement countStmt = conn.prepareStatement(countQuery);
            countStmt.setString(1, hall);
            countStmt.setString(2, exam);
            ResultSet rsCount = countStmt.executeQuery();
            rsCount.next();
            int currentCount = rsCount.getInt(1);

            int remainingSeats = hallCapacity - currentCount;

            if (remainingSeats <= 0) {
                JOptionPane.showMessageDialog(contentPane,
                        "Hall is already full.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* 3. Get exam date */
            String examQuery = "SELECT exam_date FROM exams WHERE exam_name = ?";
            PreparedStatement examStmt = conn.prepareStatement(examQuery);
            examStmt.setString(1, exam);
            ResultSet rsExam = examStmt.executeQuery();

            if (!rsExam.next()) {
                JOptionPane.showMessageDialog(contentPane,
                        "Exam not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date examDate = rsExam.getDate("exam_date");

            /* 4. Fetch students NOT already assigned */
            String studentQuery =
                    "SELECT rollno FROM students WHERE rollno NOT IN " +
                    "(SELECT roll_no FROM seating_arrangement WHERE exam_id = ?)";

            PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
            studentStmt.setString(1, exam);
            ResultSet rsStudents = studentStmt.executeQuery();

            /* 5. Insert seating for all available seats */
            String insertQuery =
                    "INSERT INTO seating_arrangement (roll_no, exam_id, exam_date, hall_no) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            int assignedCount = 0;

            while (rsStudents.next() && assignedCount < remainingSeats) {

                String rollNo = rsStudents.getString("rollno");

                insertStmt.setString(1, rollNo);
                insertStmt.setString(2, exam);
                insertStmt.setDate(3, examDate);
                insertStmt.setString(4, hall);
                insertStmt.executeUpdate();

                model.addRow(new Object[]{rollNo, exam, examDate, hall});
                assignedCount++;
            }

            JOptionPane.showMessageDialog(contentPane,
                    assignedCount + " students assigned successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane,
                    "Error generating seating.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void displayAllSeating() {
        try (Connection conn = MainApp.connect()) {
            // Clear existing rows
            model.setRowCount(0);
            String query="select * from seating_arrangement";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Populate the table with all seating arrangements
                model.addRow(new Object[]{
                     
                    rs.getString("roll_no"),
                    rs.getString("exam_id"),
                    rs.getDate("exam_date"),
                    rs.getString("hall_no"),
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private boolean isRollNoDuplicate(String rollNo, String exam, String hall) {
        try (Connection conn = MainApp.connect()) {
            String checkQuery = "SELECT * FROM seating_arrangement WHERE roll_no = ? AND exam_id = ? AND hall_no = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, rollNo);
            checkStmt.setString(2, exam);
            checkStmt.setString(3, hall);
            ResultSet rs = checkStmt.executeQuery();
  
            return rs.next(); // If a record exists, it's a duplicate
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



private void deleteSeating() {
    int selectedRow = tableSeating.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(contentPane, "Please select a seating arrangement to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get roll number, exam, and hall from the selected row
    String rollNo = (String) tableSeating.getValueAt(selectedRow, 0);
    String exam = (String) tableSeating.getValueAt(selectedRow, 1); // Exam is in column 2 (index)
    String hall = (String) tableSeating.getValueAt(selectedRow, 3); // Hall is in column 4 (index)
    
    try (Connection conn = MainApp.connect()) {
        // Delete seating arrangement from the database
        String deleteQuery = "DELETE FROM seating_arrangement WHERE roll_no = ? AND exam_id = ? AND hall_no = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
        deleteStmt.setString(1, rollNo);
        deleteStmt.setString(2, exam);
        deleteStmt.setString(3, hall);
        
        // Execute the delete statement
        int rowsAffected = deleteStmt.executeUpdate();

        // If a row was deleted, remove it from the table view
        if (rowsAffected > 0) {
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(contentPane, "Seating arrangement deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(contentPane, "Seating arrangement not deleted. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(contentPane, "Error occurred while deleting seating arrangement.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}