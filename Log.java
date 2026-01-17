package demo;

import java.awt.Dimension;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;


public class Log extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	JComboBox comboBox;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Log frame = new Log();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Log() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 990, 550);
		contentPane = new JPanel() {
	      
				private static final long serialVersionUID = 1L;

				@Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                ImageIcon background = new ImageIcon("a.jpg");
	                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(249, 162, 103, 13);
		contentPane.add(lblNewLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameField.setBounds(393, 153, 143, 31);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(249, 216, 103, 13);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(393, 207, 143, 31);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		loginButton.setBounds(294, 261, 198, 31);
		loginButton.addActionListener(e -> login());
		contentPane.add(loginButton);
		
		JLabel lblNewLabel_2 = new JLabel("If not registered");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(231, 312, 152, 40);
		contentPane.add(lblNewLabel_2);
		
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Times New Roman", Font.BOLD, 17));
		registerButton.setBounds(393, 319, 143, 31);
		registerButton.addActionListener(e -> new Register());
		contentPane.add(registerButton);
		
		JLabel lblNewLabel_3 = new JLabel("Welcome!");
		lblNewLabel_3.setForeground(SystemColor.textHighlightText);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel_3.setBounds(403, 40, 120, 31);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblLoginAs = new JLabel("Login As");
		lblLoginAs.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblLoginAs.setForeground(Color.WHITE);
		lblLoginAs.setBounds(249, 106, 103, 22);
		contentPane.add(lblLoginAs);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.BOLD, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Student"}));
		comboBox.setBounds(393, 102, 143, 31);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Log.class.getResource("/demo/log1.jpg")));
		lblNewLabel_4.setBounds(0, 0, 1004, 533);
		contentPane.add(lblNewLabel_4);
		
		/*JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setIcon(new ImageIcon(Log.class.getResource("/demo/log1.jpg")));
		lblNewLabel_4.setBounds(0, 0, 1067, 550);
		contentPane.add(lblNewLabel_4);*/
	}
    private void login() {
    	
			 String username = usernameField.getText();
		        String password = new String(passwordField.getPassword());
		        String loginas=(String) comboBox.getSelectedItem();
		        if (username.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        if (password.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        if (loginas == null || loginas.isEmpty()) {
		            JOptionPane.showMessageDialog(this, "Please select a login type", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        try (Connection conn = MainApp.connect()) {
		            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=? AND loginas=? ");
		            stmt.setString(1, username);
		            stmt.setString(2, password);
		            stmt.setString(3, loginas);
		            ResultSet rs = stmt.executeQuery();
		            if (rs.next()) {
		            	//JOptionPane.showMessageDialog(this, "Login Successful");
		            	if(loginas =="Admin")
		            	{
			                Dashboard frame = new Dashboard();
							frame.setVisible(true);
							dispose();
		            	}
		            	else {
			            	SeatingArrangement s=new SeatingArrangement(loginas);
			            	s.setVisible(true);
			                dispose();
		            	}
		            } else {
		                JOptionPane.showMessageDialog(this, "Invalid credentials");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			
		}
    }
