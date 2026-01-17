package demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	JComboBox comboBox ;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Register() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Student"}));
		comboBox.setBounds(221, 95, 76, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Login As");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(141, 96, 65, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(141, 135, 65, 20);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(141, 184, 65, 17);
		contentPane.add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		usernameField.setBounds(221, 135, 109, 19);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(e->regis());
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(193, 231, 85, 28);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordField.setBounds(221, 184, 109, 19);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Register Page");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(169, 48, 128, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setBackground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(0, 0, 493, 353);
		contentPane.add(lblNewLabel_2);
		contentPane.setBackground(SystemColor.activeCaption);
		
		JButton btnbackButton = new JButton("Back");
        btnbackButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Dashboard().setVisible(true);
        	}
        });
        btnbackButton.setBounds(345, 40, 85, 34);
        contentPane.add(btnbackButton);
        
		setVisible(true);
	}
	private void regis() {
		String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String loginas = (String)comboBox.getSelectedItem();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Username ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Password ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (loginas == null || loginas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a login type", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
      
        try (Connection conn = MainApp.connect()) {
            // Check if the username already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Username already exists, show an error message
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Proceed with registration if the username is not duplicated
            String insertQuery = "INSERT INTO users (username, password, loginas) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, loginas);
            stmt.executeUpdate();

            // Success message
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose(); // Close the registration window

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
}
