package demo;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.SystemColor;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			//
			public void run() {
				try {
					Dashboard frame = new Dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Dashboard() {
		
		setTitle("ExamHallSeatingArrangementSystem\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 482);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton StudentButton = new JButton("Student");
		StudentButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		StudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student st1=new Student();
				st1.setVisible(true);
			}
		});
		StudentButton.setBounds(240, 170, 130, 47);
		contentPane.add(StudentButton);
		
		JButton ArrangeseatButton = new JButton("Arrange Seat");
		ArrangeseatButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ArrangeseatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SeatingArrangement("Admin").setVisible(true);
			}
		});
		ArrangeseatButton.setBounds(423, 170, 130, 47);
		contentPane.add(ArrangeseatButton);
		
		JButton ExamhallButton = new JButton("ExamHall");
		ExamhallButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ExamhallButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Examhall().setVisible(true);
			}
		});
		ExamhallButton.setBounds(423, 255, 130, 47);
		contentPane.add(ExamhallButton);
		
		JButton ExamButton = new JButton("Exam");
		ExamButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ExamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exam exam=new Exam();
				exam.setVisible(true);
			}
		});
		ExamButton.setBounds(240, 255, 130, 47);
		contentPane.add(ExamButton);
		
		JLabel lblNewLabel = new JLabel("Enter Details");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(347, 121, 107, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Home Page");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setBounds(347, 73, 107, 38);
		contentPane.add(lblNewLabel_1);
		
	}
}
