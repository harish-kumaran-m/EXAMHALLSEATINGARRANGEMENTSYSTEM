package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainApp {
    static Connection connect() {
        try {
        	System.out.println("Database Connected");
        	Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/school_db", "root", "Harish2006@");
        	return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        Log l1=new Log();
        l1.setVisible(true);
        
    }
}
