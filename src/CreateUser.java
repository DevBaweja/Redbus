import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class CreateUser extends JDialog implements ActionListener {
	JLabel lbname, lbmobile, lbemail, lbgender, lbuser, lbpass;
	JTextField txname, txmobile, txemail, txgender, txuser;
	JPasswordField txpass;
	JRadioButton cbmale, cbfemale;
	ButtonGroup gr;
	JButton btsubmit, btclose;

	public CreateUser() {
		setPreferredSize(new Dimension(1000, 1000));
		setSize(new Dimension(1000, 1000));
		setVisible(true);
		setLayout(new GridLayout(7, 2,100,100));
		lbuser = new JLabel("Username");
		lbpass = new JLabel("Password");
		lbname = new JLabel("Name");
		lbmobile = new JLabel("Mobile Number");
		lbemail = new JLabel("Email Id");
		lbgender = new JLabel("Gender");
		btsubmit = new JButton("Submit");
		btclose = new JButton("Close");

		cbmale = new JRadioButton("Male");
		cbfemale = new JRadioButton("Female");
		gr = new ButtonGroup();
		gr.add(cbmale);
		gr.add(cbfemale);

		txname = new JTextField(10);
		txmobile = new JTextField(10);
		txemail = new JTextField(10);
		txgender = new JTextField(10);
		txuser = new JTextField(10);
		txpass = new JPasswordField();

		add(lbname);
		add(txname);

		add(lbuser);
		add(txuser);

		add(lbpass);
		add(txpass);

		add(lbmobile);
		add(txmobile);

		add(lbemail);
		add(txemail);
		add(lbgender);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(cbmale);
		p.add(cbfemale);
		add(p);

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
		if (src == btsubmit)

		{

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists RedBusDb");
				stmt.execute("use RedBusDb");
				stmt.executeUpdate(
						"create table if not exists UserTb(username varchar(50),password varchar(50),name varchar(50),gender varchar(10),mobile varchar(100),email varchar(50),primary key(username))");

				PreparedStatement pstmt = con
						.prepareStatement("insert into UserTb(username,password,name,gender,mobile,email) values(?,?,?,?,?,?)");
				pstmt.setString(1, txuser.getText());
				pstmt.setString(2, txpass.getText());
				pstmt.setString(3, txname.getText());
				String g = "";
				if(cbmale.isSelected())
					g = "Male";
				else if(cbfemale.isSelected())
					g = "Female";
				pstmt.setString(4,g);
				pstmt.setString(5, txmobile.getText());
				pstmt.setString(6, txemail.getText());
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
