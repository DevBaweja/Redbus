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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginManager extends JPanel implements ActionListener {
	JLabel lbuser, lbpass;
	JTextField tuser;
	JPasswordField tpass;
	JButton btsubmit, btcreate;
	static String manager ;

	public LoginManager() {
		manager = "";
		setSize(300, 300);
		setVisible(true);
		setLayout(new GridLayout(6, 2,100,100));

		lbuser = new JLabel("Username(For Manager)");
		lbpass = new JLabel("Password(For Manager)");
		tuser = new JTextField(10);
		tpass = new JPasswordField();
		btsubmit = new JButton("Submit");
		btcreate = new JButton("Create Account");

		add(lbuser);
		add(tuser);
		add(lbpass);
		add(tpass);
		add(btsubmit);
		add(btcreate);

		btsubmit.addActionListener(this);
		btcreate.addActionListener(this);
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
						.prepareStatement("select count(*) from ManagerTb where username=? and password=?");
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
					manager = tuser.getText();
					JOptionPane.showMessageDialog(null, "You are login as Manager "+tuser.getText(), "Welcome", JOptionPane.INFORMATION_MESSAGE);
					// tabbed bar
					new ManagerTabbedBar();
					refresh();
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
		
		if (src == btcreate) {
			 new CreateManager();
		}
	}

	 void refresh() {
		// TODO Auto-generated method stub
		tuser.setText("");
		tpass.setText("");
	}

}
