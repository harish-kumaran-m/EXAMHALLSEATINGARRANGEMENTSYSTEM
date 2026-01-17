package demo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import project.examh1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Examhall extends JFrame {
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField, textField_1;
    private JComboBox<String> comboBox;
    private JButton btnSubmit, btnDelete, btnDisplay;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Examhall frame = new Examhall();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Examhall() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 409);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Hall No");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(132, 122, 52, 28);
        contentPane.add(lblNewLabel);
        
        textField = new JTextField();
        textField.setBounds(194, 127, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);
        
        JLabel lblBlock = new JLabel("Block");
        lblBlock.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBlock.setBounds(132, 168, 52, 28);
        contentPane.add(lblBlock);
        
        comboBox = new JComboBox<String>();
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"CSE", "ECE", "AJ", "EEE", "S&H"}));
        comboBox.setBounds(196, 173, 57, 21);
        contentPane.add(comboBox);
        
        JLabel lblCapacity = new JLabel("Capacity");
        lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblCapacity.setBounds(132, 217, 52, 28);
        contentPane.add(lblCapacity);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(194, 223, 59, 19);
        contentPane.add(textField_1);

        btnSubmit = new JButton("Submit");
        btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSubmit.setBounds(105, 267, 85, 28);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });
        contentPane.add(btnSubmit);
        
        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnDelete.setBounds(205, 267, 85, 28);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });
        contentPane.add(btnDelete);
        
        btnDisplay = new JButton("Display");
        btnDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnDisplay.setBounds(105, 304, 185, 28);
        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRecords();
            }
        });
        contentPane.add(btnDisplay);

        JLabel lblNewLabel_1 = new JLabel("Exam Hall");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(156, 52, 79, 19);
        contentPane.add(lblNewLabel_1);
        contentPane.setBackground(Color.blue);
        
        JButton btnbackButton = new JButton("Back");
        btnbackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Dashboard().setVisible(true);
        	}
        });
        btnbackButton.setBounds(350, 30, 85, 34);
        contentPane.add(btnbackButton);
    }

    // Insert the exam hall record into the database
    private void insertRecord() {
        String hallNo = textField.getText().trim();
        String block = (String) comboBox.getSelectedItem();
        String cap = textField_1.getText().trim();
        
        if (hallNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hall No is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = MainApp.connect()) {
            String query = "INSERT INTO exam_halls (hall_no, block, capacity) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, hallNo);
                pstmt.setString(2, block);
                pstmt.setString(3, cap);
                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Record inserted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord() {
        String hallNo = JOptionPane.showInputDialog(this, "Enter Hall No to delete:");
        
        if (hallNo == null || hallNo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hall No is required to delete a record.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = MainApp.connect()) {
            String query = "DELETE FROM exam_halls WHERE hall_no = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, hallNo);
                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Record deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Hall No not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayRecords() {
    	
    	JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        String[] columns = {"Hallno", "Block"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setBounds(30, 30, 700, 300);
        panel.setVisible(true);
        panel.add(table);
        
        
        try (Connection conn = MainApp.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM exam_halls")) {
            model.addRow(new Object[] {"Hall_no","Block"});
            while (rs.next()) {
                String no = rs.getString("hall_no");
                String b1 = rs.getString("block");
                model.addRow(new Object[]{no,b1});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error fetching student data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
