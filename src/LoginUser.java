import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class LoginUser extends JPanel implements ActionListener {
	JLabel lbuser, lbpass;
	JTextField tuser;
	JPasswordField tpass;
	JButton btsubmit, btcreate;
	static String user; 
	
	public LoginUser() {
		user="";
		setSize(300, 300);
		setVisible(true);
		lbuser = new JLabel("Username(For User)");
		lbpass = new JLabel("Password(For User)");

		tuser = new JTextField(10);
		tpass = new JPasswordField();

		btsubmit = new JButton("Submit");
		btcreate = new JButton("Create Account");

		btsubmit.addActionListener(this);
		btcreate.addActionListener(this);
		add(lbuser);
		add(tuser);
		add(lbpass);
		add(tpass);
		add(btsubmit);
		add(btcreate);

		this.setLayout(new GridLayout(6, 2,100,100));
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
				PreparedStatement pstmt = con
						.prepareStatement("select count(*) from UserTb where username=? and password=?");
				pstmt.setString(1, tuser.getText());
				pstmt.setString(2, tpass.getText());
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				int c = rs.getInt(1);
				if(c==0)
				{
					JOptionPane.showMessageDialog(null, "Please enter valid username and password", "Invalid", JOptionPane.ERROR_MESSAGE);
				}
				else if(c==1)
				{
					user = tuser.getText();
					JOptionPane.showMessageDialog(null, "You are login as User "+tuser.getText(), "Welcome", JOptionPane.INFORMATION_MESSAGE);
					// tabbed bar
					refresh();
					new UserTabbedBar();
				}
				con.close();

			} catch (SQLException ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}

		else if (src == btcreate) {
			 new CreateUser();
		}

	}

	void refresh() {
		// TODO Auto-generated method stub
		tuser.setText("");
		tpass.setText("");
	}

}
