import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.*;

public class CreateManager extends JDialog implements ActionListener {
	JLabel lbusername, lbpassword, lbtravel, lbtravelcode;
	JTextField txusername, txtravel, txtravelcode;
	JPasswordField txpassword;
	JButton btsubmit, btclose;

	public CreateManager() {
		setSize(new Dimension(1000, 1000));
		setPreferredSize(new Dimension(1000, 1000));
		setVisible(true);

		lbusername = new JLabel("Username");
		lbpassword = new JLabel("Password");
		lbtravel = new JLabel("Travels");
		lbtravelcode = new JLabel("Travels Code");

		txusername = new JTextField(10);
		txpassword = new JPasswordField();
		txtravelcode = new JTextField(3);
		txtravelcode.setEditable(false);
		// Also make surte that this code doesnot exists
		int code;
		while (true) {
			 code = (int)(Math.random()*1000);
			if (code > 100) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
					Statement stmt = con.createStatement();
					stmt.executeUpdate("create database if not exists RedBusDb");
					stmt.execute("use RedBusDb");
					stmt.executeUpdate(
							"create table if not exists ManagerTb(username varchar(50),password varchar(50),travel_code int(3) unique,travel varchar(50),primary key(username))");

					PreparedStatement pstmt = con
							.prepareStatement("select count(*) from ManagerTb where travel_code=? ");
					pstmt.setInt(1, code);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					int c = rs.getInt(1);
					if (c == 0)
						break;
						con.close();

				} catch (SQLException ae) {
					// TODO Auto-generated catch block
					ae.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
		txtravelcode.setText(Integer.toString(code));
		txtravel = new JTextField(10);

		btsubmit = new JButton("Submit");
		btclose = new JButton("Close");

		setLayout(new GridLayout(5, 2));
		add(lbusername);
		add(txusername);
		add(lbpassword);
		add(txpassword);
		add(lbtravelcode);
		add(txtravelcode);
		add(lbtravel);
		add(txtravel);
		add(btsubmit);
		add(btclose);

		btsubmit.addActionListener(this);
		btclose.addActionListener(this);
		validate();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == btsubmit) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");
				stmt.executeUpdate(
						"create table if not exists ManagerTb(username varchar(50),password varchar(50),travel_code int(3) unique,travel varchar(50),primary key(username))");

				PreparedStatement pstmt = con.prepareStatement(
						"insert into ManagerTb(username,password,travel_code,travel) values(?,?,?,?)");
				pstmt.setString(1, txusername.getText());
				pstmt.setString(2, txpassword.getText());
				pstmt.setInt(3, Integer.parseInt(txtravelcode.getText()));
				pstmt.setString(4, txtravel.getText());
				pstmt.executeUpdate();

				con.close();

			} catch (SQLException ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (src == btclose) {
			this.dispose();
		}

	}
}
